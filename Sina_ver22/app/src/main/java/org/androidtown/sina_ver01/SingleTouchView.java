package org.androidtown.sina_ver01;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.FileOutputStream;

public class SingleTouchView extends View {
    private Paint paint, canvasPaint;
    private Path path;
    private Canvas drawCanvas;
    private int paintColor = 0xFF000000;
    private Bitmap canvasBitmap;


    public SingleTouchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        drawCanvas = new Canvas();
        canvasBitmap = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888);

        setUpDrawing();
    }
    public void reset(){
        canvasBitmap.eraseColor(Color.WHITE);
        invalidate();
    }
    public void setUpDrawing(){
        path = new Path();
        paint = new Paint();

        paint.setAntiAlias(true);
        paint.setStrokeWidth(10f);
        paint.setColor(paintColor);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);

        canvasPaint= new Paint(paint.DITHER_FLAG);
    }
    //public void setSize(){
    //
    //}
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //view크기가 변경되었을때 호출
        super.onSizeChanged(w, h, oldw, oldh);
        drawCanvas.setBitmap(canvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //view 핵심 영역
        //실제로 화면을 그린다.

        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(path, paint);

        }

    /*public void saveView(View view)
    {
        Bitmap b = Bitmap.createBitmap(view.getWidth(),view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        view.draw(c);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream( "/sdcard/some_view_image_" + System.currentTimeMillis() + ".png" );
            if ( fos != null )
            {
                b.compress(Bitmap.CompressFormat.PNG, 100, fos );
                fos.close();
            }
        } catch( Exception e )
        {
            Log.e("testSaveView", "Exception: " + e.toString() );
        }
    }*/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //touch에 대한 listener 함수
        //detect user touch
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                path.lineTo(touchX,touchY);
                drawCanvas.drawPath(path, paint);
                path.reset();
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }

    public void setColor(String newColor) {
        invalidate();
        paintColor = Color.parseColor(newColor);
        paint.setColor(paintColor);
        // Log.v("색깔 넘어오기", "색은" + paintColor);
    }
}