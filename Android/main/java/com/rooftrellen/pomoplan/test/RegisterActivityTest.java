package com.rooftrellen.pomoplan.test;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.EditText;

import com.rooftrellen.pomoplan.R;
import com.rooftrellen.pomoplan.activity.other.RegisterActivity;

/**
 * RegisterActivityTest is a test class for testing RegisterActivity.
 *
 * @author Team FirstRow
 * @version 1.0.0
 */
public class RegisterActivityTest extends ActivityInstrumentationTestCase2<RegisterActivity> {

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
     * The button for register.
     *
     * @since 1.0.0
     */
    private Button buttonRegister;

    /**
     * Initializes with class type.
     *
     * @since 1.0.0
     */
    public RegisterActivityTest() {
        super(RegisterActivity.class);
    }

    /**
     * Sets up RegisterActivity.
     *
     * @since 1.0.0
     */
    @Override
    public void setUp() throws Exception {
        Intent intent = new Intent(getInstrumentation().getTargetContext(), RegisterActivity.class);
        setActivityIntent(intent);
        RegisterActivity registerActivity = getActivity();
        editName = (EditText) registerActivity.findViewById(R.id.editName);
        editPassword = (EditText) registerActivity.findViewById(R.id.editPassword);
        buttonRegister = (Button) registerActivity.findViewById(R.id.buttonRegister);
    }

    /**
     * Tests register with wrong name.
     *
     * @since 1.0.0
     */
    @UiThreadTest
    public void testRegisterWithWrongName() {
        editName.setText("test");
        editPassword.setText("aaa");
        buttonRegister.performClick();
        assertEquals("", editName.getText().toString());
        assertEquals("", editPassword.getText().toString());
    }

    /**
     * Tests successful register.
     *
     * @since 1.0.0
     */
    @UiThreadTest
    public void testRegister() {
        editName.setText("test3");
        editPassword.setText("3");
        buttonRegister.performClick();
        assertEquals("test3", editName.getText().toString());
        assertEquals("3", editPassword.getText().toString());
    }

}
