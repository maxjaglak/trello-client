package org.max.trello.base;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import org.max.trello.activities.BaseActivity;

public abstract class AbstractUITest<T extends BaseActivity> extends ActivityInstrumentationTestCase2<T> {

    protected Solo solo;

    public AbstractUITest(Class<T> activityClass) {
        super(activityClass);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }




}
