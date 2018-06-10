package org.androidtown.sina_ver01;


import android.app.Activity;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SecondMain extends AppCompatActivity {

    private BackPressCloseHandler backPressCloseHandler;
    private int flag=1;

    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "SignInActivity";

    private GoogleSignInClient mGoogleSignInClient;
    private TextView mStatusTextView;
    GoogleSignInAccount userAccount;
    private FirebaseAuth.AuthStateListener mListener;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    SharedPreferences sh_pref;
    SharedPreferences.Editor toEdit;
    String username="";


    String pw = "0000";
    String num="0";

    String nowpw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_main);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        backPressCloseHandler = new BackPressCloseHandler(this);

        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        mListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mUser = mAuth.getCurrentUser();

                if (mUser != null) {
                    username =mUser.getDisplayName();
                    sharedPrefernces();

                    DatabaseReference mRef1 = FirebaseDatabase.getInstance().getReference().child("users").child(mUser.getUid()).child("diary").child("password").child("num");
                    DatabaseReference mRef2 = FirebaseDatabase.getInstance().getReference().child("users").child(mUser.getUid()).child("diary").child("password").child("pw");

                    mRef1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot messageData:dataSnapshot.getChildren()) {
                                num = messageData.getValue().toString();
                                Log.d("now_num", num);
                                flag=Integer.parseInt(num);
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
                    Log.d("엥",flag+"");
                    if(flag == 1){
                        startActivity(new Intent(getApplicationContext(),PasswordActivity.class));
                    }else if(flag == 0)
                    {
                        startActivity(new Intent(getApplicationContext(),SelectEmotionActivity.class));
                    }
                    finish();
                } else {
                    init();
                }
            }
        };
    }

    public void init()
    {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // gso로 지정된 옵션을 사용하여 GoogleSignInClient를 빌드합니다.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    public void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mListener);
    }

    // [START onactivityresult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // [START_EXCLUDE]

                // [END_EXCLUDE]
            }
        }
    }
    // [END onactivityresult]

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());

                        }
                    }
                });
    }
    // [END auth_with_google]

    // [START signin]
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signin]

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }

    private void revokeAccess() {
        // Firebase sign out
        mAuth.signOut();

        // Google revoke access
        mGoogleSignInClient.revokeAccess().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }


    //로그인 된 사용자의 개인정보 확인.
    //메일, 이름, 나이, 성별 확인 필요.
    private void profile() {
        String email = userAccount.getEmail();
        String id = userAccount.getId();
        String familyname = userAccount.getFamilyName();
        String givenname = userAccount.getGivenName();

        Toast.makeText(getApplicationContext(), "email : " + email + "\nid = " + id + "\nfamilyname = " + familyname + "\ngivenname = " + givenname, Toast.LENGTH_SHORT).show();

    }


    @Override
    protected void onStop() {
        super.onStop();

        mAuth.removeAuthStateListener(mListener);
    }
    public void sharedPrefernces() {
        sh_pref = getSharedPreferences("Login Credentials", MODE_PRIVATE);
        toEdit = sh_pref.edit();
        toEdit.putString("Username", username);
        toEdit.commit();
    }


}
