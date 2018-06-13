package org.androidtown.sina_ver01.test;

import android.graphics.Color;
import junit.framework.TestCase;
import org.androidtown.sina_ver01.Font;

public class FontTest extends TestCase {

    Font test=new Font();

    public void testColor(){
        test.setColor(Color.BLACK);
        assertEquals(Color.BLACK,test.getColor());
    }
    public void testColor2(){
        test.setColor(Color.RED);
        assertEquals(Color.RED,test.getColor());
    }
}
