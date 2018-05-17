package org.androidtown.sina_ver01;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

    final int REQ_CODE_SELECT_IMAGE = 100;

    TextView mDisplayDate;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    Button picture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_lay);

        mDisplayDate = (TextView) findViewById(R.id.Date);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                       PictureLayActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d("Main4Activity", "onDateSet: yyyy/mm/dd: " + year + "년" + month + "월" + day + "일");

                String date = year + "년" + month + "월" + day + "일";
                mDisplayDate.setText(date);
            }
        };

        picture = (Button) findViewById(R.id.picture);
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

    public String getImageNameToUri(Uri data)

    {

        String[] proj = { MediaStore.Images.Media.DATA };

        Cursor cursor = managedQuery(data, proj, null, null, null);

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        String imgPath = cursor.getString(column_index);

        String imgName = imgPath.substring(imgPath.lastIndexOf("/")+1);



        return imgName;

    }



}
