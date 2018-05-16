package org.androidtown.sina_ver01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by GENIE on 2018-04-08.
 */

public class SplashActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        startActivity(new Intent(this,SecondMain.class));
        finish();
    }
}
