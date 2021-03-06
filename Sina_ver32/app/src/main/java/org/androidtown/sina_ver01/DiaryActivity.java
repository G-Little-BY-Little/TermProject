package org.androidtown.sina_ver01;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import static android.view.View.GONE;

public class DiaryActivity extends AppCompatActivity implements View.OnClickListener {
    DatabaseReference mRef;
    DiaryData diaryData;
    ImageButton saveBtn;
    ImageButton pSaveBtn;


    TextView lDate;
    Button lWeather;
    EditText lTitle;
    TextView lSubtitle;
    EditText lContent;
    ImageButton lPlayBtn;

    TextView pSubtitle;
    TextView pDate;
    Button pWeather;
    ImageView pView;
    EditText pContent;
    ImageButton pUploadBtn;
    ImageButton pPlayBtn;

    TextView dSubtitle;
    TextView dDate;
    Button dWeather;
    ImageView dView;
    EditText dContents;
    ImageButton dSaveBtn;
    ImageButton dPlayBtn;
    int emotion;

    TextView eDate;
    Button eWeather;
    ImageView eView;
    ImageButton eSaveBtn;
    ImageButton ePlayBtn;

    boolean checkClick = false;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_diary);


        Intent myIntent = getIntent();
        Bundle myBundle = myIntent.getExtras();
        diaryData = myBundle.getParcelable("data");

        mRef = FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("diary");

        int type = diaryData.getType();
        switch (type) {
            case 1:
                setContentView(R.layout.activity_letter);

                lDate = findViewById(R.id.Draw_Date);
                lWeather = findViewById(R.id.Draw_Weather);
                lTitle = findViewById(R.id.title);
                lSubtitle = findViewById(R.id.subtitle);
                lContent = findViewById(R.id.content);
                saveBtn = findViewById(R.id.save);
                lPlayBtn = findViewById(R.id.music);

                lDate.setText(diaryData.getDate());
                lWeather.setText(diaryData.getWeather());
                lTitle.setText(diaryData.getTitle());
                lSubtitle.setText(diaryData.getSubtitle());
                lContent.setText(diaryData.getText());

                //play();
                if(checkClick == false) {
                    play();
                    checkClick = true;
                }

                saveBtn.setVisibility(GONE);
                lPlayBtn.setOnClickListener(DiaryActivity.this);

                break;

            case 2:
                setContentView(R.layout.activity_draw_layout);

                dDate = findViewById(R.id.Draw_Date);
                dWeather = findViewById(R.id.Draw_Weather);
                dView = findViewById(R.id.drawView);
                dContents = findViewById(R.id.drawContents);
                dPlayBtn = findViewById(R.id.music);

                dDate.setText(diaryData.getDate());
                dWeather.setText(diaryData.getWeather());
                dContents.setText(diaryData.getText());

                dView.setVisibility(View.VISIBLE);
                dView.setImageBitmap(Utils.stringToBitmap(diaryData.getDraw()));
                //play();
                if(checkClick == false) {
                    play();
                    checkClick = true;
                }
                dPlayBtn.setOnClickListener(DiaryActivity.this);
                //dSaveBtn.setVisibility(GONE);

                break;

            case 3:
                setContentView(R.layout.activity_picture_lay);

                pDate = findViewById(R.id.Draw_Date);
                pWeather = findViewById(R.id.Draw_Weather);
                pView = findViewById(R.id.view);
                pContent = findViewById(R.id.diaryPhoto);
                pSaveBtn = findViewById(R.id.save_btn);
                pUploadBtn = findViewById(R.id.upload_btn);
                pPlayBtn = findViewById(R.id.music);

                //play();
                if(checkClick == false) {
                    play();
                    checkClick = true;
                }
                pDate.setText(diaryData.getDate());
                pWeather.setText(diaryData.getWeather());

                pView.setImageBitmap(Utils.stringToBitmap(diaryData.getPic()));

                pContent.setText(diaryData.getText());

                pPlayBtn.setOnClickListener(DiaryActivity.this);
                pSaveBtn.setVisibility(GONE);
                pUploadBtn.setVisibility(GONE);
                break;

            case 4:
                setContentView(R.layout.activity_empty_layout);

                eDate = findViewById(R.id.Draw_Date);
                eWeather = findViewById(R.id.Draw_Weather);
                eView = findViewById(R.id.captureView);
                eSaveBtn=findViewById(R.id.save_btn);
                ePlayBtn=findViewById(R.id.music);
                //play();
                if(checkClick == false) {
                    play();
                    checkClick = true;
                }
                eDate.setText(diaryData.getDate());
                eWeather.setText(diaryData.getWeather());

                eView.setVisibility(View.VISIBLE);
                eView.setImageBitmap(Utils.stringToBitmap(diaryData.getCapture()));

                eSaveBtn.setVisibility(GONE);
                ePlayBtn.setOnClickListener(DiaryActivity.this);

        }

    }

    public void play()
    {
        //Play Music
        Intent intent1 = new Intent(DiaryActivity.this, MusicService.class);
        intent1.putExtra(MusicService.MESSEAGE_KEY,true);

        startService(intent1);
    }

    public void exit(){
        //Stop Music
        Intent intent1 = new Intent(getApplicationContext(), MusicService.class);
        intent1.putExtra(MusicService.MESSEAGE_KEY,false);
        stopService(intent1);
    }

    public boolean onKeyDown(int keyCode,KeyEvent event){
        switch(keyCode){
            case android.view.KeyEvent.KEYCODE_BACK:
                exit();
                finish();
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.music:
                if (checkClick == true) {
                    exit();
                    checkClick = false;
                }else
                {
                    play();
                    checkClick = true;
                }
                break;
        }
    }


}