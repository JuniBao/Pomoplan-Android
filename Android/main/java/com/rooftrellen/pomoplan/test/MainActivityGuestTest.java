package com.rooftrellen.pomoplan.test;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;

import com.rooftrellen.pomoplan.R;
import com.rooftrellen.pomoplan.activity.main.MainActivity;
import com.rooftrellen.pomoplan.activity.other.HelpActivity;
import com.rooftrellen.pomoplan.activity.other.LoginActivity;
import com.rooftrellen.pomoplan.activity.other.RegisterActivity;
import com.rooftrellen.pomoplan.activity.other.SettingActivity;

/**
 * MainActivityGuestTest ia a test class for testing MainActivity and three fragments in guest mode.
 *
 * @author Team FirstRow
 * @version 1.0.0
 */
public class MainActivityGuestTest extends ActivityInstrumentationTestCase2<MainActivity> {

    /**
     * The main activity.
     *
     * @since 1.0.0
     */
    private MainActivity mainActivity;

    /**
     * Initializes with class type.
     *
     * @since 1.0.0
     */
    public MainActivityGuestTest() {
        super(MainActivity.class);
    }

    /**
     * Sets up the main activity.
     *
     * @since 1.0.0
     */
    @Override
    public void setUp() throws Exception{
        super.setUp();
        mainActivity = getActivity();
    }

    /**
     * Tests PomoFragment is not null.
     *
     * @since 1.0.0
     */
    public void testPomoFragmentNotNull() {
        assertNotNull(mainActivity.getPomoFragment());
    }

    /**
     * Tests DailyFragment is not null.
     *
     * @since 1.0.0
     */
    public void testDailyFragmentNotNull() {
        assertNotNull(mainActivity.getDailyFragment());
    }

    /**
     * Tests TagFragment is not null.
     *
     * @since 1.0.0
     */
    public void testTagFragmentNotNull() {
        assertNotNull(mainActivity.getTagFragment());
    }

    /**
     * Tests database is not null.
     *
     * @since 1.0.0
     */
    public void testDbNotNull() {
        assertNotNull(mainActivity.getDbHelper());
    }

    /**
     * Tests user is null.
     *
     * @since 1.0.0
     */
    public void testUserNull() {
        assertNull(mainActivity.getUser());
    }

    /**
     * Tests the guest's WiFi option.
     *
     * @since 1.0.0
     */
    public void testGuestWiFiSetting() {
        assertEquals(mainActivity.getWifiOff(), false);
    }

    /**
     * Tests the guest's bluetooth option.
     *
     * @since 1.0.0
     */
    public void testGuestBluetoothSetting() {
        assertEquals(mainActivity.getBluetoothOff(), false);
    }

    /**
     * Tests the guest's Pomodoro duration.
     *
     * @since 1.0.0
     */
    public void testGuestPomoDuration() {
        assertEquals(mainActivity.getPomoDuration(), 1);
    }

    /**
     * Tests the guest's break duration.
     *
     * @since 1.0.0
     */
    public void testGuestBreakDuration() {
        assertEquals(mainActivity.getBreakDuration(), 1);
    }

    /**
     * Tests calling LoginActivity.
     *
     * @since 1.0.0
     */
    public void testLoginActivity() {
        Instrumentation.ActivityMonitor am = getInstrumentation()
                .addMonitor(LoginActivity.class.getName(), null, false);
        getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_MENU);
        getInstrumentation().invokeMenuActionSync(mainActivity, R.id.action_login, 0);
        Activity a = getInstrumentation().waitForMonitorWithTimeout(am, 1000);
        assertEquals(true, getInstrumentation().checkMonitorHit(am, 1));
        a.finish();
    }

    /**
     * Tests calling RegisterActivity.
     *
     * @since 1.0.0
     */
    public void testRegisterActivity() {
        Instrumentation.ActivityMonitor am = getInstrumentation().addMonitor(RegisterActivity.class.getName(), null, false);

        getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_MENU);
        getInstrumentation().invokeMenuActionSync(mainActivity, R.id.action_register, 0);

        Activity a = getInstrumentation().waitForMonitorWithTimeout(am, 1000);
        assertEquals(true, getInstrumentation().checkMonitorHit(am, 1));
        a.finish();
    }

    /**
     * Tests calling SettingActivity.
     *
     * @since 1.0.0
     */
    public void testSettingActivity() {
        Instrumentation.ActivityMonitor am = getInstrumentation().addMonitor(SettingActivity.class.getName(), null, false);

        getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_MENU);
        getInstrumentation().invokeMenuActionSync(mainActivity, R.id.action_setting, 0);

        Activity a = getInstrumentation().waitForMonitorWithTimeout(am, 1000);
        assertEquals(true, getInstrumentation().checkMonitorHit(am, 1));
        a.finish();
    }

    /**
     * Tests calling HelpActivity.
     *
     * @since 1.0.0
     */
    public void testHelpActivity() {
        Instrumentation.ActivityMonitor am = getInstrumentation().addMonitor(HelpActivity.class.getName(), null, false);

        getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_MENU);
        getInstrumentation().invokeMenuActionSync(mainActivity, R.id.action_help, 0);

        Activity a = getInstrumentation().waitForMonitorWithTimeout(am, 1000);
        assertEquals(true, getInstrumentation().checkMonitorHit(am, 1));
        a.finish();
    }

}
