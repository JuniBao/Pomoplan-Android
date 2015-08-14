package com.rooftrellen.pomoplan.backend;

import java.util.ArrayList;

import com.rooftrellen.pomoplan.model.PomoDaily;
import com.rooftrellen.pomoplan.model.PomoTag;
import com.rooftrellen.pomoplan.model.PomoUser;
import com.rooftrellen.pomoplan.model.Pomodoro;

/**
 * RetrieveService is a class for retrieving data from backend.
 *
 * @author Team FirstRow
 * @version 1.0.0
 */
public class GetService {

    /**
     * Gets user by name and password.
     *
     * @param name the user name.
     * @param password the user password.
     * @return the PomoUser.
     * @since 1.0.0
     */
    public PomoUser getUser(String name, String password) {
        PomoServer server = new PomoServer();
        String parameters = PomoServer.MODE_SELECT + "&" + "name=" + name + "&" + "password="
                + password;
        String url = PomoServer.DOMAIN_URL + PomoServer.SERVLET_USER;
        String data = server.get(url, parameters);
        if (data.equals("0")) {
            return null;
        }
        String[] attributes = data.split("&");
        String id = attributes[0];
        boolean wifiOff = Integer.parseInt(attributes[1])==1;
        boolean bluetoothOff = Integer.parseInt(attributes[2])==1;
        int pomoDuration = Integer.parseInt(attributes[3]);
        int breakDuration = Integer.parseInt(attributes[4]);
        return new PomoUser(id, name, password, wifiOff, bluetoothOff, pomoDuration,
                breakDuration);
    }

    /**
     * Verifies the existence of user name.
     *
     * @param name the user name.
     * @return the existence of user.
     * @since 1.0.0
     */
    public boolean getUser(String name) {
        PomoServer server = new PomoServer();
        String parameters = PomoServer.MODE_SELECT + "&" + "name=" + name;
        String url = PomoServer.DOMAIN_URL + PomoServer.SERVLET_USER;
        String data = server.get(url, parameters);
        return data.equals("1");
    }

    /**
     * Gets all daily by user ID.
     *
     * @param userId the user ID.
     * @return the list of PomoDaily.
     * @since 1.0.0
     */
    public ArrayList<PomoDaily> getDaily(String userId) {
        PomoServer server = new PomoServer();
        String parameters = PomoServer.MODE_SELECT + "&" + "userid=" + userId;
        String url = PomoServer.DOMAIN_URL + PomoServer.SERVLET_DAILY;
        String data = server.get(url, parameters);
        ArrayList<PomoDaily> list = new ArrayList<>();
        if (data.equals("0")) {
            return list;
        }
        String[] dailies = data.split(";");
        for (String d : dailies) {
            String[] attributes = d.split("&");
            String id = attributes[0];
            String date = attributes[1];
            int plan = Integer.parseInt(attributes[2]);
            list.add(new PomoDaily(id, date, plan, userId));
        }
        return list;
    }


    /**
     * Gets all tag by user ID.
     *
     * @param userId the user ID.
     * @return the list of PomoTag.
     * @since 1.0.0
     */
    public ArrayList<PomoTag> getTag(String userId) {
        PomoServer server = new PomoServer();
        String parameters = PomoServer.MODE_SELECT + "&" + "userid=" + userId;
        String url = PomoServer.DOMAIN_URL + PomoServer.SERVLET_TAG;
        String data = server.get(url, parameters);
        ArrayList<PomoTag> list = new ArrayList<>();
        if (data.equals("0")) {
            return list;
        }
        String[] tags = data.split(";");
        for (String t : tags) {
            String[] attributes = t.split("&");
            String id = attributes[0];
            String name = attributes[1];
            int plan = Integer.parseInt(attributes[2]);
            list.add(new PomoTag(id, name, plan, userId));
        }
        return list;
    }

    /**
     * Gets all pomo by user ID.
     *
     * @param userId the user ID.
     * @return the list of Pomodoro.
     * @since 1.0.0
     */
    public ArrayList<Pomodoro> getPomo(String userId) {
        PomoServer server = new PomoServer();
        String parameters = PomoServer.MODE_SELECT + "&" + "userid=" + userId;
        String url = PomoServer.DOMAIN_URL + PomoServer.SERVLET_POMO;
        String data = server.get(url, parameters);
        ArrayList<Pomodoro> list = new ArrayList<>();
        if (data.equals("0")) {
            return list;
        }
        String[] pomos = data.split(";");
        for (String p : pomos) {
            String[] attributes = p.split("&");
            String id = attributes[0];
            String tagId = attributes[1];
            String memo = attributes[2];
            String dailyId = attributes[3];
            String time = attributes[4];
            list.add(new Pomodoro(id, tagId, memo, dailyId, time, userId));
        }
        return list;
    }

}
