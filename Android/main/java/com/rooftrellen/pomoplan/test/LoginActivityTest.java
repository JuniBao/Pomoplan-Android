package com.rooftrellen.pomoplan.test;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.EditText;

import com.rooftrellen.pomoplan.R;
import com.rooftrellen.pomoplan.activity.other.LoginActivity;

/**
 * LoginActivityTest is a test class for testing LoginActivity.
 *
 * @author Team FirstRow
 * @version 1.0.0
 */
public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    /**
     * The EditText for name.
     *
     * @since 1.0.0
     */
    private EditText editName;

    /**
     * The EditText for password.
     *
     * @since 1.0.0
     */
    private EditText editPassword;

    /**
     * The button for login.
     *
     * @since 1.0.0
     */
    private Button buttonLogin;

    /**
     * Initializes with class type.
     *
     * @since 1.0.0
     */
    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    /**
     * Sets up the LoginActivity.
     *
     * @since 1.0.0
     */
    @Override
    public void setUp() throws Exception {
        Intent intent = new Intent(getInstrumentation().getTargetContext(), LoginActivity.class);
        setActivityIntent(intent);
        LoginActivity loginActivity = getActivity();
        editName = (EditText) loginActivity.findViewById(R.id.editName);
        editPassword = (EditText) loginActivity.findViewById(R.id.editPassword);
        buttonLogin = (Button) loginActivity.findViewById(R.id.buttonLogin);
    }

    /**
     * Tests login with a wrong name.
     *
     * @since 1.0.0
     */
    @UiThreadTest
    public void testLoginWithWrongName() {
        editName.setText("aaa");
        editPassword.setText("aaa");
        buttonLogin.performClick();
        assertEquals("", editName.getText().toString());
        assertEquals("", editPassword.getText().toString());
    }

    /**
     * Tests login with a wrong password.
     *
     * @since 1.0.0
     */
    @UiThreadTest
    public void testLoginWithWrongPassword() {
        editName.setText("test");
        editPassword.setText("dd");
        buttonLogin.performClick();
        assertEquals("", editName.getText().toString());
        assertEquals("", editPassword.getText().toString());
    }

    /**
     * Tests successful login.
     *
     * @since 1.0.0
     */
    @UiThreadTest
    public void testLogin() {
        editName.setText("test");
        editPassword.setText("1");
        buttonLogin.performClick();
        assertEquals("test", editName.getText().toString());
        assertEquals("1", editPassword.getText().toString());
    }

}
