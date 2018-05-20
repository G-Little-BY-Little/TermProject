package org.androidtown.sina_ver01;

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
}
