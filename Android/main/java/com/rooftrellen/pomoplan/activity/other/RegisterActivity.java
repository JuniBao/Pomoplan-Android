package com.rooftrellen.pomoplan.activity.other;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.rooftrellen.pomoplan.R;
import com.rooftrellen.pomoplan.activity.util.ExtraName;
import com.rooftrellen.pomoplan.db.PomoDbHelper;
import com.rooftrellen.pomoplan.model.PomoUser;

/**
 * RegisterActivity is an Activity of controlling user register.
 *
 * @author Team FirstRow
 * @version 1.0.0
 */
public class RegisterActivity extends Activity {

    /**
     * The database helper.
     *
     * @since 1.0.0
     */
    private PomoDbHelper dbHelper;

    /**
     * The user name input view.
     *
     * @since 1.0.0
     */
    private EditText editName;

    /**
     * The password input view.
     *
     * @since 1.0.0
     */
    private EditText editPassword;

    /**
     * Initializes database and views.
     *
     * @param savedInstanceState the saved instance state.
     * @since 1.0.0
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dbHelper = PomoDbHelper.getInstance(getApplicationContext());
        editName = (EditText) findViewById(R.id.editName);
        editPassword = (EditText) findViewById(R.id.editPassword);
    }

    /**
     * Attempts to register.
     *
     * @param view the view.
     * @since 1.0.0
     */
    public void attemptRegister(View view) {
        String name = editName.getText().toString();
        if (dbHelper.selectUserByName(name)) {
            editName.setText("");
            editPassword.setText("");
            Toast.makeText(view.getContext(), "Exist.", Toast.LENGTH_SHORT).show();
        } else {
            PomoUser user = new PomoUser(name, editPassword.getText().toString());
            dbHelper.insertUser(user, true);
            Intent i = new Intent();
            i.putExtra(ExtraName.POMO_USER, user);
            setResult(Activity.RESULT_OK, i);
            Toast.makeText(view.getContext(), "Registered.", Toast.LENGTH_SHORT).show();
            super.onBackPressed();
        }
    }

    /**
     * Return to the previous activity.
     *
     * @since 1.0.0
     */
    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        setResult(Activity.RESULT_CANCELED, i);
        super.onBackPressed();
    }

}
