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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.text.Line;

import java.util.Calendar;

public class DrawLayout extends AppCompatActivity implements View.OnClickListener {
    TextView DisplayDate;
    Button DisplayWeather;
    DatePickerDialog.OnDateSetListener DateListener;
    private ImageButton color1, color2, color3, color4, color5, color6, color7, color8;
    private ImageButton reset_Btn, brush_Btn, erase_Btn, save_Btn;
    private SingleTouchView drawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_layout);
        init();
        clickBtn();
        calendar();

    }

    public void calendar() {
        DisplayWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {"맑음", "눈 / 비", "흐림"};
                AlertDialog.Builder builder = new AlertDialog.Builder(DrawLayout.this);     // 여기서 this는 Activity의 this

                // 여기서 부터는 알림창의 속성 설정
                builder.setTitle("날씨를 선택하세요")        // 제목 설정
                        .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {    // 목록 클릭시 설정
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

    public void clickBtn() {
        color1.setOnClickListener(this);
        color2.setOnClickListener(this);
        color3.setOnClickListener(this);
        color4.setOnClickListener(this);
        color5.setOnClickListener(this);
        color6.setOnClickListener(this);
        color7.setOnClickListener(this);
        color8.setOnClickListener(this);
        reset_Btn.setOnClickListener(this);
        brush_Btn.setOnClickListener(this);
        erase_Btn.setOnClickListener(this);
        save_Btn.setOnClickListener(this);
    }

    public void init() {
        DisplayWeather = (Button) findViewById(R.id.Draw_Weather);
        DisplayDate = (TextView) findViewById(R.id.Draw_Date);
        drawView = (SingleTouchView) findViewById(R.id.drawing);
        LinearLayout paintLayout = (LinearLayout) findViewById(R.id.paint_colors);
        color1 = (ImageButton) findViewById(R.id.color1);
        color2 = (ImageButton) findViewById(R.id.color2);
        color3 = (ImageButton) findViewById(R.id.color3);
        color4 = (ImageButton) findViewById(R.id.color4);
        color5 = (ImageButton) findViewById(R.id.color5);
        color6 = (ImageButton) findViewById(R.id.color6);
        color7 = (ImageButton) findViewById(R.id.color7);
        color8 = (ImageButton) findViewById(R.id.color8);
        reset_Btn = (ImageButton) findViewById(R.id.reset_btn);
        brush_Btn = (ImageButton) findViewById(R.id.brush_btn);
        erase_Btn = (ImageButton) findViewById(R.id.erase_btn);
        save_Btn = (ImageButton) findViewById(R.id.save_btn);

    }

    public void onClick(View v) {
        String color;
        switch ((v.getId())) {
            case R.id.color1:
                color = v.getTag().toString();
                drawView.setColor(color);
                //Log.i("버튼1","눌렸다");
                break;
            case R.id.color2:
                color = v.getTag().toString();
                drawView.setColor(color);
                //Log.i("버튼2","눌렸다");
                //Log.v("버튼2","값은" + v.getTag().toString());
                break;
            case R.id.color3:
                color = v.getTag().toString();
                drawView.setColor(color);
                //Log.i("버튼3","눌렸다");
                break;
            case R.id.color4:
                color = v.getTag().toString();
                drawView.setColor(color);
                //Log.i("버튼4","눌렸다");
                break;
            case R.id.color5:
                color = v.getTag().toString();
                drawView.setColor(color);
                //Log.i("버튼5","눌렸다");
                break;
            case R.id.color6:
                color = v.getTag().toString();
                drawView.setColor(color);
                //Log.i("버튼6","눌렸다");
                break;
            case R.id.color7:
                color = v.getTag().toString();
                drawView.setColor(color);
                //Log.i("버튼7","눌렸다");
                break;
            case R.id.color8:
                color = v.getTag().toString();
                drawView.setColor(color);
                //Log.i("버튼8","눌렸다");
                break;
            case R.id.reset_btn:
                drawView.reset();
                break;
            case R.id.brush_btn:
                break;
            case R.id.erase_btn:
                drawView.setColor("#FFFFFFFF");
                break;
            case R.id.save_btn:
                break;

        }
    }
}
