package org.androidtown.sina_ver01;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class LetterActivity extends AppCompatActivity implements View.OnClickListener {
    private int mYear, mMonth, mDay;

    TextView mDisplayDate;
    EditText mTitle, mSubTitle, mText;
    DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter);

        mText = (EditText) findViewById(R.id.content);
        mTitle = (EditText) findViewById(R.id.title);
        mSubTitle = (EditText) findViewById(R.id.subtitle);
        mDisplayDate = (TextView) findViewById(R.id.Date);
        Button saveBtn = (Button) findViewById(R.id.save);
        saveBtn.setOnClickListener(this);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                mYear = cal.get(Calendar.YEAR);
                mMonth = cal.get(Calendar.MONTH);
                mDay = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(LetterActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, mYear, mMonth, mDay);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Date date = new Date(System.currentTimeMillis());
                date.setYear(year);
                date.setMonth(month);
                date.setDate(day);

                mYear = year;
                mMonth = month;
                mDay = day;

                String sDate = Utils.calToString(date);

                mDisplayDate.setText(sDate);
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
        }
    }
}
