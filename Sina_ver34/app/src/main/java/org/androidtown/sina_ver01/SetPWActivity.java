package org.androidtown.sina_ver01;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SetPWActivity extends AppCompatActivity {

    private SharedPrefManager mySharedPrefManager;

    String password = "";
    ToggleButton tb;
    DatabaseReference mRef1;
    DatabaseReference mRef2;
    Button changePW;
    EditText enterPW;
    Button enter;
    LinearLayout passwordLayout;

    String username = "";

    String pw;
    String num;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pw);

        mySharedPrefManager = SharedPrefManager.getInstance(this);

        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        mRef1 = FirebaseDatabase.getInstance().getReference().child("users").child(mUser.getUid()).child("diary").child("password").child("num");
        mRef2 = FirebaseDatabase.getInstance().getReference().child("users").child(mUser.getUid()).child("diary").child("password").child("pw");
        passwordLayout = findViewById(R.id.Password);
        tb = (ToggleButton) this.findViewById(R.id.toggleButton1);
        enterPW = findViewById(R.id.enterPW);
        changePW = findViewById(R.id.changePW);
        enter = findViewById(R.id.enter);

        num=mySharedPrefManager.getUserIsFirst();
        pw=mySharedPrefManager.getUserPassword();

        tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tb.isChecked()) {
                    if (num.equals("0") && pw.equals("")) {
                        passwordLayout.setVisibility(View.VISIBLE);
                        enter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pw = enterPW.getText().toString();
                                mySharedPrefManager.setUserIsFirst("1");
                                mySharedPrefManager.setUserPassword(pw);
                                finish();
                            }
                        });
                    }
                }else if(num.equals("0") && !pw.equals("")){
                    mySharedPrefManager.setUserIsFirst("1");
                    mySharedPrefManager.setUserPassword(pw);
                }
                else {
                    mySharedPrefManager.setUserIsFirst("0");
                    mySharedPrefManager.setUserPassword(pw);
                }
            }
        });

        changePW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordLayout.setVisibility(View.VISIBLE);
                enter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pw = enterPW.getText().toString();
                        mySharedPrefManager.setUserIsFirst("1");
                        mySharedPrefManager.setUserPassword(pw);
                        finish();
                    }
                });
            }
        });

        mRef1.push().setValue(mySharedPrefManager.getUserIsFirst());
        mRef2.push().setValue(mySharedPrefManager.getUserName());

    }
}