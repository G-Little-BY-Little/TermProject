package org.androidtown.sina_ver01;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

    int checkFeel;
    String mStart, mEnd, cDate;
    CheckBox happy_check;
    CheckBox angry_check;
    CheckBox sad_check;
    Button okBtn;

    Context context;

    TextView mDisplayStartDate;
    TextView mDisplayEndDate;
    DatePickerDialog.OnDateSetListener mStartListener, mEndListener;

    private int feelNum;
    private String subtitle;

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
        okBtn = (Button)findViewById(R.id.check);
        context = getApplicationContext();
        cDate=data.getDate().toString();

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
                month = month + 1;
                Log.d("Page12", "onDateSet: yyyy/mm/dd: " + year + "년" + month + "월" + day + "일");

                String date = year + "년" + month + "월" + day + "일";
                mDisplayStartDate.setText(date);
                date = year + "-" + month + "-" + day;
                mStart = ""+date;
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
                month = month + 1;
                Log.d("Page12", "onDateSet: yyyy/mm/dd: " + year + "년" + month + "월" + day + "일");

                String date = year + "년" + month + "월" + day + "일";
                mDisplayEndDate.setText(date);
                date = year + "-" + month + "-" + day;
                mEnd= ""+date;
            }
        };

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if(happy_check.isChecked()){
                    checkFeel=0;
                }
                if(angry_check.isChecked()){
                    checkFeel=1;
                }
                if(sad_check.isChecked()) {
                    checkFeel=2;
                }
            }
        });

        listView= (ListView)  findViewById(R.id.lv_fileList);
        mAdapter = new DiaryAdapter(context, fileList);
        listView.setAdapter(mAdapter);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference textRef = database.getReference("users").child(user.getUid()).child("diary").child("text");

        textRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                DiaryData text = dataSnapshot.getValue(DiaryData.class);
                text.setId(dataSnapshot.getKey());
                fileList.add(text);
                mAdapter.notifyDataSetChanged();
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

    class DiaryAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<DiaryData> items;

        public DiaryAdapter() {}
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
            if(convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            DiaryData data = items.get(position);

            viewHolder.title.setText(data.getTitle());
            viewHolder.subtitle.setText(subtitle);
            viewHolder.date.setText(data.getDate());
            feelNum=data.getEmotion();

            if(feelNum==0){
                viewHolder.emo_view.setImageResource(R.drawable.happy_32);}
            if(feelNum==1){
                viewHolder.emo_view.setImageResource(R.drawable.angry_32);}
            if(feelNum==2){
                viewHolder.emo_view.setImageResource(R.drawable.sad_32);}

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
