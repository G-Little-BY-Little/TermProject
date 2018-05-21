package org.androidtown.sina_ver01;

import android.app.DatePickerDialog;
import android.content.Context;
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
import android.widget.DatePicker;
import android.widget.ImageButton;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SortingActivity extends AppCompatActivity {
    private ListView listView;
    ArrayList<Data> fileList = new ArrayList<>();
    DiaryAdapter mAdapter;
    static boolean calledAlready = false;

    ImageButton button1;
    ImageButton button2;
    ImageButton button3;

    Context context;

    TextView mDisplayStartDate;
    TextView mDisplayEndDate;
    DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorting);
        button1 = (ImageButton) findViewById(R.id.feeling1);
        button2 = (ImageButton) findViewById(R.id.feeling2);
        button3 = (ImageButton) findViewById(R.id.feeling3);
        context = getApplicationContext();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "버튼1", Toast.LENGTH_LONG).show();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "버튼2", Toast.LENGTH_LONG).show();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "버튼3", Toast.LENGTH_LONG).show();
            }
        });

        mDisplayStartDate = (TextView) findViewById(R.id.StartDate);

        mDisplayStartDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        SortingActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d("Page12", "onDateSet: yyyy/mm/dd: " + year + "년" + month + "월" + day + "일");

                String date = year + "년" + month + "월" + day + "일";
                mDisplayStartDate.setText(date);
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
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d("Page12", "onDateSet: yyyy/mm/dd: " + year + "년" + month + "월" + day + "일");

                String date = year + "년" + month + "월" + day + "일";
                mDisplayEndDate.setText(date);
            }
        };
        listView= (ListView)  findViewById(R.id.lv_fileList);
        mAdapter = new DiaryAdapter(context, fileList);
        listView.setAdapter(mAdapter);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference textRef = database.getReference("users").child(user.getUid()).child("diary").child("text");

        textRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Data text = dataSnapshot.getValue(Data.class);
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
        private ArrayList<Data> items;

        public DiaryAdapter() {}
        public DiaryAdapter(Context context, ArrayList<Data> items) {
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

            Data data = items.get(position);

            viewHolder.title.setText(data.getTitle());
            viewHolder.subtitle.setText(data.getSubtitle());
            viewHolder.date.setText(data.getDate());

            return convertView;
        }

        private class ViewHolder {
            TextView title, subtitle, date;

            public ViewHolder(View view) {
                title = (TextView) view.findViewById(R.id.item_title);
                subtitle = (TextView) view.findViewById(R.id.item_subtitle);
                date = (TextView) view.findViewById(R.id.item_date);
            }
        }
    }
}
