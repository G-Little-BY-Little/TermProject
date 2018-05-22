package org.androidtown.sina_ver01;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import java.util.Calendar;
import java.util.Date;

public class LetterActivity extends AppCompatActivity implements View.OnClickListener {
    private int mYear, mMonth, mDay;

    TextView mDisplayDate;
    Button mDisplayWeather;
    EditText mTitle, mSubTitle, mText;
    DatePickerDialog.OnDateSetListener mDateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter);

        mText = (EditText) findViewById(R.id.content);
        mTitle = (EditText) findViewById(R.id.title);
        mSubTitle = (EditText) findViewById(R.id.subtitle);
        mDisplayDate = (TextView) findViewById(R.id.Draw_Date);
        mDisplayWeather = (Button)findViewById(R.id.Draw_Weather);
        ImageButton saveBtn = (ImageButton) findViewById(R.id.save);
        saveBtn.setOnClickListener(this);

        calendar();
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
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(LetterActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Log.d("DrawLayout", "onDataSet:yyyy/mm/dd:" + year + "년" + month + "월" + dayOfMonth + "일");

                String date = year + "년" + month + "월" + dayOfMonth + "일";
                mDisplayDate.setText(date);
            }
        };

    }

    private void saveData() {
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("users").child(mUser.getUid()).child("diary").child("text");
        String title = mTitle.getText().toString();
        String subtitle = mSubTitle.getText().toString();
        String text = mText.getText().toString();
        Date temp = new Date(System.currentTimeMillis());
        temp.setYear(mYear);
        temp.setMonth(mMonth);
        temp.setDate(mDay);
        String date = Utils.dateToString(temp);

        DiaryData diaryData = new DiaryData(title, subtitle, date, text);

        mRef.push().setValue(diaryData);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.save:
                saveData();
                finish();
        }
    }
}
