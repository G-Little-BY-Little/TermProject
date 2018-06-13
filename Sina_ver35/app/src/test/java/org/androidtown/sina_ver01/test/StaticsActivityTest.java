package org.androidtown.sina_ver01.test;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageButton;

import org.androidtown.sina_ver01.R;
import org.androidtown.sina_ver01.StaticsActivity;

public class StaticsActivityTest extends ActivityInstrumentationTestCase2<StaticsActivity> {

    public StaticsActivityTest(){
        super(StaticsActivity.class);
    }

    public void testStatistic(){
        Activity activity=getActivity();

        ImageButton rec_btn = (ImageButton) activity.findViewById(R.id.recommend_btn);
        assertNotNull(rec_btn);
    }

}
