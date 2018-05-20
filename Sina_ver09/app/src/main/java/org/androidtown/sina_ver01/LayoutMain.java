package org.androidtown.sina_ver01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

public class LayoutMain extends AppCompatActivity {

    ImageButton emptyBnt;
    ImageButton letterBnt;
    ImageButton drawBnt;
    ImageButton pictureBnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_main);

        setToolBar();

        emptyBnt=(ImageButton)findViewById(R.id.emptyBtn);
        letterBnt=(ImageButton)findViewById(R.id.letterBtn);
        drawBnt=(ImageButton)findViewById(R.id.drawBtn);
        pictureBnt=(ImageButton)findViewById(R.id.pictureBtn);

        emptyBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"빈 레이아웃",Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(getApplicationContext(),SetActivity.class));
            }
        });

        letterBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"편지지 레이아웃",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),LetterActivity.class));
            }
        });

        drawBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"그림일기 레이아웃",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),DrawLayout.class));
            }
        });

        pictureBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"사진 레이아웃",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),PictureLayActivity.class));
            }
        });
    }

    public void setToolBar(){
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.findViewById(R.id.menuBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"버튼",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
