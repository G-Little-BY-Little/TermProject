package org.androidtown.sina_ver01;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

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

    TextView pSubtitle;
    TextView pDate;
    Button pWeather;
    ImageView pView;
    EditText pContent;
    ImageButton pUploadBtn;

    TextView dSubtitle;
    TextView dDate;
    Button dWeather;
    ImageView dView;
    EditText dContents;
    ImageButton dSaveBtn;



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

                lDate.setText(diaryData.getDate());
                lWeather.setText(diaryData.getWeather());
                lTitle.setText(diaryData.getTitle());
                lSubtitle.setText(diaryData.getSubtitle());
                lContent.setText(diaryData.getText());
                saveBtn.setOnClickListener(DiaryActivity.this);
                break;

            case 2:
                setContentView(R.layout.activity_draw_layout);

                dDate = findViewById(R.id.Draw_Date);
                dWeather = findViewById(R.id.Draw_Weather);
                dView = findViewById(R.id.drawView);
                dContents = findViewById(R.id.drawContents);

                dDate.setText(diaryData.getDate());
                dWeather.setText(diaryData.getWeather());
                dContents.setText(diaryData.getText());

                dView.setVisibility(View.VISIBLE);
                dView.setImageBitmap(Utils.stringToBitmap(diaryData.getDraw()));
                break;

            case 3:
                setContentView(R.layout.activity_picture_lay);

                pDate = findViewById(R.id.Draw_Date);
                pWeather = findViewById(R.id.Draw_Weather);
                pView = findViewById(R.id.view);
                pContent = findViewById(R.id.diaryPhoto);
                pSaveBtn = findViewById(R.id.save_btn);
                pUploadBtn = findViewById(R.id.upload_btn);

                pDate.setText(diaryData.getDate());
                pWeather.setText(diaryData.getWeather());

                pView.setImageBitmap(Utils.stringToBitmap(diaryData.getPic()));

                pContent.setText(diaryData.getText());
                //pSaveBtn.setOnClickListener(DiaryActivity.this);
                //pUploadBtn.setOnClickListener(DiaryActivity.this);
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.save:
                DiaryData newData = new DiaryData(lTitle.getText().toString(), lSubtitle.getText().toString(), lDate.getText().toString(), lWeather.getText().toString(),lContent.getText().toString(), diaryData.getEmotion(), diaryData.getType());
                mRef.child("text").child(diaryData.getId()).setValue(newData);
                Toast.makeText(getApplicationContext(),"수정 되었습니다.",Toast.LENGTH_LONG).show();
                finish();
                break;
            case R.id.save_btn:
                //String subtitle, int emotion, String date, String weather, String text, String pic, int type
                DiaryData newData2 = new DiaryData(pSubtitle.getText().toString(), diaryData.getEmotion(),pDate.getText().toString(), pWeather.getText().toString(),pContent.getText().toString(), diaryData.getPic(), diaryData.getType());
                mRef.child("picture").child(diaryData.getId()).setValue(newData2);
                Toast.makeText(getApplicationContext(),"수정 되었습니다.",Toast.LENGTH_LONG).show();
                finish();
                break;
        }
    }
}
