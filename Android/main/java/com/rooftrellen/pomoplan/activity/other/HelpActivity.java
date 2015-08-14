package com.rooftrellen.pomoplan.activity.other;

import android.app.Activity;
import android.os.Bundle;

import com.rooftrellen.pomoplan.R;

/**
 * HelpActivity is an Activity for showing help information.
 *
 * @author Team FirstRow
 * @version 1.0.0
 */
public class HelpActivity extends Activity {

    /**
     * Inflates view.
     *
     * @param savedInstanceState the saved instance state.
     * @since 1.0.0
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }

}
