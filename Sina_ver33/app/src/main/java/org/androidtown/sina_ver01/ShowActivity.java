package org.androidtown.sina_ver01;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class ShowActivity extends AppCompatActivity{

    RecyclerView mRecyclerView;
    ShowAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    DiaryData text;
    DiaryData pic;
    DiaryData draw;
    DiaryData empty;

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


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference textRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("diary").child("text");
        final DatabaseReference picRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("diary").child("picture");
        final DatabaseReference drawRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("diary").child("draw");
        final DatabaseReference emptyRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("diary").child("empty");

        View.OnLongClickListener mLongListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final int position = mRecyclerView.getChildAdapterPosition(v);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ShowActivity.this);

                alertDialogBuilder.setTitle("다이어리 삭제");
                alertDialogBuilder
                        .setMessage("다이어리를 삭제하시겠습니까?")
                        .setCancelable(false)
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                switch (fileList.get(position).getType()) {
                                    case 1:
                                        textRef.child(fileList.get(position).getId()).removeValue();
                                        break;
                                    case 3:
                                        picRef.child(fileList.get(position).getId()).removeValue();
                                        break;
                                    case 2:
                                        drawRef.child(fileList.get(position).getId()).removeValue();
                                        break;
                                    case 4:
                                        emptyRef.child(fileList.get(position).getId()).removeValue();
                                        break;
                                }
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.show();

                return true;
            }
            ;
        };
        mAdapter = new ShowAdapter(this, fileList, mListener,mLongListener);
        mRecyclerView.setAdapter(mAdapter);

        //text
        textRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                text = dataSnapshot.getValue(DiaryData.class);
                text.setId(dataSnapshot.getKey());
                fileList.add(text);

                Collections.sort(fileList);
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
                int position;
                for(position=0; position<fileList.size(); position++) {
                    if(dataSnapshot.getKey().equals(fileList.get(position).getId())){
                        break;
                    }
                }

                fileList.remove(position);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //picture
        picRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                pic = dataSnapshot.getValue(DiaryData.class);
                pic.setId(dataSnapshot.getKey());
                fileList.add(pic);

                Collections.sort(fileList);
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
                int position;
                for(position=0; position<fileList.size(); position++) {
                    if(dataSnapshot.getKey().equals(fileList.get(position).getId())){
                        break;
                    }
                }

                fileList.remove(position);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //draw
        drawRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                draw = dataSnapshot.getValue(DiaryData.class);
                draw.setId(dataSnapshot.getKey());
                fileList.add(draw);

                Collections.sort(fileList);
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
                int position;
                for(position=0; position<fileList.size(); position++) {
                    if(dataSnapshot.getKey().equals(fileList.get(position).getId())){
                        break;
                    }
                }

                fileList.remove(position);
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //empty
        emptyRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                empty = dataSnapshot.getValue(DiaryData.class);
                empty.setId(dataSnapshot.getKey());
                fileList.add(empty);

                Collections.sort(fileList);
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
                int position;
                for(position=0; position<fileList.size(); position++) {
                    if(dataSnapshot.getKey().equals(fileList.get(position).getId())){
                        break;
                    }
                }

                fileList.remove(position);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    }


   class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.ViewHolder>{
        Context mContext;
        ArrayList<DiaryData> items;
        View.OnClickListener mListener;
        View.OnLongClickListener mLongListener;


        public ShowAdapter(Context context, ArrayList<DiaryData> items, View.OnClickListener mListener,View.OnLongClickListener mLongListener) {
            this.mContext = context;
            this.items = items;
            this.mListener = mListener;
            this.mLongListener = mLongListener;
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
                myViewHolder.vLabel.setBackgroundColor(Color.rgb(255,228,0));
            }else if(items.get(position).getEmotion()==1){
                myViewHolder.ivPicture.setImageResource(R.drawable.angry);
                myViewHolder.vLabel.setBackgroundColor(Color.rgb(204,61,61));
            }else if(items.get(position).getEmotion()==2){
                myViewHolder.ivPicture.setImageResource(R.drawable.sad);
                myViewHolder.vLabel.setBackgroundColor(Color.rgb(67,116,217));
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
            View vLabel;

            public ViewHolder(View itemView) {
                super(itemView);

                ivPicture = itemView.findViewById(R.id.iv_picture);
                tvDate = itemView.findViewById(R.id.tv_date);
                tvSubtitle = itemView.findViewById(R.id.tv_subtitle);
                vLabel = itemView.findViewById(R.id.labelView);

                if(mListener != null)
                    itemView.setOnClickListener(mListener);
                if(mLongListener != null)
                    itemView.setOnLongClickListener(mLongListener);
            }

        }
    }



