package org.androidtown.sina_ver01;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.navdrawer.SimpleSideDrawer;


public class SetActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    ImageButton logoutBtn;
    ImageButton letterlogoBtn;
    ImageButton pwlogoBtn;

    private GoogleSignInClient mGoogleSignInClient;

    public static Typeface mtypeface;
    public static String fonts="fonts/PNH_FRIENDNET.TTF";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        setToolBar();
        setDrawer();
    }

    public void setToolBar() {
        Toolbar toolbarSet = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbarSet);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_memo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setButton();
        toolbarSet.findViewById(R.id.startBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "첫 페이지로 버튼", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void  setButton(){
        logoutBtn = (ImageButton) findViewById(R.id.logoutBtn);
        letterlogoBtn = (ImageButton) findViewById(R.id.letterlogoBtn);
        pwlogoBtn = (ImageButton) findViewById(R.id.pwlogoBtn);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_logout(v);
            }
        });

        letterlogoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerForContextMenu(letterlogoBtn);
                Toast.makeText(getApplicationContext(), "글꼴 변경", Toast.LENGTH_SHORT).show();
            }
        });

        pwlogoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "비밀번호 변경", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), SetPWActivity.class));
            }
        });
    }
    public void btn_logout(View v){
        new AlertDialog.Builder(this)
                .setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
                .setPositiveButton("로그아웃",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int whichButton){
                        Intent i = new Intent(getApplicationContext(),SecondMain.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        signOut();
                        startActivity(i);
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }


    private void signOut() {
        // Firebase sign out
        FirebaseAuth.getInstance().signOut();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }

    private void setDrawer() {
        drawer = findViewById(R.id.drawer_set);
        ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close);
        drawer.addDrawerListener(mToggle);
        mToggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_item1:

        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("FontMenu");
        menu.add(0, 1, 0, "은하수");
        menu.add(0, 2, 0, "PNK 친구넷");
        menu.add(0, 3, 0, "rix별과나");
    }

    public boolean onContextItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case 1:
                fonts="fonts/MILKYWAY.TTF";
                mtypeface= Typeface.createFromAsset(getAssets(),"fonts/MILKYWAY.TTF");
                ViewGroup root=(ViewGroup)findViewById(android.R.id.content);
                setGlobalFont(root);
                break;
            case 2:
                fonts="fonts/PNH_FRIENDNET.TTF";
                mtypeface=Typeface.createFromAsset(getAssets(),"fonts/PNH_FRIENDNET.TTF");
                ViewGroup root2=(ViewGroup)findViewById(android.R.id.content);
                setGlobalFont(root2);
                break;
            case 3:
                fonts="fonts/RIX_STAR_N_ME.TTF";
                mtypeface=Typeface.createFromAsset(getAssets(),"fonts/RIX_STAR_N_ME.TTF");
                ViewGroup root3=(ViewGroup)findViewById(android.R.id.content);
                setGlobalFont(root3);
                break;
        }
        return false;
    }

    public static void setGlobalFont(ViewGroup root){
        for(int i=0;i<root.getChildCount();i++){
            View child=root.getChildAt(i);
            if(child instanceof TextView)
            {
                ((TextView)child).setTypeface(mtypeface);
            }
            else if(child instanceof ViewGroup)
            {
                setGlobalFont((ViewGroup)child);
            }
        }
    }
}