package org.androidtown.sina_ver01;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;

public class LetterActivity extends AppCompatActivity implements View.OnClickListener {

    TextView mDisplayDate, mSubTitle;
    Button mDisplayWeather;
    EditText mTitle, mText;
    DatePickerDialog.OnDateSetListener mDateListener;
    DiaryData data = new DiaryData();
    Calendar date;
    private int emotion;
    private String subtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter);

        /*감정 번들 풀기*/
        Intent myIntent = getIntent();
        Bundle myBundle = myIntent.getExtras();
        int emo_flag = myBundle.getInt("emo_num");
        String edit = myBundle.getString("subtitle");
        /*******************************************************/
        //Log.i("넘어온값","? " + emo_flag);
        emotion = emo_flag;
        subtitle=edit;
        data.setEmotion(emotion);

        date = Calendar.getInstance();

        mText = (EditText) findViewById(R.id.content);
        mTitle = (EditText) findViewById(R.id.title);
        mSubTitle = (TextView) findViewById(R.id.subtitle);
        mSubTitle.setText(edit);
        mDisplayDate = (TextView) findViewById(R.id.Draw_Date);
        mDisplayWeather = (Button) findViewById(R.id.Draw_Weather);
        ImageButton saveBtn = (ImageButton) findViewById(R.id.save);
        saveBtn.setOnClickListener(this);

        calendar();

        //글꼴 설정
        SetActivity.mtypeface= Typeface.createFromAsset(getAssets(),SetActivity.fonts);
        ViewGroup root3=(ViewGroup)findViewById(android.R.id.content);
        SetActivity.setGlobalFont(root3);


        //Play Music
        Intent intent1 = new Intent(LetterActivity.this, MusicService.class);
        intent1.putExtra(MusicService.MESSEAGE_KEY,true);

        Bundle myBundle1 = new Bundle();
        myBundle1.putInt("emo_num", emotion);
        intent1.putExtras(myBundle);
        startService(intent1);




    }

    public boolean onKeyDown(int keyCode,KeyEvent event){
        switch(keyCode){
            case android.view.KeyEvent.KEYCODE_BACK:
                exit();
        }
        return false;
    }

    public void exit(){
        //Stop Music
        Intent intent1 = new Intent(LetterActivity.this, MusicService.class);
        intent1.putExtra(MusicService.MESSEAGE_KEY,false);
        stopService(intent1);
        finish();
    }

    public void calendar() {
        mDisplayWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {"맑음", "눈 / 비", "흐림"};
                AlertDialog.Builder builder = new AlertDialog.Builder(LetterActivity.this);     // 여기서 this는 Activity의 this

                // 여기서 부터는 알림창의 속성 설정
                builder.setTitle("날씨를 선택하세요")        // 제목 설정
                        .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {    // 목록 클릭시 설정
                            public void onClick(DialogInterface dialog, int index) {
                                mDisplayWeather.setText(items[index]);
                                mDisplayWeather.setTextColor(Color.GRAY);
                                Toast.makeText(getApplicationContext(), items[index], Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        });

                AlertDialog dialog = builder.create();    // 알림창 객체 생성
                dialog.show();    // 알림창 띄우기

            }
        });
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();

                DatePickerDialog dialog = new DatePickerDialog(LetterActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d("DrawLayout", "onDataSet:yyyy/mm/dd:" + year + "년" + month + "월" + dayOfMonth + "일");

                date.set(year, month, dayOfMonth);
                mDisplayDate.setText(Utils.calToString(date.getTime()));
            }
        };

    }

    private void saveData() {
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("users").child(mUser.getUid()).child("diary").child("text");
        String title = mTitle.getText().toString();
        String subtitle = mSubTitle.getText().toString();
        String text = mText.getText().toString();
        String weather = mDisplayWeather.getText().toString();
        int type = 1;

        DiaryData diaryData = new DiaryData(title, subtitle, Utils.dateToString(date.getTime()),weather, text, emotion,type);

        mRef.push().setValue(diaryData);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                saveData();
                /*번들 넘기기*/
                Intent intent = new Intent(LetterActivity.this, CalendarActivity.class);
                Bundle myBundle = new Bundle();
                myBundle.putInt("emo_num", emotion);
                myBundle.putString("subtitle", subtitle);
                intent.putExtras(myBundle);
                //Log.i("번호","넘기기전"+emo_flag);
                startActivityForResult(intent, 1234);

                //Stop Music
                Intent intent1 = new Intent(LetterActivity.this, MusicService.class);
                intent1.putExtra(MusicService.MESSEAGE_KEY,false);
                stopService(intent1);

                finish();
        }
    }
}
