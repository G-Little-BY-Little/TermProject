package org.androidtown.sina_ver01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class DrawBoard extends AppCompatActivity {
    private ImageButton currPaint;
    private SingleTouchView drawView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_board);
        drawView = (SingleTouchView)findViewById(R.id.drawing);
        LinearLayout paintLayout = (LinearLayout)findViewById(R.id.paint_colors);
        currPaint = (ImageButton)paintLayout.getChildAt(0);
    }
    public void clicked(View view){
        if(view!=currPaint){
            String color = view.getTag().toString();
            drawView.setColor(color);
            currPaint=(ImageButton)view;
        }
    }
}
