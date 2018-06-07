package org.androidtown.sina_ver01;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

    DiaryData text;
    DiaryData pic;

    Calendar date;

    ArrayList<DiaryData> fileList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        fileList = new ArrayList<>();

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        View.OnClickListener mListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = mRecyclerView.getChildAdapterPosition(v);
                DiaryData diaryData = fileList.get(position);
                Intent intent = new Intent(ShowActivity.this, DiaryActivity.class);
                Bundle myBundle = new Bundle();
                myBundle.putParcelable("data", diaryData);
                intent.putExtras(myBundle);
                //Log.i("번호","넘기기전"+emo_flag);
                startActivity(intent);
            }
        };
        mAdapter = new ShowAdapter(this, fileList, mListener);
        mRecyclerView.setAdapter(mAdapter);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference textRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("diary").child("text");
        DatabaseReference picRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("diary").child("picture");

        //text
        textRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                text = dataSnapshot.getValue(DiaryData.class);
                text.setId(dataSnapshot.getKey());
                fileList.add(text);

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                int position;
                DiaryData diaryData = dataSnapshot.getValue(DiaryData.class);
                diaryData.setId(dataSnapshot.getKey());

                for(position=0; position<fileList.size(); position++) {
                    if(fileList.get(position).getId().equals(diaryData.getId()))
                        break;
                }

                fileList.remove(position);

                fileList.add(position, diaryData);

                mAdapter.notifyDataSetChanged();
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

        picRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                pic = dataSnapshot.getValue(DiaryData.class);
                pic.setId(dataSnapshot.getKey());
                fileList.add(pic);

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                int position;
                DiaryData diaryData = dataSnapshot.getValue(DiaryData.class);
                diaryData.setId(dataSnapshot.getKey());

                for(position=0; position<fileList.size(); position++) {
                    if(fileList.get(position).getId().equals(diaryData.getId()))
                        break;
                }

                fileList.remove(position);

                fileList.add(position, diaryData);

                mAdapter.notifyDataSetChanged();

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
        View.OnClickListener mListener;


        public ShowAdapter(Context context, ArrayList<DiaryData> items, View.OnClickListener mListener) {
            this.mContext = context;
            this.items = items;
            this.mListener = mListener;
        }

        @NonNull
        @Override
        public ShowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ShowAdapter.ViewHolder holder, int position) {
            ViewHolder myViewHolder = (ViewHolder) holder;

            if(items.get(position).getEmotion()==0 ) {
                myViewHolder.ivPicture.setImageResource(R.drawable.happy);
            }else if(items.get(position).getEmotion()==1){
                myViewHolder.ivPicture.setImageResource(R.drawable.angry);
            }else if(items.get(position).getEmotion()==2){
                myViewHolder.ivPicture.setImageResource(R.drawable.sad);
            }
            myViewHolder.tvDate.setText(items.get(position).getDate());
            myViewHolder.tvSubtitle.setText(items.get(position).getSubtitle());

        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            ImageView ivPicture;
            TextView tvSubtitle;
            TextView tvDate;

            public ViewHolder(View itemView) {
                super(itemView);

                ivPicture = itemView.findViewById(R.id.iv_picture);
                tvDate = itemView.findViewById(R.id.tv_date);
                tvSubtitle = itemView.findViewById(R.id.tv_subtitle);

                if(mListener != null)
                    itemView.setOnClickListener(mListener);
            }
        }
    }
}
