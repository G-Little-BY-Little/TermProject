package org.androidtown.sina_ver01.test;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.CheckBox;

import org.androidtown.sina_ver01.R;
import org.androidtown.sina_ver01.SortingActivity;

public class SortingActivityTest extends ActivityInstrumentationTestCase2<SortingActivity> {

    public SortingActivityTest()
    {
        super(SortingActivity.class);
    }

    public void testsort(){
        Activity activity=getActivity();

        CheckBox happy_check = (CheckBox) activity.findViewById(R.id.feeling1);
        CheckBox angry_check = (CheckBox) activity.findViewById(R.id.feeling2);
        CheckBox sad_check = (CheckBox) activity.findViewById(R.id.feeling3);
        Button okBtn = (Button) activity.findViewById(R.id.check);

        assertNotNull(happy_check);
        assertNotNull(angry_check);
        assertNotNull(sad_check);
        assertNotNull(okBtn);
    }

}
