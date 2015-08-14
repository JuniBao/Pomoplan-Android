package com.rooftrellen.pomoplan.activity.other;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.rooftrellen.pomoplan.R;
import com.rooftrellen.pomoplan.activity.util.ExtraName;
import com.rooftrellen.pomoplan.db.PomoDbHelper;
import com.rooftrellen.pomoplan.model.PomoTag;
import com.rooftrellen.pomoplan.model.Pomodoro;

/**
 * PlanActivity is an Activity of showing Pomodoro plan and history.
 *
 * @author Team FirstRow
 * @version 1.0.0
 */
public class TagActivity extends Activity {

    /**
     * The TextView for showing plan.
     *
     * @since 1.0.0
     */
    private TextView textPlan;

    /**
     * The tag's index in the list.
     *
     * @since 1.0.0
     */
    private int tagIndex;

    /**
     * The tag.
     *
     * @since 1.0.0
     */
    private PomoTag tag;

    /**
     * The database.
     *
     * @since 1.0.0
     */
    private PomoDbHelper dbHelper;

    /**
     * Initializes plan and history components.
     *
     * @param savedInstanceState the saved instance state.
     * @since 1.0.0
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);
        dbHelper = PomoDbHelper.getInstance(getApplicationContext());
        // Get TextView for plan
        textPlan = (TextView) findViewById(R.id.textPlan);
        // Get plan and completed Pomodoro
        Intent intent = getIntent();
        tag = (PomoTag) intent.getSerializableExtra(ExtraName.POMO_TAG);
        tagIndex = intent.getIntExtra(ExtraName.TAG_INDEX, -1);
        // Display plan and completed
        textPlan.setText(String.valueOf(tag.getPlan()));
        ((TextView) findViewById(R.id.textCompleted))
                .setText(String.valueOf(tag.getCompleted().size()));
        // Setup list view of history Pomodoro
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new PomoArrayAdapter(this, R.layout.row_pomo, tag.getCompleted()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Shows detail of each Pomodoro.
             *
             * @param parent the parent view.
             * @param view the current view.
             * @param position the position in the list.
             * @param id the row ID.
             * @since 1.0.0
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), PomoActivity.class);
                intent.putExtra(ExtraName.POMODORO, (Pomodoro) parent.getAdapter().getItem(position));
                startActivity(intent);
            }
        });
    }

    /**
     * Decreases plan when click.
     *
     * @param view the decrease button.
     * @since 1.0.0
     */
    public void decreasePlan(View view) {
        tag.setPlan(Math.max(0, tag.getPlan() - 1));
        textPlan.setText(String.valueOf(tag.getPlan()));
    }

    /**
     * Increases plan when click.
     *
     * @param view the increase button.
     * @since 1.0.0
     */
    public void increasePlan(View view) {
        tag.setPlan(tag.getPlan() + 1);
        textPlan.setText(String.valueOf(tag.getPlan()));
    }

    /**
     * Sends back update when back.
     *
     * @since 1.0.0
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(ExtraName.POMO_TAG, tag);
        intent.putExtra(ExtraName.TAG_INDEX, tagIndex);
        setResult(Activity.RESULT_OK, intent);
        super.onBackPressed();
    }

    /**
     * PomoHistoryAdapter is an ArrayAdapter for listing all Pomodoro in the history.
     *
     * @since 1.0.0
     */
    private class PomoArrayAdapter extends ArrayAdapter<Pomodoro> {

        /**
         * Initializes with context, and list of Pomodoro.
         *
         * @param context the context.
         * @param resource the layout resource.
         * @param completed the completed Pomodoro list.
         * @since 1.0.0
         */
        public PomoArrayAdapter(Context context, int resource, ArrayList<Pomodoro> completed) {
            super(context, resource, completed);
        }

        /**
         * Generates the view of each row.
         *
         * @param position the row position.
         * @param convertView the old view to reuse.
         * @param parent the parent view.
         * @return the view of row.
         * @since 1.0.0
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Generate view
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.row_pomo, parent, false);
            }
            // Get information from Pomodoro
            Pomodoro p = getItem(position);
            // Display
            ((TextView) convertView.findViewById(R.id.textDate))
                    .setText(String.valueOf(dbHelper.selectDailyDateById(p.getDailyId())));
            ((TextView) convertView.findViewById(R.id.textTime))
                    .setText(String.valueOf(p.getTime()));
            ((TextView) convertView.findViewById(R.id.textTag))
                    .setText(String.valueOf(tag.getName()));
            return convertView;
        }

    }

}
