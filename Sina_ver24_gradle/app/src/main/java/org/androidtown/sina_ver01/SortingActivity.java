package org.androidtown.sina_ver01;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SortingActivity extends AppCompatActivity {
    private ListView listView;
    ArrayList<DiaryData> fileList = new ArrayList<>();
    DiaryAdapter mAdapter;
    static boolean calledAlready = false;
    DiaryData data = new DiaryData();
    Calendar mStartCal, mEndCal;
    CheckBox happy_check, angry_check, sad_check;
    Button okBtn;

    Context context;

    TextView mDisplayStartDate;
    TextView mDisplayEndDate;
    DatePickerDialog.OnDateSetListener mStartListener, mEndListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorting);

        /*감정 번들 풀기*/
        Intent myIntent = getIntent();
        Bundle myBundle = myIntent.getExtras();
        int emo_flag = myBundle.getInt("emo_num");
        String edit = myBundle.getString("subtitle");
        /*******************************************************/

        //feelNum =emo_flag;
        //subtitle = ""+edit;

        happy_check = (CheckBox) findViewById(R.id.feeling1);
        angry_check = (CheckBox) findViewById(R.id.feeling2);
        sad_check = (CheckBox) findViewById(R.id.feeling3);
        okBtn = (Button) findViewById(R.id.check);
        context = getApplicationContext();

        mStartCal = Calendar.getInstance();
        mEndCal = Calendar.getInstance();

        mDisplayStartDate = (TextView) findViewById(R.id.StartDate);

        mDisplayStartDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        SortingActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, mStartListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mStartListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Log.d("Page12", "onDateSet: yyyy/mm/dd: " + year + "년" + month + "월" + day + "일");

                mStartCal.set(year, month, day);

                mDisplayStartDate.setText(Utils.calToString(mStartCal.getTime()));
            }
        };

        mDisplayEndDate = (TextView) findViewById(R.id.EndDate);

        mDisplayEndDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        SortingActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, mEndListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });
        mEndListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Log.d("Page12", "onDateSet: yyyy/mm/dd: " + year + "년" + month + "월" + day + "일");

                mEndCal.set(year, month, day);

                mDisplayEndDate.setText(Utils.calToString(mEndCal.getTime()));
            }
        };

        listView = (ListView) findViewById(R.id.lv_fileList);
        mAdapter = new DiaryAdapter(context, fileList);
        listView.setAdapter(mAdapter);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                fileList.clear();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference textRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("diary").child("text");
                DatabaseReference picRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("diary").child("picture");
                DatabaseReference drawRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("diary").child("draw");

                //text
                textRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        DiaryData text = dataSnapshot.getValue(DiaryData.class);
                        Calendar temp = Calendar.getInstance();
                        temp.setTime(Utils.stringToDate(text.getDate()));

                        if ((temp.before(mEndCal) || temp.equals(mEndCal)) && (temp.after(mStartCal) || temp.equals(mStartCal))) {
                            switch (text.getEmotion()) {
                                case 0:
                                    if (happy_check.isChecked()) {
                                        text.setId(dataSnapshot.getKey());
                                        fileList.add(text);
                                    }
                                    break;
                                case 1:
                                    if (angry_check.isChecked()) {
                                        text.setId(dataSnapshot.getKey());
                                        fileList.add(text);
                                    }
                                    break;
                                case 2:
                                    if (sad_check.isChecked()) {
                                        text.setId(dataSnapshot.getKey());
                                        fileList.add(text);
                                    }
                                    break;
                            }
                            mAdapter.notifyDataSetChanged();
                        }
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

                //picture
                picRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        DiaryData pic = dataSnapshot.getValue(DiaryData.class);
                        Calendar temp = Calendar.getInstance();
                        temp.setTime(Utils.stringToDate(pic.getDate()));

                        if ((temp.before(mEndCal) || temp.equals(mEndCal)) && (temp.after(mStartCal) || temp.equals(mStartCal))) {
                            switch (pic.getEmotion()) {
                                case 0:
                                    if (happy_check.isChecked()) {
                                        pic.setId(dataSnapshot.getKey());
                                        fileList.add(pic);
                                    }
                                    break;
                                case 1:
                                    if (angry_check.isChecked()) {
                                        pic.setId(dataSnapshot.getKey());
                                        fileList.add(pic);
                                    }
                                    break;
                                case 2:
                                    if (sad_check.isChecked()) {
                                        pic.setId(dataSnapshot.getKey());
                                        fileList.add(pic);
                                    }
                                    break;
                            }
                            mAdapter.notifyDataSetChanged();
                        }
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

                //draw
                drawRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        DiaryData draw = dataSnapshot.getValue(DiaryData.class);
                        Calendar temp = Calendar.getInstance();
                        temp.setTime(Utils.stringToDate(draw.getDate()));

                        if ((temp.before(mEndCal) || temp.equals(mEndCal)) && (temp.after(mStartCal) || temp.equals(mStartCal))) {
                            switch (draw.getEmotion()) {
                                case 0:
                                    if (happy_check.isChecked()) {
                                        draw.setId(dataSnapshot.getKey());
                                        fileList.add(draw);
                                    }
                                    break;
                                case 1:
                                    if (angry_check.isChecked()) {
                                        draw.setId(dataSnapshot.getKey());
                                        fileList.add(draw);
                                    }
                                    break;
                                case 2:
                                    if (sad_check.isChecked()) {
                                        draw.setId(dataSnapshot.getKey());
                                        fileList.add(draw);
                                    }
                                    break;
                            }
                            mAdapter.notifyDataSetChanged();
                        }
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
        });
        //글꼴 설정
        SetActivity.mtypeface= Typeface.createFromAsset(getAssets(),SetActivity.fonts);
        ViewGroup root3=(ViewGroup)findViewById(android.R.id.content);
        SetActivity.setGlobalFont(root3);
    }

    class DiaryAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<DiaryData> items;

        public DiaryAdapter() {
        }

        public DiaryAdapter(Context context, ArrayList<DiaryData> items) {
            this.context = context;
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            DiaryData data = items.get(position);
            viewHolder.title.setText(data.getTitle());
            viewHolder.subtitle.setText(data.getSubtitle());
            viewHolder.date.setText(data.getDate());

            switch(data.getEmotion()) {
                case 0:
                    viewHolder.emo_view.setImageResource(R.drawable.happy_32);
                    break;
                case 1:
                    viewHolder.emo_view.setImageResource(R.drawable.angry_32);
                    break;
                case 2:
                    viewHolder.emo_view.setImageResource(R.drawable.sad_32);
                    break;
            }

            return convertView;
        }

        private class ViewHolder {
            TextView title, subtitle, date;
            ImageView emo_view;

            public ViewHolder(View view) {
                title = (TextView) view.findViewById(R.id.item_title);
                subtitle = (TextView) view.findViewById(R.id.item_subtitle);
                date = (TextView) view.findViewById(R.id.item_date);
                emo_view = (ImageView) view.findViewById(R.id.item_emotion);

            }
        }
    }
}
