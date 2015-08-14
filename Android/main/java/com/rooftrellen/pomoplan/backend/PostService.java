package com.rooftrellen.pomoplan.backend;

import android.app.IntentService;
import android.content.Intent;

import com.rooftrellen.pomoplan.activity.util.ExtraName;
import com.rooftrellen.pomoplan.model.PomoDaily;
import com.rooftrellen.pomoplan.model.PomoTag;
import com.rooftrellen.pomoplan.model.PomoUser;
import com.rooftrellen.pomoplan.model.Pomodoro;

/**
 * The PomoService is an IntentService for synchronizing database with backend.
 *
 * @author Team FirstRow
 * @version 1.0.0
 */
public class PostService extends IntentService {

    /**
     * Insert user mode.
     *
     * @since 1.0.0
     */
    public final static String INSERT_USER = "INSERTUSER";

    /**
     * Update user mode.
     *
     * @since 1.0.0
     */
    public final static String UPDATE_USER = "UPDATEUSER";

    /**
     * Delete user mode.
     *
     * @since 1.0.0
     */
    public final static String DELETE_USER = "DELETEUSER";

    /**
     * Insert daily mode.
     *
     * @since 1.0.0
     */
    public final static String INSERT_DAILY = "INSERTDAILY";

    /**
     * Update daily mode.
     *
     * @since 1.0.0
     */
    public final static String UPDATE_DAILY = "UPDATEDAILY";

    /**
     * Delete daily mode.
     *
     * @since 1.0.0
     */
    public final static String DELETE_DAILY = "DELETEDAILY";

    /**
     * Insert tag mode.
     *
     * @since 1.0.0
     */
    public final static String INSERT_TAG = "INSERTTAG";

    /**
     * Update tag mode.
     *
     * @since 1.0.0
     */
    public final static String UPDATE_TAG = "UPDATETAG";

    /**
     * Delete tag mode.
     *
     * @since 1.0.0
     */
    public final static String DELETE_TAG = "DELETETAG";

    /**
     * Insert pomo mode.
     *
     * @since 1.0.0
     */
    public final static String INSERT_POMO = "INSERTPOMO";

    /**
     * Delete pomo mode.
     *
     * @since 1.0.0
     */
    public final static String UPDATE_POMO = "UPDATEPOMO";

    /**
     * Delete pomo mode.
     *
     * @since 1.0.0
     */
    public final static String DELETE_POMO = "DELETEPOMO";

    /**
     * Default constructor.
     *
     * @since 1.0.0
     */
    public PostService() {
        super(PostService.class.getName());
    }

    /**
     * Handles different intents.
     *
     * @param intent the service intent.
     * @since 1.0.0
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        PomoServer server = new PomoServer();
        String type = intent.getStringExtra(ExtraName.SERVICE_TYPE);
        String url = "";
        String parameters = "";
        PomoUser user;
        PomoDaily daily;
        PomoTag tag;
        Pomodoro pomo;
        switch (type) {
            case INSERT_USER:
                url = PomoServer.DOMAIN_URL + PomoServer.SERVLET_USER;
                user = (PomoUser) intent.getSerializableExtra(ExtraName.SERVICE_OBJECT);
                parameters = PomoServer.MODE_INSERT + "&" + user;
                break;
            case UPDATE_USER:
                url = PomoServer.DOMAIN_URL + PomoServer.SERVLET_USER;
                user = (PomoUser) intent.getSerializableExtra(ExtraName.SERVICE_OBJECT);
                parameters = PomoServer.MODE_UPDATE + "&" + user;
                break;
            case DELETE_USER:
                url = PomoServer.DOMAIN_URL + PomoServer.SERVLET_USER;
                user = (PomoUser) intent.getSerializableExtra(ExtraName.SERVICE_OBJECT);
                parameters = PomoServer.MODE_DELETE + "&" + user;
                break;
            case INSERT_DAILY:
                url = PomoServer.DOMAIN_URL + PomoServer.SERVLET_DAILY;
                daily = (PomoDaily) intent.getSerializableExtra(ExtraName.SERVICE_OBJECT);
                parameters = PomoServer.MODE_INSERT + "&" + daily;
                break;
            case UPDATE_DAILY:
                url = PomoServer.DOMAIN_URL + PomoServer.SERVLET_DAILY;
                daily = (PomoDaily) intent.getSerializableExtra(ExtraName.SERVICE_OBJECT);
                parameters = PomoServer.MODE_UPDATE + "&" + daily;
                break;
            case DELETE_DAILY:
                url = PomoServer.DOMAIN_URL + PomoServer.SERVLET_DAILY;
                daily = (PomoDaily) intent.getSerializableExtra(ExtraName.SERVICE_OBJECT);
                parameters = PomoServer.MODE_DELETE + "&" + daily;
                break;
            case INSERT_TAG:
                url = PomoServer.DOMAIN_URL + PomoServer.SERVLET_TAG;
                tag = (PomoTag) intent.getSerializableExtra(ExtraName.SERVICE_OBJECT);
                parameters = PomoServer.MODE_INSERT + "&" + tag;
                break;
            case UPDATE_TAG:
                url = PomoServer.DOMAIN_URL + PomoServer.SERVLET_TAG;
                tag = (PomoTag) intent.getSerializableExtra(ExtraName.SERVICE_OBJECT);
                parameters = PomoServer.MODE_UPDATE + "&" + tag;
                break;
            case DELETE_TAG:
                url = PomoServer.DOMAIN_URL + PomoServer.SERVLET_TAG;
                tag = (PomoTag) intent.getSerializableExtra(ExtraName.SERVICE_OBJECT);
                parameters = PomoServer.MODE_DELETE + "&" + tag;
                break;
            case INSERT_POMO:
                url = PomoServer.DOMAIN_URL + PomoServer.SERVLET_POMO;
                pomo = (Pomodoro) intent.getSerializableExtra(ExtraName.SERVICE_OBJECT);
                parameters = PomoServer.MODE_INSERT + "&" + pomo;
                break;
            case UPDATE_POMO:
                url = PomoServer.DOMAIN_URL + PomoServer.SERVLET_POMO;
                pomo = (Pomodoro) intent.getSerializableExtra(ExtraName.SERVICE_OBJECT);
                parameters = PomoServer.MODE_UPDATE + "&" + pomo;
                break;
            case DELETE_POMO:
                url = PomoServer.DOMAIN_URL + PomoServer.SERVLET_POMO;
                pomo = (Pomodoro) intent.getSerializableExtra(ExtraName.SERVICE_OBJECT);
                parameters = PomoServer.MODE_DELETE + "&" + pomo;
                break;
        }
        server.post(url, parameters);
    }

}
