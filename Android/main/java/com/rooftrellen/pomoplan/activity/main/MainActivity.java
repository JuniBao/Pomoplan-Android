package com.rooftrellen.pomoplan.activity.main;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.rooftrellen.pomoplan.R;
import com.rooftrellen.pomoplan.activity.other.AccountActivity;
import com.rooftrellen.pomoplan.activity.other.HelpActivity;
import com.rooftrellen.pomoplan.activity.other.LoginActivity;
import com.rooftrellen.pomoplan.activity.other.RegisterActivity;
import com.rooftrellen.pomoplan.activity.other.SettingActivity;
import com.rooftrellen.pomoplan.activity.util.CustomViewPager;
import com.rooftrellen.pomoplan.activity.util.ExtraName;
import com.rooftrellen.pomoplan.db.PomoDbHelper;
import com.rooftrellen.pomoplan.model.PomoUser;

/**
 * MainActivity is the parent of three fragments and controller of the application.
 *
 * @author Team FirstRow
 * @version 1.0.0
 */
public class MainActivity extends Activity implements ActionBar.TabListener {

    /**
     * The current user.
     *
     * @since 1.0.0
     */
    private PomoUser user;

    /**
     * The guest.
     *
     * @since 1.0.0
     */
    private PomoUser guest;

    /**
     * The fragment container.
     *
     * @since 1.0.0
     */
    private CustomViewPager viewPager;

    /**
     * The action bar.
     *
     * @since 1.0.0
     */
    private ActionBar actionBar;

    /**
     * The PomoFragment.
     *
     * @since 1.0.0
     */
    private PomoFragment pomoFragment;

    /**
     * The DailyFragment..
     *
     * @since 1.0.0
     */
    private DailyFragment dailyFragment;

    /**
     * The TagFragment..
     *
     * @since 1.0.0
     */
    private TagFragment tagFragment;

    /**
     * The database.
     *
     * @since 1.0.0
     */
    private PomoDbHelper dbHelper;

    /**
     * The flag of timer.
     *
     * @since 1.0.0
     */
    private boolean timerOn;

    /**
     * The setting activity request code.
     *
     * @since 1.0.0
     */
    private final static int SETTING_CODE = 0;

    /**
     * The login/register activity request code.
     *
     * @since 1.0.0
     */
    private final static int LOGIN_CODE = 1;

    /**
     * The account activity request code.
     *
     * @since 1.0.0
     */
    private final static int LOGOUT_CODE = 2;

    /**
     * Initializes the application.
     *
     * @param savedInstanceState the saved instance state.
     * @since 1.0.0
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerOn = false;
        guest = new PomoUser();
        user = null;
        // Setup database
        dbHelper = PomoDbHelper.getInstance(getApplicationContext());
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        viewPager = (CustomViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        // Set up the action bar
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        for (int i = 0; i < pagerAdapter.getCount(); i++) {
            actionBar.addTab(actionBar.newTab().setText(pagerAdapter.getPageTitle(i))
                    .setTabListener(this));
        }
        // Setup view pager
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            /**
             * Returns the corresponding fragment.
             *
             * @since 1.0.0
             */
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });
        // Reset view
        logoutUpdate();
    }

    /**
     * Updates menu when needed.
     *
     * @param menu the menu.
     * @return the success flag.
     * @since 1.0.0
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // No menu when timer is on
        if (timerOn) {
            return false;
        } else {
            menu.clear();
            MenuInflater inflater = getMenuInflater();
            if(user == null) {
                inflater.inflate(R.menu.menu_guest, menu);
            }else{
                inflater.inflate(R.menu.menu_user, menu);
            }
            return super.onPrepareOptionsMenu(menu);
        }
    }

    /**
     * Opens menu item with corresponding activity.
     *
     * @param item the menu item.
     * @return the success flag.
     * @since 1.0.0
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id) {
            // Account activity
            case R.id.action_account:
                intent = new Intent(this, AccountActivity.class);
                intent.putExtra(ExtraName.POMO_USER, user);
                startActivityForResult(intent, LOGOUT_CODE);
                break;
            // Setting activity
            case R.id.action_setting:
                intent = new Intent(this, SettingActivity.class);
                if (user == null) {
                    intent.putExtra(ExtraName.POMO_USER, guest);
                } else {
                    intent.putExtra(ExtraName.POMO_USER, user);
                }
                startActivityForResult(intent, SETTING_CODE);
                break;
            // Login activity
            case R.id.action_login:
                intent = new Intent(this, LoginActivity.class);
                startActivityForResult(intent, LOGIN_CODE);
                break;
            // Register activity
            case R.id.action_register:
                intent = new Intent(this, RegisterActivity.class);
                startActivityForResult(intent, LOGIN_CODE);
                break;
            // Help activity
            case R.id.action_help:
                intent = new Intent(this, HelpActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Processes menu result.
     *
     * @param requestCode the activity request code.
     * @param resultCode the activiy result code.
     * @param data the return data.
     * @since 1.0.0
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // Setting activity
            case SETTING_CODE:
                if (user == null) {
                    guest = (PomoUser) data.getSerializableExtra(ExtraName.POMO_USER);
                } else {
                    user = (PomoUser) data.getSerializableExtra(ExtraName.POMO_USER);
                    dbHelper.updateUser(user);
                }
                pomoFragment.resetTimer();
                break;
            // Login/register activity
            case LOGIN_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    user = (PomoUser) data.getSerializableExtra(ExtraName.POMO_USER);
                    loginUpdate();
                    pomoFragment.resetTimer();
                    pomoFragment.loginUpdate();
                    dailyFragment.loginUpdate(user);
                    tagFragment.loginUpdate(user);
                }
                break;
            // Account activity
            case LOGOUT_CODE:
                if (data.getBooleanExtra(ExtraName.LOG_OUT, false)) {
                    user = null;
                    logoutUpdate();
                    pomoFragment.resetTimer();
                    pomoFragment.logoutUpdate();
                    dbHelper.reopen();
                }
                break;
        }
    }

    /**
     * Switches to the corresponding fragment when selected.
     *
     * @param tab the tab.
     * @param fragmentTransaction the fragment transaction.
     * @since 1.0.0
     */
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    /**
     * Blank unselected.
     *
     * @param tab the tab.
     * @param fragmentTransaction the fragment transaction.
     * @since 1.0.0
     */
    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * Blank reselected.
     *
     * @param tab the tab.
     * @param fragmentTransaction the fragment transaction.
     * @since 1.0.0
     */
    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * Gets the current user.
     *
     * @return the current user.'
     * @since 1.0.0
     */
    public PomoUser getUser() {
        return user;
    }

    /**
     * Gets the DailyFragment.
     *
     * @return the DailyFragment.
     * @since 1.0.0
     */
    public PomoFragment getPomoFragment() {
        return pomoFragment;
    }

    /**
     * Gets the DailyFragment.
     *
     * @return the DailyFragment.
     * @since 1.0.0
     */
    public DailyFragment getDailyFragment() {
        return dailyFragment;
    }

    /**
     * Gets the TagFragment.
     *
     * @return the TagFragment.
     */
    public TagFragment getTagFragment() {
        return tagFragment;
    }

    /**
     * Gets the database.
     *
     * @return the database.
     * @since 1.0.0
     */
    public PomoDbHelper getDbHelper() {
        return dbHelper;
    }

    /**
     * Gets the WiFi option.
     *
     * @return the WiFi option.
     * @since 1.0.0
     */
    public boolean getWifiOff() {
        if (user == null) {
            return guest.getWifiOff();
        } else {
            return user.getWifiOff();
        }
    }

    /**
     * Gets the bluetooth option.
     *
     * @return the WiFi option.
     * @since 1.0.0
     */
    public boolean getBluetoothOff() {
        if (user == null) {
            return guest.getBluetoothOff();
        } else {
            return user.getBluetoothOff();
        }
    }

    /**
     * Gets the Pomodoro duration.
     *
     * @return the Pomodoro duration.
     * @since 1.0.0
     */
    public int getPomoDuration() {
        if (user == null) {
            return guest.getPomoDuration();
        } else {
            return user.getPomoDuration();
        }
    }

    /**
     * Gets the Break duration.
     *
     * @return the Pomodoro duration.
     * @since 1.0.0
     */
    public int getBreakDuration() {
        if (user == null) {
            return guest.getBreakDuration();
        } else {
            return user.getBreakDuration();
        }
    }

    /**
     * Sets the timer flag.
     *
     * @param timerOn the timer flag.
     * @since 1.0.0
     */
    public void setTimerOn(boolean timerOn) {
        this.timerOn = timerOn;
    }

    /**
     * Updates view when login.
     *
     * @since 1.0.0
     */
    private void loginUpdate() {
        viewPager.setPagingEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setSelectedNavigationItem(0);
    }

    /**
     * Updates view when logout.
     *
     * @since 1.0.0
     */
    private void logoutUpdate() {
        viewPager.setPagingEnabled(false);
        actionBar.setSelectedNavigationItem(0);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
    }

    /**
     * Gets the current time.
     *
     * @return the current time.
     * @since 1.0.0
     */
    public Calendar getTime() {
        SimpleTimeZone pdt = new SimpleTimeZone(-8 * 60 * 60 * 1000, TimeZone.getAvailableIDs(-8 * 60 * 60 * 1000)[0]);
        pdt.setStartRule(Calendar.APRIL, 1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
        pdt.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
        Calendar calendar = new GregorianCalendar(pdt);
        calendar.setTime(new Date());
        return calendar;
    }

    /**
     * SectionsPagerAdapter is a class for containing fragments.
     *
     * @since 1.0.0
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        /**
         * Initializes with fragment manager.
         *
         * @param fm the fragment manager.
         * @since 1.0.0
         */
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Gets the fragment with position.
         *
         * @param position the position.
         * @return the fragment.
         * @since 1.0.0
         */
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                // PomoFragment
                case 0:
                    pomoFragment = PomoFragment.newInstance();
                    return pomoFragment;
                // DailyFragment
                case 1:
                    dailyFragment = DailyFragment.newInstance();
                    return dailyFragment;
                // TagFragment
                case 2:
                    tagFragment = TagFragment.newInstance();
                    return tagFragment;
            }
            return null;
        }

        /**
         * Gets the count of fragments.
         *
         * @return the count.
         * @since 1.0.0
         */
        @Override
        public int getCount() {
            return 3;
        }

        /**
         * Gets the page title.
         *
         * @param position the position.
         * @return the page title.
         * @since 1.0.0
         */
        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_fragment_pomo).toUpperCase(l);
                case 1:
                    return getString(R.string.title_fragment_daily).toUpperCase(l);
                case 2:
                    return getString(R.string.title_fragment_tag).toUpperCase(l);
            }
            return null;
        }

    }

}
