package org.androidtown.sina_ver01;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

public class BackPressCloseHandler {
    private long backKeyPressedTime = 0;
    private Toast toast;
    private Activity activity;
    public BackPressCloseHandler(Activity context){
        this.activity = context;
    }

    public void onBackPressed(){
        if(System.currentTimeMillis()>backKeyPressedTime+2000){
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }

        if(System.currentTimeMillis() <= backKeyPressedTime + 2000){
            ActivityCompat.finishAffinity(activity);
            toast.cancel();
        }
    }

    public void showGuide(){
        toast = Toast.makeText(activity,"뒤로가기 버튼을 한번 더 누르면 앱이 종료됩니다.",Toast.LENGTH_SHORT);
        toast.show();
    }
}
