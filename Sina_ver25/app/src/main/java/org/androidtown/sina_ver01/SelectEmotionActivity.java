package org.androidtown.sina_ver01;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SelectEmotionActivity extends AppCompatActivity {

    RadioButton emoHappy;
    RadioButton emoAngry;
    RadioButton emoSad;
    RadioGroup emoGroup;
    EditText edit;
    Button btnOk;
    Button btnCancel;
    int emo_flag;
    DiaryData data = new DiaryData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_emotion);


        setListener();
        /*20자 이내*/
        edit = (EditText) findViewById(R.id.todayText);
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(20);
        edit.setFilters(FilterArray);

        //글꼴 설정
        SetActivity.mtypeface = Typeface.createFromAsset(getAssets(), SetActivity.fonts);
        ViewGroup root3 = (ViewGroup) findViewById(android.R.id.content);
        SetActivity.setGlobalFont(root3);


    }

    public void setListener() {

        emoHappy = (RadioButton) findViewById(R.id.emoHappy);
        emoAngry = (RadioButton) findViewById(R.id.emoAngry);
        emoSad = (RadioButton) findViewById(R.id.emoSad);
        emoGroup = (RadioGroup) findViewById(R.id.emoGroup);

        btnOk = (Button) findViewById(R.id.btnOk);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioId = emoGroup.getCheckedRadioButtonId();
                if (emoHappy.getId() == radioId) {
                    emo_flag = 0;
                    Log.i("번호", "들어옴" + emo_flag);
                }
                if (emoAngry.getId() == radioId) {
                    emo_flag = 1;
                    Log.i("번호", "들어옴" + emo_flag);
                }
                if (emoSad.getId() == radioId) {
                    emo_flag = 2;
                    Log.i("번호", "들어옴" + emo_flag);
                }
                /*번들 넘기기*/
                Intent intent = new Intent(SelectEmotionActivity.this, LayoutMain.class);
                Bundle myBundle = new Bundle();
                myBundle.putInt("emo_num", emo_flag);
                myBundle.putString("subtitle", edit.getText().toString());
                intent.putExtras(myBundle);
                //Log.i("번호","넘기기전"+emo_flag);
                startActivityForResult(intent, 1234);

                //Play Music
                Intent intent2 = new Intent(SelectEmotionActivity.this, MusicService.class);
                intent2.putExtra(MusicService.MESSEAGE_KEY,true);
                Bundle myBundle2 = new Bundle();
                myBundle2.putInt("emo_num", emo_flag);
                intent2.putExtras(myBundle);
                startService(intent2);
            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*번들 넘기기*/
                Intent intent = new Intent(SelectEmotionActivity.this, CalendarActivity.class);
                Bundle myBundle = new Bundle();
                myBundle.putInt("emo_num", emo_flag);
                myBundle.putString("subtitle", edit.getText().toString());
                intent.putExtras(myBundle);
                //Log.i("번호","넘기기전"+emo_flag);
                startActivityForResult(intent, 1234);
                finish();
            }
        });

    }
}
