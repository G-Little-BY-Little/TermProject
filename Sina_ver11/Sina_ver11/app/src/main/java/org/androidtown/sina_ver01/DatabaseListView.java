package org.androidtown.sina_ver01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DatabaseListView extends AppCompatActivity {

    private ListView listView;
    List fileList = new ArrayList<>();
    ArrayAdapter adapter;
    static boolean calledAlready = false;

    public void getDatabase() {
           }

}
