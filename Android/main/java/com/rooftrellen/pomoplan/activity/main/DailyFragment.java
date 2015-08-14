package com.rooftrellen.pomoplan.activity.main;

import java.util.Calendar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.rooftrellen.pomoplan.R;
import com.rooftrellen.pomoplan.activity.other.DailyActivity;
import com.rooftrellen.pomoplan.activity.util.ExtraName;
import com.rooftrellen.pomoplan.db.PomoDbHelper;
import com.rooftrellen.pomoplan.model.PomoDaily;
import com.rooftrellen.pomoplan.model.PomoUser;
import com.rooftrellen.pomoplan.model.Pomodoro;

/**
 * DailyFragment is a Fragment for showing the daily status.
 *
 * @author Team FirstRow
 * @version 1.0.0
 */
public class DailyFragment extends Fragment {

    /**
     * The parent Activity.
     *
     * @since 1.0.0
     */
    private MainActivity mainActivity;

    /**
     * The database connection.
     *
     * @since 1.0.0
     */
    private PomoDbHelper dbHelper;

    /**
     * The root View's context.
     *
     * @since 1.0.0
     */
    private Context context;

    /**
     * The history table.
     *
     * @since 1.0.0
     */
    private TableLayout history;

    /**
     * The date picker.
     *
     * @since 1.0.0
     */
    private DatePicker datePicker;

    /**
     * The picked year.
     *
     * @since 1.0.0
     */
    private int year;

    /**
     * The picked month.
     *
     * @since 1.0.0
     */
    private int month;

    /**
     * The picked day.
     *
     * @since 1.0.0
     */
    private int day;

    /**
     * The PomoDaily of today.
     *
     * @since 1.0.0
     */
    private PomoDaily today;

    /**
     * The PomoDaily of picked date.
     *
     * @since 1.0.0
     */
    private PomoDaily daily;

    /**
     * The user ID.
     *
     * @since 1.0.0
     */
    String userId;

    /**
     * The display date format.
     *
     * @since 1.0.0
     */
    private final static String DATE_FORMAT = "%d/%d/%d";

    /**
     * Pomodoro per row.
     *
     * @since 1.0.0
     */
    private final static int POMO_PER_ROW = 6;

    /**
     * Required empty constructor.
     *
     * @since 1.0.0
     */
    public DailyFragment() {}

    /**
     * Creates a new instance of DailyFragment.
     *
     * @return the new instance.
     * @since 1.0.0
     */
    public static DailyFragment newInstance() {
        return new DailyFragment();
    }

    /**
     * Gets the parent Activity and current time and database connection.
     *
     * @param savedInstanceState the saved instance state.
     * @since 1.0.0
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        dbHelper = mainActivity.getDbHelper();
    }

    /**
     * Sets up root view.
     *
     * @param inflater the inflater.
     * @param container the container.
     * @param savedInstanceState the saved instance state.
     * @return the root view.
     * @since 1.0.0
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_daily, container, false);
        context = root.getContext();
        // Set up history
        history = (TableLayout) root.findViewById(R.id.history);
        history.setOnClickListener(new View.OnClickListener() {
            /**
             * Shows daily detail when click.
             *
             * @param view the view.
             * @since 1.0.0
             */
            @Override
            public void onClick(View view) {
                if (daily == null) {
                    String date = String.format(DATE_FORMAT, month + 1, day, year);
                    daily = new PomoDaily(date, userId);
                    dbHelper.insertDaily(daily, true);
                }
                Intent intent = new Intent(view.getContext(), DailyActivity.class);
                intent.putExtra(ExtraName.POMO_DAILY, daily);
                startActivityForResult(intent, 1);
            }
        });
        datePicker = (DatePicker) root.findViewById(R.id.datePicker);
        return root;
    }

    /**
     * Updates daily plan changes.
     *
     * @param requestCode the request code.
     * @param resultCode the result code.
     * @param data the intent data.
     * @since 1.0.0
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        daily = (PomoDaily) data.getSerializableExtra(ExtraName.POMO_DAILY);
        dbHelper.updateDaily(daily);
        if (daily.getId().equals(today.getId())) {
            today = daily;
        }
        drawHistory();
    }

    /**
     * Updates user view after login.
     *
     * @param user the user.
     * @since 1.0.0
     */
    public void loginUpdate(PomoUser user) {
        // Get ID
        userId = user.getId();
        Calendar calendar = mainActivity.getTime();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        String date = String.format(DATE_FORMAT, month + 1, day, year);
        // Get completed and plan
        today = dbHelper.selectDailyByDate(date, userId);
        if (today == null) {
            today = new PomoDaily(date, userId);
            dbHelper.insertDaily(today, true);
        }
        daily = today;
        // Draw history
        drawHistory();
        // Reset date picker
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            /**
             * Updates data when date change.
             *
             * @param view the view.
             * @param changedYear the changed year.
             * @param monthOfYear the changed month.
             * @param dayOfMonth the changed day.
             * @since 1.0.0
             */
            @Override
            public void onDateChanged(DatePicker view, int changedYear, int monthOfYear, int dayOfMonth) {
                // Update picked date
                year = changedYear;
                month = monthOfYear;
                day = dayOfMonth;
                // Update completed and plan
                String date = String.format(DATE_FORMAT, month + 1, day, year);
                if (date.equals(today.getDate())) {
                    daily = today;
                } else {
                    daily = dbHelper.selectDailyByDate(date, userId);
                }
                // Draw history
                drawHistory();
            }
        });
    }

    /**
     * Adds today's new Pomodoro if necessary.
     *
     * @param pomo the new Pomodoro
     * @since 1.0.0
     */
    public void addPomo(Pomodoro pomo) {
        today.getCompleted().add(pomo);
        drawHistory();
    }

    /**
     * Draws the Pomodoro in history.
     *
     * @since 1.0.0
     */
    public void drawHistory() {
        // Remove old data
        history.removeAllViews();
        // Get statistics
        int completeNum;
        int incompleteNum;
        if (daily == null) {
            completeNum = 0;
            incompleteNum = 0;
        } else {
            completeNum = daily.getCompleted().size();
            incompleteNum = Math.max(daily.getPlan() - completeNum, 0);
        }
        if (completeNum == 0 && incompleteNum == 0) {
            // No Pomodoro
            TableRow tableRow = (TableRow) View.inflate(context, R.layout.row_daily, null);
            ImageView completed = (ImageView) View.inflate(context, R.layout.image_add, null);
            tableRow.addView(completed);
            history.addView(tableRow);
        } else {
            int row = (completeNum + incompleteNum) / POMO_PER_ROW;
            int remainder = (completeNum + incompleteNum) % POMO_PER_ROW;
            if (remainder != 0) {
                row++;
            }
            // Draw rows
            for (int i = 0; i < row; i++) {
                TableRow tableRow = (TableRow) View.inflate(context, R.layout.row_daily, null);
                for (int j = 0 ; j < POMO_PER_ROW; j++) {
                    if (completeNum > 0) {
                        completeNum--;
                        ImageView completed = (ImageView) View.inflate(context, R.layout.image_complete, null);
                        tableRow.addView(completed);
                    } else if (incompleteNum > 0) {
                        incompleteNum--;
                        ImageView incomplete = (ImageView) View.inflate(context, R.layout.image_incomplete, null);
                        tableRow.addView(incomplete);
                    }
                }
                history.addView(tableRow);
            }
        }
    }

}
