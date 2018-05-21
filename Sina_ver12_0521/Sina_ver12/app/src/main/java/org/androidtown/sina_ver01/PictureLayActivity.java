package org.androidtown.sina_ver01;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.androidtown.sina_ver01.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

public class PictureLayActivity extends AppCompatActivity {

    Adapter adapter;
    ViewPager viewPager;
    Button DisplayWeather;
    DatePickerDialog.OnDateSetListener DateListener;

    final int REQ_CODE_SELECT_IMAGE = 100;

    TextView mDisplayDate;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    ImageButton picture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_lay);

        DisplayWeather = (Button)findViewById(R.id.Draw_Weather);
        mDisplayDate = (TextView)findViewById(R.id.Draw_Date);

        calendar();


        picture = (ImageButton) findViewById(R.id.upload_btn);
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
            }
        });


        viewPager = (ViewPager) findViewById(R.id.view);
        adapter = new Adapter(this);
        viewPager.setAdapter(adapter);

    }

    public void calendar() {
        DisplayWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {"맑음", "눈 / 비", "흐림"};
                AlertDialog.Builder builder = new AlertDialog.Builder(PictureLayActivity.this);     // 여기서 this는 Activity의 this

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
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(PictureLayActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, DateListener, year, month, day);
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
                mDisplayDate.setText(date);
            }
        };

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Toast.makeText(getBaseContext(), "resultCode: " + resultCode, Toast.LENGTH_SHORT).show();

        if (requestCode == REQ_CODE_SELECT_IMAGE)

        {

            if (resultCode == Activity.RESULT_OK)

            {

                try {

                    //Uri에서 이미지 이름을 얻어온다.

                    //String name_Str = getImageNameToUri(data.getData());


                    //이미지 데이터를 비트맵으로 받아온다.

                    Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());

                    ImageView image = (ImageView) findViewById(R.id.view);

                    //배치해놓은 ImageView에 set

                    image.setImageBitmap(image_bitmap);

                } catch (FileNotFoundException e) {

                    // TODO Auto-generated catch block

                    e.printStackTrace();

                } catch (IOException e) {

                    // TODO Auto-generated catch block

                    e.printStackTrace();

                } catch (Exception e)

                {

                    e.printStackTrace();
                }

            }
        }
    }
}