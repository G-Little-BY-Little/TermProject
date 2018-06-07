package org.androidtown.sina_ver01;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StaticsActivity extends AppCompatActivity {

    PieChart pieChart;
    ImageButton rec_btn;
    DiaryData data = new DiaryData();
    int data_total, happy_total, sad_total, angry_total;
    float happy_ratio, sad_ratio, angry_ratio;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statics);

        pieChart = findViewById(R.id.piechart);
        rec_btn = (ImageButton) findViewById(R.id.recommend_btn);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference textRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("diary").child("text");

        textRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    data = snapshot.getValue(DiaryData.class);
                    data.setId(snapshot.getKey());
                    //Log.v("들어옴","들어옴1");
                    data_total++;
                    switch (data.getEmotion()) {
                        case 0:
                            //Log.v("들어옴","들어옴2");
                            happy_total++;
                            break;
                        case 1:
                            //Log.v("들어옴","들어옴3");
                            angry_total++;
                            break;
                        case 2:
                            //Log.v("들어옴","들어옴4");
                            sad_total++;
                            break;
                    }

                    happy_ratio = (float)happy_total/data_total;
                    sad_ratio = (float)sad_total/data_total;
                    angry_ratio = (float)angry_total/data_total;

                    chart(happy_ratio,sad_ratio,angry_ratio);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        rec_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DialogActivity.class));
            }
        });

        //글꼴 설정
        SetActivity.mtypeface = Typeface.createFromAsset(getAssets(), SetActivity.fonts);
        ViewGroup root3 = (ViewGroup) findViewById(android.R.id.content);
        SetActivity.setGlobalFont(root3);

    }

    public void chart(float a, float b, float c) {

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.BLACK);
        pieChart.setTransparentCircleRadius(61f);

        ArrayList<PieEntry> yValues = new ArrayList();

        yValues.add(new PieEntry(happy_ratio, "행복"));
        yValues.add(new PieEntry(angry_ratio, "분노"));
        yValues.add(new PieEntry(sad_ratio, "슬픔")); //여기에서 표정 갯수로 퍼센트나눠서..알아서하기

        /*Description description = new Description();
        description.setText("세계 국가"); //라벨
        description.setTextSize(15);
        pieChart.setDescription(description);*/

        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic); //애니메이션

        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(15f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);

    }
}
