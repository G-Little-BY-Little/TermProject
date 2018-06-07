/*package org.androidtown.sina_ver01;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class ShowActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ShowAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    private int emotion;
    private String subtitle;
    Calendar date;

    ArrayList<DiaryData> fileList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        fileList = new ArrayList<>();
        mAdapter = new ShowAdapter(this, fileList);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        Intent myIntent = getIntent();
        Bundle myBundle = myIntent.getExtras();
        int emo_flag = myBundle.getInt("emo_num");
        String edit = myBundle.getString("subtitle");

        emotion = emo_flag;
        subtitle=edit;
        data.setEmotion(emotion);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference textRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("diary").child("text");

        textRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                DiaryData text = dataSnapshot.getValue(DiaryData.class);
                fileList.add(text);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.ViewHolder> {
        Context mContext;
        ArrayList<DiaryData> items;

        public ShowAdapter(Context context, ArrayList<DiaryData> items) {
            this.mContext = context;
            this.items = items;
        }

        @NonNull
        @Override
        public ShowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ShowAdapter.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}*/
