package org.androidtown.sina_ver01;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN);
    private static DateFormat textFormat = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREAN);

    public static String dateToString(Date date) {
        return dateFormat.format(date);
    }

    public static Date stringToDate(String date) {
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {}

        return null;
    }

    public static String calToString(Date date) {
        return textFormat.format(date);
    }

    public static Date stringToCal(String date) {
        try {
            return textFormat.parse(date);
        } catch (ParseException e) {}

        return null;
    }

    public static String bitmapToString(Context context, Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        bitmap.recycle();
        byte[] byteArray = stream.toByteArray();
        try{
            stream.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        stream.write(byteArray, 0,byteArray.length);
        stream = null;
        String strBase64 = Base64.encodeToString(byteArray, Base64.URL_SAFE);
        return strBase64;
    }

    public static Bitmap stringToBitmap(String bitmapString){
        byte[] bytes = Base64.decode(bitmapString, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0 , bytes.length);
        return bitmap;
    }


}
