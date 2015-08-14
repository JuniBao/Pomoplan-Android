package com.rooftrellen.pomoplan.backend;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * PomoServer is a class for describing backend server.
 *
 * @author Team FirstRow
 * @version 1.0.0
 */
public class PomoServer {

    /**
     * Backend URL.
     *
     * @since 1.0.0
     */
    public final static String DOMAIN_URL = "http://10.0.17.186:8080/PomoWebService/";

    /**
     * PomoUser table servlet.
     *
     * @since 1.0.0
     */
    public final static String SERVLET_USER = "user";

    /**
     * PomoDaily table servlet.
     *
     * @since 1.0.0
     */
    public final static String SERVLET_DAILY = "daily";

    /**
     * PomoTag table servlet.
     *
     * @since 1.0.0
     */
    public final static String SERVLET_TAG = "tag";

    /**
     * Pomodoro table servlet.
     *
     * @since 1.0.0
     */
    public final static String SERVLET_POMO = "pomo";

    /**
     * Select mode.
     *
     * @since 1.0.0
     */
    public final static String MODE_SELECT = "mode=select";

    /**
     * Insert mode.
     *
     * @since 1.0.0
     */
    public final static String MODE_INSERT = "mode=insert";

    /**
     * Update mode.
     *
     * @since 1.0.0
     */
    public final static String MODE_UPDATE = "mode=update";

    /**
     * Delete mode.
     *
     * @since 1.0.0
     */
    public final static String MODE_DELETE = "mode=delete";

    /**
     * Sends GET through HTTP.
     *
     * @param url the HTTP URL.
     * @param parameters the GET parameters.
     * @since 1.0.0
     */
    public String get(String url, String parameters) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url+"?"+parameters).openConnection();
            connection.setRequestMethod("GET");
            int status = connection.getResponseCode();
            Log.i("STATUS", String.valueOf(status));
            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String data;
            StringBuilder response = new StringBuilder();
            while ((data = input.readLine()) != null) {
                response.append(data);
            }
            input.close();
            connection.disconnect();
            data = response.toString();
            return data;
        } catch (Exception e) {
            Log.e("HTTP", "Connection.");
            return null;
        }
    }

    /**
     * Sends POST through HTTP.
     *
     * @param url the HTTP URL.
     * @param parameters the POST parameters.
     * @since 1.0.0
     */
    public void post(String url, String parameters) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url+"?"+parameters).openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            int status = connection.getResponseCode();
            Log.i("STATUS", String.valueOf(status));
            connection.disconnect();
        } catch (Exception e) {
            Log.e("HTTP", "Connection.");
        }
    }

}
