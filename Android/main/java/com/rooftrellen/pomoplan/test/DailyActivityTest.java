package com.rooftrellen.pomoplan.test;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.TextView;

import com.rooftrellen.pomoplan.R;
import com.rooftrellen.pomoplan.activity.other.DailyActivity;
import com.rooftrellen.pomoplan.activity.util.ExtraName;
import com.rooftrellen.pomoplan.model.PomoDaily;

/**
 * DailyActivityTest is a test class for testing DailyActivity.
 *
 * @author Team FirstRow
 * @version 1.0.0
 */
public class DailyActivityTest extends ActivityInstrumentationTestCase2<com.rooftrellen.pomoplan.activity.other.DailyActivity> {

    /**
     * The decrease button.
     *
     * @since 1.0.0
     */
    private Button buttonDec;

    /**
     * The increase button.
     *
     * @since 1.0.0
     */
    private Button buttonInc;

    /**
     * The TextView for plan.
     *
     * @since 1.0.0
     */
    private TextView textPlan;

    /**
     * Initializes with class type.
     *
     * @since 1.0.0
     */
    public DailyActivityTest() {
        super(DailyActivity.class);
    }

    /**
     * Sets up DailyActivity with a PomoDaily.
     *
     * @since 1.0.0
     */
    @Override
    public void setUp() throws Exception{
        Intent intent = new Intent(getInstrumentation().getTargetContext(), DailyActivity.class);
        PomoDaily daily = new PomoDaily("111", "5/3/2015", 2, "aaa");
        intent.putExtra(ExtraName.POMO_DAILY, daily);
        setActivityIntent(intent);
        DailyActivity dailyActivity = getActivity();
        buttonInc = (Button) dailyActivity.findViewById(R.id.buttonInc);
        buttonDec = (Button) dailyActivity.findViewById(R.id.buttonDec);
        textPlan = (TextView) dailyActivity.findViewById(R.id.textPlan);
    }

    /**
     * Tests the correctness of showing origin plan.
     *
     * @since 1.0.0
     */
    public void testPlan() {
        assertEquals("2", textPlan.getText().toString());
    }

    /**
     * Tests the button for increasing plan.
     *
     * @since 1.0.0
     */
    @UiThreadTest
    public void testIncreasePlan() {
        int plan = Integer.parseInt(textPlan.getText().toString());
        buttonInc.performClick();
        assertEquals(plan + 1, Integer.parseInt(textPlan.getText().toString()));
    }

    /**
     * Tests the button for decreasing plan.
     *
     * @since 1.0.0
     */
    @UiThreadTest
    public void testDecreasePlan() {
        int plan = Integer.parseInt(textPlan.getText().toString());
        buttonDec.performClick();
        assertEquals(plan - 1, Integer.parseInt(textPlan.getText().toString()));
    }

}
