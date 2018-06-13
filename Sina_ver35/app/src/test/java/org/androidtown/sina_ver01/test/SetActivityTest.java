package org.androidtown.sina_ver01.test;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageButton;

import org.androidtown.sina_ver01.R;
import org.androidtown.sina_ver01.SetActivity;

public class SetActivityTest extends ActivityInstrumentationTestCase2<SetActivity> {

    public SetActivityTest(){
        super(SetActivity.class);
    }
    public void testButton(){
        Activity activity=getActivity();

        ImageButton logout=(ImageButton)activity.findViewById(R.id.logoutBtn);

        assertNotNull(logout);
    }

    public void testButton2(){
        Activity activity=getActivity();

        ImageButton logout=(ImageButton)activity.findViewById(R.id.letterlogoBtn);

        assertNotNull(logout);
    }


}
