package org.androidtown.sina_ver01;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private int feelNum=1;
    String subtitle;
    DiaryData data = new DiaryData();
    /**
     * 연/월 텍스트뷰
     */
    private TextView tvDate;
    /**
     * 그리드뷰 어댑터
     */
    private GridAdapter gridAdapter;
    /**
     * 일 저장 할 리스트
     */
    private ArrayList<String> dayList;
    /**
     * 그리드뷰
     */
    private GridView gridView;
    /**
     * 캘린더 변수
     */
    private Calendar mCal;
    Button prevButton;
    Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        /*감정 번들 풀기*/
        Intent myIntent = getIntent();
        Bundle myBundle = myIntent.getExtras();
        int emo_flag = myBundle.getInt("emo_num");
        String edit = myBundle.getString("subtitle");
        /*******************************************************/

        feelNum = emo_flag;
        subtitle = "" + edit;
        //Log.i("기분값" , "? " + data.getEmotion());

        tvDate = (TextView) findViewById(R.id.tmonth);

        gridView = (GridView) findViewById(R.id.gridview);

        prevButton = (Button) findViewById(R.id.prev);

        nextButton = (Button) findViewById(R.id.next);

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCalendar(-1);
            }
        });


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCalendar(1);
            }
        });
// 오늘에 날짜를 세팅 해준다.

        long now = System.currentTimeMillis();

        final Date date = new Date(now);

//연,월,일을 따로 저장

        final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);

        final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);

        final SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);


//현재 날짜 텍스트뷰에 뿌려줌

        tvDate.setText("    " + curYearFormat.format(date) + "/" + curMonthFormat.format(date));


//gridview 요일 표시

        dayList = new ArrayList<String>();

        dayList.add("일");

        dayList.add("월");

        dayList.add("화");

        dayList.add("수");

        dayList.add("목");

        dayList.add("금");

        dayList.add("토");

        mCal = Calendar.getInstance();

//이번달 1일 무슨요일인지 판단 mCal.set(Year,Month,Day)

        gridAdapter = new GridAdapter(this, dayList);

        gridView.setAdapter(gridAdapter);

        setCalendar(0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //글꼴 설정
        SetActivity.mtypeface= Typeface.createFromAsset(getAssets(),SetActivity.fonts);
        ViewGroup root3=(ViewGroup)findViewById(android.R.id.content);
        SetActivity.setGlobalFont(root3);

    }


    /**
     * 해당 월에 표시할 일 수 구함
     *
     * @param month
     */

    private void setCalendarDate(int month) {

        mCal.set(Calendar.MONTH, month - 1);


        for (int i = 0; i < mCal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {

            dayList.add("" + (i + 1));
        }

    }

    private void setCalendar(int value) {
        dayList.clear();
        mCal.set(Calendar.DATE, 1);
        mCal.add(Calendar.MONTH, value);

        tvDate.setText("    "+mCal.get(Calendar.YEAR) + "년" + (mCal.get(Calendar.MONTH) + 1) + "월");

        int dayNum = mCal.get(Calendar.DAY_OF_WEEK);

//1일 - 요일 매칭 시키기 위해 공백 add

        for (int i = 1; i < dayNum; i++) {
            dayList.add("empty");
        }

        for (int i = 0; i < (mCal.getActualMaximum(Calendar.DAY_OF_MONTH)); i++) {
            dayList.add(String.valueOf(i + 1));
        }

        gridAdapter.notifyDataSetChanged();
    }

    /**
     * 그리드뷰 어댑터
     */

    private class GridAdapter extends BaseAdapter implements View.OnClickListener {
        private CalendarActivity context;

        private List<String> list;

        private LayoutInflater inflater;


        /**
         * 생성자
         *
         * @param context
         * @param list
         */

        public GridAdapter(CalendarActivity context, List<String> list) {
            this.context = context;
            this.list = list;

            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }


        @Override

        public int getCount() {

            return list.size();

        }


        @Override

        public String getItem(int position) {

            return list.get(position);

        }


        @Override

        public long getItemId(int position) {

            return position;

        }


        @Override

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_calendar_gridview_calendar, parent, false);

                holder = new ViewHolder();

                holder.tvItemGridView = (TextView) convertView.findViewById(R.id.tv_item_gridview);
                if(!list.get(position).equals("empty")) {
                    holder.feelImage = (ImageView) convertView.findViewById(R.id.feel);
                    if (feelNum == 0) {
                        //Log.v("들어옴","값" + feelNum);
                        holder.feelImage.setImageResource(R.drawable.happy);
                    } else if (feelNum == 1) {
                        //Log.v("들어옴","값" + feelNum);
                        holder.feelImage.setImageResource(R.drawable.angry);
                    } else if (feelNum == 2) {
                        //Log.v("들어옴","값" + feelNum);
                        holder.feelImage.setImageResource(R.drawable.sad);
                    }
                }
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if(!list.get(position).equals("empty")) {
                holder.tvItemGridView.setText(list.get(position));
                convertView.setOnClickListener(this);
            } else {
                holder.tvItemGridView.setText("");
                convertView.setClickable(false);
            }

//해당 날짜 텍스트 컬러,배경 변경

//오늘 day 가져옴

            Calendar c= Calendar.getInstance();
            int today = c.get(Calendar.DATE);

            String sToday = String.valueOf(today);

            if (sToday.equals(list.get(position))) { //오늘 day 텍스트 컬러 변경
                holder.tvItemGridView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            }

            return convertView;

        }

        @Override
        public void onClick(View v) {
            ViewHolder viewHolder = (ViewHolder)v.getTag();

            startActivity(new Intent((CalendarActivity)context, LayoutMain.class));
        }

        private class ViewHolder {
            TextView tvItemGridView;
            ImageView feelImage;
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.calendar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_write) {
            //Intent intent=new Intent(getApplicationContext(), LayoutMain.class);
            //startActivity(intent);
            /*번들 넘기기*/
            Intent intent = new Intent(CalendarActivity.this, SelectEmotionActivity.class);
            Bundle myBundle = new Bundle();
            myBundle.putInt("emo_num", feelNum);
            myBundle.putString("subtitle",subtitle);
            intent.putExtras(myBundle);
            //Log.i("번호","넘기기전"+emo_flag);
            startActivityForResult(intent,5678);
        }else if(id == R.id.nav_show){
            Intent intent=new Intent(getApplicationContext(),ShowActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_filter) {
            //Intent intent=new Intent(getApplicationContext(),SortingActivity.class);
            //startActivity(intent);
            // finish();
            /*번들 넘기기*/
            Intent intent = new Intent(CalendarActivity.this, SortingActivity.class);
            Bundle myBundle = new Bundle();
            myBundle.putInt("emo_num", feelNum);
            myBundle.putString("subtitle",subtitle);
            intent.putExtras(myBundle);
            //Log.i("번호","넘기기전"+emo_flag);
            startActivityForResult(intent,1345);
        } else if (id == R.id.nav_static) {
            Intent intent=new Intent(getApplicationContext(),StaticsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_set) {
            Intent intent=new Intent(getApplicationContext(),SetActivity.class);
            startActivity(intent);
            //finished();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}