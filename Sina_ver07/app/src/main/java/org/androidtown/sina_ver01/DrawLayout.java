package org.androidtown.sina_ver01;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class DrawLayout extends AppCompatActivity {
    TextView DisplayDate;
    Button DisplayWeather;
    DatePickerDialog.OnDateSetListener DateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_layout);
        DisplayWeather = (Button) findViewById(R.id.Draw_Weather);
        DisplayDate = (TextView) findViewById(R.id.Draw_Date);

        DisplayWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {"맑음", "눈 / 비", "흐림"};
                AlertDialog.Builder builder = new AlertDialog.Builder(DrawLayout.this);     // 여기서 this는 Activity의 this

                // 여기서 부터는 알림창의 속성 설정
                builder.setTitle("날씨를 선택하세요")        // 제목 설정
                        .setSingleChoiceItems(items,-1, new DialogInterface.OnClickListener() {    // 목록 클릭시 설정
                            public void onClick(DialogInterface dialog, int index) {
                                DisplayWeather.setText(items[index]);
                                DisplayWeather.setTextColor(Color.GRAY);
                                Toast.makeText(getApplicationContext(), items[index], Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        });

                AlertDialog dialog = builder.create();    // 알림창 객체 생성
                dialog.show();    // 알림창 띄우기

            }
        });
        DisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(DrawLayout.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, DateListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        DateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Log.d("DrawLayout", "onDataSet:yyyy/mm/dd:" + year + "년" + month + "월" + dayOfMonth + "일");

                String date = year + "년" + month + "월" + dayOfMonth + "일";
                DisplayDate.setText(date);
            }
        };
    }
}
