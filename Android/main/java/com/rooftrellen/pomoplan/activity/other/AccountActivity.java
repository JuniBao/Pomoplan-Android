package com.rooftrellen.pomoplan.activity.other;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.rooftrellen.pomoplan.R;
import com.rooftrellen.pomoplan.activity.util.ExtraName;
import com.rooftrellen.pomoplan.model.PomoUser;

/**
 * AccountActivity is an activity for showing user information and logout.
 *
 * @author Team FirstRow
 * @version 1.0.0
 */
public class AccountActivity extends Activity {

    /**
     * Inflates the view.
     *
     * @param savedInstanceState the saved instance state.
     * @since 1.0.0
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        PomoUser user = (PomoUser) getIntent().getSerializableExtra(ExtraName.POMO_USER);
        ((TextView) findViewById(R.id.textName)).setText(user.getName());
    }

    /**
     * Logs out.
     *
     * @param view the button.
     * @since 1.0.0
     */
    public void logout(View view) {
        Intent intent = new Intent();
        intent.putExtra(ExtraName.LOG_OUT, true);
        setResult(Activity.RESULT_OK, intent);
        super.onBackPressed();
    }

    /**
     * Returns to the previous activity.
     *
     * @since 1.0.0
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(ExtraName.LOG_OUT, false);
        setResult(Activity.RESULT_OK, intent);
        super.onBackPressed();
    }

}
