package org.androidtown.sina_ver01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

public class LayoutMain extends AppCompatActivity {

    ImageButton emptyBnt;
    ImageButton letterBnt;
    ImageButton drawBnt;
    ImageButton pictureBnt;

    DiaryData data = new DiaryData();
    private int feelNum;
    private String subtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_main);

        /*감정 번들 풀기*/
        Intent myIntent = getIntent();
        Bundle myBundle = myIntent.getExtras();
        int emo_flag = myBundle.getInt("emo_num");
        String edit = myBundle.getString("subtitle");
        /*******************************************************/

        feelNum = emo_flag;
        subtitle = "" + edit;

        setToolBar();

        //Log.i("이모션값", "은 " + data.getEmotion());
        emptyBnt = (ImageButton) findViewById(R.id.emptyBtn);
        letterBnt = (ImageButton) findViewById(R.id.letterBtn);
        drawBnt = (ImageButton) findViewById(R.id.drawBtn);
        pictureBnt = (ImageButton) findViewById(R.id.pictureBtn);

        emptyBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "빈 레이아웃", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(getApplicationContext(),EmptyLayout.class));
                /*번들 넘기기*/
                Intent intent = new Intent(LayoutMain.this, EmptyLayout.class);
                Bundle myBundle = new Bundle();
                myBundle.putInt("emo_num", feelNum);
                myBundle.putString("subtitle", subtitle);
                intent.putExtras(myBundle);
                //Log.i("번호","넘기기전"+emo_flag);
                startActivityForResult(intent, 1234);
                finish();
            }
        });

        letterBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "편지지 레이아웃", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(getApplicationContext(),LetterActivity.class));

                /*번들 넘기기*/
                Intent intent = new Intent(LayoutMain.this, LetterActivity.class);
                Bundle myBundle = new Bundle();
                myBundle.putInt("emo_num", feelNum);
                myBundle.putString("subtitle", subtitle);
                intent.putExtras(myBundle);
                //Log.i("번호","넘기기전"+emo_flag);
                startActivityForResult(intent, 1234);
                finish();
            }
        });

        drawBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "그림일기 레이아웃", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(getApplicationContext(), DrawLayout.class));
                /*번들 넘기기*/
                Intent intent = new Intent(LayoutMain.this,DrawLayout.class);
                Bundle myBundle = new Bundle();
                myBundle.putInt("emo_num", feelNum);
                myBundle.putString("subtitle", subtitle);
                intent.putExtras(myBundle);
                //Log.i("번호","넘기기전"+emo_flag);
                startActivityForResult(intent, 1234);
                finish();
            }
        });

        pictureBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "사진 레이아웃", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(getApplicationContext(), PictureLayActivity.class));
                /*번들 넘기기*/
                Intent intent = new Intent(LayoutMain.this, PictureLayActivity.class);
                Bundle myBundle = new Bundle();
                myBundle.putInt("emo_num", feelNum);
                myBundle.putString("subtitle", subtitle);
                intent.putExtras(myBundle);
                //Log.i("번호","넘기기전"+emo_flag);
                startActivityForResult(intent, 1234);
                finish();
            }
        });
    }

    public void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarSina);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.findViewById(R.id.menuBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "버튼", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
