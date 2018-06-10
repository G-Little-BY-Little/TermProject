package org.androidtown.sina_ver01;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SetPWActivity extends AppCompatActivity {
    String nowpw;
    String pw = "0000";
    String password="";
    String num;
    ToggleButton tb;
    DatabaseReference mRef1;
    DatabaseReference mRef2;
    Button changePW;
    EditText enterPW;
    Button enter;
    LinearLayout passwordLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pw);


        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        mRef1 = FirebaseDatabase.getInstance().getReference().child("users").child(mUser.getUid()).child("diary").child("password").child("num");
        mRef2 = FirebaseDatabase.getInstance().getReference().child("users").child(mUser.getUid()).child("diary").child("password").child("pw");

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

        if(nowpw==null)
            mRef2.push().setValue("0000");


        tb=(ToggleButton)this.findViewById(R.id.toggleButton1);

        if(num=="1")
            tb.setTextOn("비밀번호 사용");
        else
            tb.setTextOff("비밀번호 사용안함");

        tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tb.isChecked()) {
                    tb.setTextColor(Color.BLACK);
                    mRef1.push().setValue("1");
                }else{
                    tb.setTextColor(Color.RED);
                    mRef1.push().setValue("0");
                }
            }
        });
        passwordLayout=findViewById(R.id.Password);

        enterPW=findViewById(R.id.enterPW);
        changePW=findViewById(R.id.changePW);
        enter=findViewById(R.id.enter);
        changePW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordLayout.setVisibility(View.VISIBLE);
                enter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mRef2.push().setValue(enterPW.getText().toString());
                    }
                });
            }
        });

    }
}