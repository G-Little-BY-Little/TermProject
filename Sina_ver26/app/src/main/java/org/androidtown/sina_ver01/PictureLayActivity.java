package org.androidtown.sina_ver01;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Picture;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

public class PictureLayActivity extends AppCompatActivity implements View.OnClickListener {

    DiaryData data = new DiaryData();
    private int emotion;
    private String subtitle;
    Bitmap photo;
    Calendar date;

    Button DisplayWeather;
    DatePickerDialog.OnDateSetListener DateListener;

    ImageView image;
    ImageButton save_btn;
    TextView mDisplayDate;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    ImageButton picture;
    EditText contents;

    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;

    private Uri mImageCaptureUri;
    private String absolutePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_lay);

        /*감정 번들 풀기*/
        Intent myIntent = getIntent();
        Bundle myBundle = myIntent.getExtras();
        int emo_flag = myBundle.getInt("emo_num");
        String edit = myBundle.getString("subtitle");
        /*******************************************************/
        //Log.i("넘어온값","? " + emo_flag);
        emotion = emo_flag;
        subtitle = edit;
        data.setEmotion(emotion);

        date = Calendar.getInstance();





        //글꼴 설정
        SetActivity.mtypeface = Typeface.createFromAsset(getAssets(), SetActivity.fonts);
        ViewGroup root3 = (ViewGroup) findViewById(android.R.id.content);
        SetActivity.setGlobalFont(root3);

        contents = (EditText)findViewById(R.id.diaryPhoto);
        save_btn = (ImageButton) findViewById(R.id.save_btn);
        DisplayWeather = (Button) findViewById(R.id.Draw_Weather);

        mDisplayDate = (TextView) findViewById(R.id.Draw_Date);
        picture = (ImageButton) findViewById(R.id.upload_btn);

        calendar();


        //Play Music
        Intent intent1 = new Intent(PictureLayActivity.this, MusicService.class);
        intent1.putExtra(MusicService.MESSEAGE_KEY,true);

        Bundle myBundle1 = new Bundle();
        myBundle1.putInt("emo_num", emotion);
        intent1.putExtras(myBundle);
        startService(intent1);


        picture.setOnClickListener(this);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*번들 넘기기*/
                Intent intent = new Intent(PictureLayActivity.this, CalendarActivity.class);
                Bundle myBundle = new Bundle();
                saveData();
                myBundle.putInt("emo_num", emotion);
                myBundle.putString("subtitle", subtitle);
                intent.putExtras(myBundle);
                //Log.i("번호","넘기기전"+emo_flag);
                startActivityForResult(intent, 1234);
            }
        });
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
        Intent intent1 = new Intent(PictureLayActivity.this, MusicService.class);
        intent1.putExtra(MusicService.MESSEAGE_KEY,false);
        stopService(intent1);
        finish();
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
                Log.d("DrawLayout", "onDataSet:yyyy/mm/dd:" + year + "년" + month + "월" + dayOfMonth + "일");

                date.set(year,month,dayOfMonth);
                mDisplayDate.setText(year + "년" + (month+1) + "월" + dayOfMonth + "일");
            }
        };

    }

    private void saveData() {
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("users").child(mUser.getUid()).child("diary").child("picture");

        String text = contents.getText().toString();
        String weather = DisplayWeather.getText().toString();
        int type = 3;
        //String subtitle, int emotion, String date, String text, String pic
        DiaryData diaryData = new DiaryData(subtitle,emotion,Utils.dateToString(date.getTime()),weather,text,Utils.bitmapToString(this,photo),type);
        //DiaryData diaryData = new DiaryData(title, subtitle, Utils.dateToString(date.getTime()), text, emotion);

        mRef.push().setValue(diaryData);
    }

    public void onClick(View v) {
        DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doTakeAlbumAction();
            }
        };

        new AlertDialog.Builder(this)
                .setTitle("사진앨범").setPositiveButton("앨범선택", albumListener).show();
    }

    public void doTakeAlbumAction() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    private void storeCropImage(Bitmap bitmap, String filePath) {
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SmartWheel";
        File directory_SmartWheel = new File(dirPath);

        if (!directory_SmartWheel.exists())
            directory_SmartWheel.mkdir();

        File copyFile = new File(filePath);
        BufferedOutputStream out = null;

        try {
            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(copyFile)));

            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case PICK_FROM_ALBUM: {
                mImageCaptureUri = data.getData();
                Log.d("SmartWheel", mImageCaptureUri.getPath().toString());
            }

            case PICK_FROM_CAMERA: {
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");

                intent.putExtra("outputX", 1080);
                intent.putExtra("outputY", 900);
                intent.putExtra("aspectX", 4);
                intent.putExtra("aspectY", 3);
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_IMAGE);
                break;
            }

            case CROP_FROM_IMAGE: {
                if (resultCode != RESULT_OK) {
                    return;
                }

                image = (ImageView) findViewById(R.id.view);
                final Bundle extras = data.getExtras();

                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SmartWheel/" + System.currentTimeMillis() + ".jpg";

                if (extras != null) {
                    photo = extras.getParcelable("data");
                    image.setImageBitmap(photo);

                    storeCropImage(photo, filePath);
                    absolutePath = filePath;
                    break;
                }

                File f = new File(mImageCaptureUri.getPath());
                if (f.exists()) {
                    f.delete();
                }
            }
        }
    }
}