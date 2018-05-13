package org.androidtown.sina_ver01;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class SortingActivity extends AppCompatActivity {

    ImageButton button1;
    ImageButton button2;
    ImageButton button3;

    Context context;


    TextView mDisplayStartDate;

    TextView mDisplayEndDate;

    DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorting);


        button1=(ImageButton)findViewById(R.id.feeling1);
        button2=(ImageButton)findViewById(R.id.feeling2);
        button3=(ImageButton)findViewById(R.id.feeling3);
        context=getApplicationContext();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"버튼1",Toast.LENGTH_LONG).show();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"버튼2",Toast.LENGTH_LONG).show();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"버튼3",Toast.LENGTH_LONG).show();
            }
        });

        mDisplayStartDate=(TextView)findViewById(R.id.StartDate);

        mDisplayStartDate.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Calendar cal= Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(
                        SortingActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month=month+1;
                Log.d("Page12","onDateSet: yyyy/mm/dd: "+year+"년"+month+"월"+day+"일");

                String date=year+"년"+month+"월"+day+"일";
                mDisplayStartDate.setText(date);
            }
        };

        mDisplayEndDate=(TextView)findViewById(R.id.EndDate);

        mDisplayEndDate.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Calendar cal= Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(
                        SortingActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month=month+1;
                Log.d("Page12","onDateSet: yyyy/mm/dd: "+year+"년"+month+"월"+day+"일");

                String date=year+"년"+month+"월"+day+"일";
                mDisplayEndDate.setText(date);
            }
        };

    }
}
