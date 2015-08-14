package com.rooftrellen.pomoplan.test;

import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.ListView;
import android.widget.TableLayout;

import com.rooftrellen.pomoplan.R;
import com.rooftrellen.pomoplan.activity.main.MainActivity;
import com.rooftrellen.pomoplan.activity.util.ExtraName;
import com.rooftrellen.pomoplan.model.PomoUser;

/**
 * MainActivityUserTest is a test class for testing MainActivity and three fragments in user mode.
 *
 * @author Team FirstRow
 * @version 1.0.0
 */
public class MainActivityUserTest extends ActivityInstrumentationTestCase2<MainActivity> {

    /**
     * The main acitivity.
     *
     * @since 1.0.0
     */
    private MainActivity mainActivity;

    /**
     * Initializes with class type.
     *
     * @since 1.0.0
     */
    public MainActivityUserTest() {
        super(MainActivity.class);
    }

    /**
     * Sets up the main activity.
     *
     * @since 1.0.0
     */
    @Override
    public void setUp() throws Exception {
        super.setUp();
        mainActivity = getActivity();
    }

    /**
     * Tests the user is not null.
     *
     * @since 1.0.0
     */
    @UiThreadTest
    public void testUserNotNull() {
        Intent loginIntent = new Intent();
        PomoUser user = new PomoUser("9a36d2f9018aacde1430411867961", "test", "1", false, true, 1, 1);
        loginIntent.putExtra(ExtraName.POMO_USER, user);
        mainActivity.onActivityResult(1, Activity.RESULT_OK, loginIntent);
        assertNotNull(mainActivity.getUser());
    }

    /**
     * Tests the user is null after logout.
     *
     * @since 1.0.0
     */
    @UiThreadTest
    public void testLogout() {
        Intent loginIntent = new Intent();
        PomoUser user = new PomoUser("9a36d2f9018aacde1430411867961", "test", "1", false, true, 1, 1);
        loginIntent.putExtra(ExtraName.POMO_USER, user);
        mainActivity.onActivityResult(1, Activity.RESULT_OK, loginIntent);
        Intent logoutIntent = new Intent();
        logoutIntent.putExtra(ExtraName.LOG_OUT, true);
        mainActivity.onActivityResult(2, Activity.RESULT_OK, logoutIntent);
        assertNull(mainActivity.getUser());
    }

    /**
     * Tests the number of daily history in DailyFragment.
     *
     * @since 1.0.0
     */
    @UiThreadTest
    public void testDailyHistory() {
        Intent loginIntent = new Intent();
        PomoUser user = new PomoUser("9a36d2f9018aacde1430411867961", "test", "1", false, true, 1, 1);
        loginIntent.putExtra(ExtraName.POMO_USER, user);
        mainActivity.onActivityResult(1, Activity.RESULT_OK, loginIntent);
        assertTrue(((TableLayout) mainActivity.findViewById(R.id.history)).getChildCount() > 0);
    }

    /**
     * Tests the number of tags in TagFragment.
     *
     * @since 1.0.0
     */
    @UiThreadTest
    public void testTagList() {
        Intent loginIntent = new Intent();
        PomoUser user = new PomoUser("9a36d2f9018aacde1430411867961", "test", "1", false, true, 1, 1);
        loginIntent.putExtra(ExtraName.POMO_USER, user);
        mainActivity.onActivityResult(1, Activity.RESULT_OK, loginIntent);
        assertTrue(((ListView) mainActivity.findViewById(R.id.listView)).getAdapter().getCount() > 0);
    }

}
