package org.androidtown.sina_ver01;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PasswordActivity extends AppCompatActivity {

    DiaryData data;

    Button b1;
    Button b2;
    Button b3;
    Button b4;
    Button b5;
    Button b6;
    Button b7;
    Button b8;
    Button b9;
    Button b10;
    Button b0;
    Button b11;

    ImageView img1;
    ImageView img2;
    ImageView img3;
    ImageView img4;


    String pw = "";
    String password="";
    String nowpw;
    String num;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);



        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mRef1 = FirebaseDatabase.getInstance().getReference().child("users").child(mUser.getUid()).child("diary").child("password").child("num");
        DatabaseReference mRef2 = FirebaseDatabase.getInstance().getReference().child("users").child(mUser.getUid()).child("diary").child("password").child("pw");

        mRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot messageData:dataSnapshot.getChildren()) {
                    num = messageData.getValue().toString();
                    Log.d("now_password", num);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot messageData:dataSnapshot.getChildren()) {
                    nowpw = messageData.getValue().toString();
                    Log.d("now_password", nowpw);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        b1 = findViewById(R.id.button1);
        b2 = findViewById(R.id.button2);
        b3 = findViewById(R.id.button3);
        b4 = findViewById(R.id.button4);
        b5 = findViewById(R.id.button5);
        b6 = findViewById(R.id.button6);
        b7 = findViewById(R.id.button7);
        b8 = findViewById(R.id.button8);
        b9 = findViewById(R.id.button9);
        b10 = findViewById(R.id.button10);
        b0 = findViewById(R.id.button0);
        b11 = findViewById(R.id.button11);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password += "1";checkPassword();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password += "2";checkPassword();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password += "3";checkPassword();
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password += "4";checkPassword();
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password += "5";checkPassword();
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password += "6";checkPassword();
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password += "7";checkPassword();
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password += "8";checkPassword();
            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password += "9";checkPassword();
            }
        });
        b10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password += "*";checkPassword();
            }
        });
        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password += "0";checkPassword();
            }
        });
        b11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password += "#";checkPassword();
            }
        });

        //글꼴 설정
        SetActivity.mtypeface= Typeface.createFromAsset(getAssets(),SetActivity.fonts);
        ViewGroup root3=(ViewGroup)findViewById(android.R.id.content);
        SetActivity.setGlobalFont(root3);

    }

    private void checkPassword()
    {
        img1=findViewById(R.id.pImage1);
        img2=findViewById(R.id.pImage2);
        img3=findViewById(R.id.pImage3);
        img4=findViewById(R.id.pImage4);

        if(password.length()==(1))
        {
            img1.setVisibility(View.VISIBLE);
        }
        else if(password.length()==(2))
        {
            img2.setVisibility(View.VISIBLE);
        }
        else if(password.length()==(3))
        {
            img3.setVisibility(View.VISIBLE);
        }

        else if(password.length()==(4))
        {
            img4.setVisibility(View.VISIBLE);

            if(!password.equals(nowpw))
            {
                password="";
                img1.setVisibility(View.INVISIBLE);
                img2.setVisibility(View.INVISIBLE);
                img3.setVisibility(View.INVISIBLE);
                img4.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
            }
            else if(password.equals(nowpw))
            {
                startActivity(new Intent(getApplicationContext(), SelectEmotionActivity.class));
            }
        }
    }

    public boolean onKeyDown(int keyCode,KeyEvent event){
        switch(keyCode){
            case android.view.KeyEvent.KEYCODE_BACK:
                back();
        }
        return false;
    }

    public void back()
    {
        Intent i = new Intent(getApplicationContext(),SecondMain.class);
        startActivity(i);
        finish();
    }
}