package org.androidtown.sina_ver01;


import android.app.Activity;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;


public class SecondMain extends AppCompatActivity {
    ImageButton loginBtn;
    ImageButton setBtn;

    private BackPressCloseHandler backPressCloseHandler;
    public int flag=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_main);
        setToolBar();

        backPressCloseHandler = new BackPressCloseHandler(this);

        loginBtn = (ImageButton) findViewById(R.id.loginBtn);
        setBtn = (ImageButton) findViewById(R.id.setBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "로그인이 되었습니다.", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getApplicationContext(), LoginAcitivity.class));
                //startActivity(new Intent(getApplicationContext(), LayoutMain.class));
            }
        });

        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "가입버튼을 눌렀습니다.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), SetActivity.class));
            }
        });
    }

    public void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.findViewById(R.id.menuBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "버튼", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onBackPressed(){
        backPressCloseHandler.onBackPressed();
    }


}
