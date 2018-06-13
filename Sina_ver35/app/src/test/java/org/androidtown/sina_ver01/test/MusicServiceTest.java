package org.androidtown.sina_ver01.test;

import junit.framework.TestCase;

import org.androidtown.sina_ver01.MusicService;
import org.junit.Test;

public class MusicServiceTest extends TestCase{
    MusicService test=new MusicService();

    @Test
    public void testRandom(){
        assertEquals("https://firebasestorage.googleapis.com/v0/b/sinabro-1526195392107.appspot.com/o/music%2Fangry%2FSpring.mp3?alt=media&token=f39a778d-c3a3-4c9c-bcc2-cfb77da9a942",test.randomMusic(2));
    }

    @Test
    public void testHappy(){
        assertEquals("https://firebasestorage.googleapis.com/v0/b/sinabro-1526195392107.appspot.com/o/music%2Fhappy%2FBongo_Madness.mp3?alt=media&token=e64e7ba4-6ce0-48ae-918d-fa0b004c5c23",test.happyMusic(2));
    }

    @Test
    public void testSad(){
        assertEquals("https://firebasestorage.googleapis.com/v0/b/sinabro-1526195392107.appspot.com/o/music%2Fsad%2FCampfire_Song.mp3?alt=media&token=58a929c1-46dc-4812-9c97-a405b090747a",test.sadMusic(1));
    }

    @Test
    public void testAngry(){
        assertEquals("https://firebasestorage.googleapis.com/v0/b/sinabro-1526195392107.appspot.com/o/music%2Fangry%2FDarkness_of_My_Sun.mp3?alt=media&token=a3435ff4-47d2-492c-b529-1e2f6dd9afe0",test.angryMusic(1));
    }

}
