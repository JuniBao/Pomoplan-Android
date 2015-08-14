package com.rooftrellen.pomoplan.activity.other;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.rooftrellen.pomoplan.R;
import com.rooftrellen.pomoplan.activity.util.ExtraName;
import com.rooftrellen.pomoplan.db.PomoDbHelper;
import com.rooftrellen.pomoplan.model.Pomodoro;

/**
 * PomoActivity is an activity to show the detail of Pomodoro.
 *
 * @author Team FirstRow
 * @version 1.0.0
 */
public class PomoActivity extends Activity {

    /**
     * Initializes views.
     *
     * @param savedInstanceState the saved instance state.
     * @since 1.0.0
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomo);
        PomoDbHelper dbHelper = PomoDbHelper.getInstance(getApplicationContext());
        Intent data = getIntent();
        Pomodoro pomo = (Pomodoro) data.getSerializableExtra(ExtraName.POMODORO);
        ((TextView) findViewById(R.id.textDate)).setText(dbHelper.selectDailyDateById(pomo.getDailyId()));
        ((TextView) findViewById(R.id.textTime)).setText(pomo.getTime());
        ((TextView) findViewById(R.id.textTag)).setText(dbHelper.selectTagNameById(pomo.getTagId()));
        ((TextView) findViewById(R.id.textMemo)).setText(pomo.getMemo());
    }

}
