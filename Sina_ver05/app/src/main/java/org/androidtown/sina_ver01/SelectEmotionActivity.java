package org.androidtown.sina_ver01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class SelectEmotionActivity extends AppCompatActivity {

    ImageButton emoHappy;
    ImageButton emoAngry;
    ImageButton emoSad;

    Button btnOk;
    Button btnCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_emotion);
        setListener();

        EditText edit = (EditText)findViewById(R.id.todayText);
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(20);
        edit.setFilters(FilterArray);

    }

    public void setListener(){

        emoHappy = (ImageButton)findViewById(R.id.emoHappy);
        emoAngry = (ImageButton)findViewById(R.id.emoAngry);
        emoSad = (ImageButton)findViewById(R.id.emoSad);

        btnOk=(Button)findViewById(R.id.btnOk);
        btnCancel=(Button)findViewById(R.id.btnCancel);

        emoHappy.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getApplicationContext(),"행복한 하루시군요!",Toast.LENGTH_SHORT).show();
            }
        });

        emoAngry.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getApplicationContext(),"화나는 일이 있으셨군요!",Toast.LENGTH_SHORT).show();
            }
        });

        emoSad.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getApplicationContext(),"슬퍼하지 마세요ㅠ!",Toast.LENGTH_SHORT).show();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),CalendarActivity.class);
            startActivity(intent);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),SecondMain.class);
                startActivity(intent);
            }
        });

    }
}
