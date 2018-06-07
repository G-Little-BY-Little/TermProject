package org.androidtown.sina_ver01;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import java.io.IOException;

public class MusicService extends Service {
    public MediaPlayer mp;
    public static String MESSEAGE_KEY;

    int notificationID=1;
    String music;
    public MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public int onStartCommand(Intent intent,int flags,int startId){
        boolean message=intent.getExtras().getBoolean(MusicService.MESSEAGE_KEY);
        int emotion=intent.getExtras().getInt("emo_num");

        DiaryData emo=new DiaryData();
        music=randomMusic(emotion);

        mp=new MediaPlayer();

        if(message){
            try {
                mp.setDataSource(music);
                mp.prepare();
                mp.start();
                mp.setLooping(true);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Intent mMainIntent=new Intent(this,SelectEmotionActivity.class);
            mMainIntent.putExtra("notificationID",notificationID);
            PendingIntent mPendingIntent=PendingIntent.getActivity(this,0,mMainIntent,PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Builder mBuilder=
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(android.R.drawable.btn_star)
                            .setContentTitle("시나브로 앱 음악")
                            .setContentIntent(mPendingIntent)
                            .setContentText("백그라운드에서 음악이 플레이되고 있어요");

            NotificationManager mNotifyMgr=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            mNotifyMgr.notify(notificationID,mBuilder.build());

        }else {
            mp.stop();
            mp.release();
        }
        return START_NOT_STICKY;
    }

    public String randomMusic(int emotion)
    {
        String emoString="";
        int random=(int)(Math.random()*4)+1;

        switch(emotion){
            case 0:
                emoString=happyMusic(random);
                break;
            case 1:
                emoString=sadMusic(random);
                break;
            case 2:
                emoString=angryMusic(random);
                break;
            default:
                break;
        }
        return emoString;
    }


    public String happyMusic(int random){
        String emoString="";

        if(random==1) {
            emoString="https://firebasestorage.googleapis.com/v0/b/sinabro-1526195392107.appspot.com/o/music%2Fhappy%2FBed_and_Breakfast.mp3?alt=media&token=c269cdc2-d26d-4e90-ac11-7fdf90f13204";
        } else if(random==2) {
            emoString = "https://firebasestorage.googleapis.com/v0/b/sinabro-1526195392107.appspot.com/o/music%2Fhappy%2FBongo_Madness.mp3?alt=media&token=e64e7ba4-6ce0-48ae-918d-fa0b004c5c23";
        } else if(random==3){
            emoString="https://firebasestorage.googleapis.com/v0/b/sinabro-1526195392107.appspot.com/o/music%2Fhappy%2FFond_Memories.mp3?alt=media&token=a5f07d41-7514-4558-9beb-8947025d7e48";
        }else if(random==4){
            emoString="https://firebasestorage.googleapis.com/v0/b/sinabro-1526195392107.appspot.com/o/music%2Fhappy%2FSpecial_Ed.mp3?alt=media&token=dfb046ee-4a1d-4024-aa94-96f4235532ce";
        }
        return emoString;
    }

    public String sadMusic(int random){
        String emoString="";

        if(random==1)
            emoString="https://firebasestorage.googleapis.com/v0/b/sinabro-1526195392107.appspot.com/o/music%2Fsad%2FCampfire_Song.mp3?alt=media&token=58a929c1-46dc-4812-9c97-a405b090747a";
        else if(random==2)
            emoString="https://firebasestorage.googleapis.com/v0/b/sinabro-1526195392107.appspot.com/o/music%2Fsad%2FClover_3.mp3?alt=media&token=d37a40bc-9bb8-461a-ab8b-6f0346474397";
        else if(random==3)
            emoString="https://firebasestorage.googleapis.com/v0/b/sinabro-1526195392107.appspot.com/o/music%2Fsad%2FNew_Year_s_Anthem.mp3?alt=media&token=c9304667-b11f-44c2-94d5-819eefba3505";
        else if(random==4)
            emoString="https://firebasestorage.googleapis.com/v0/b/sinabro-1526195392107.appspot.com/o/music%2Fsad%2FTake_Me_to_the_Depths.mp3?alt=media&token=71f2b233-97f3-4185-96c0-cc0b4549ea22";

        return emoString;
    }

    public String angryMusic(int random){
        String emoString="";

        if(random==1)
            emoString="https://firebasestorage.googleapis.com/v0/b/sinabro-1526195392107.appspot.com/o/music%2Fangry%2FDarkness_of_My_Sun.mp3?alt=media&token=a3435ff4-47d2-492c-b529-1e2f6dd9afe0";
        else if(random==2)
            emoString="https://firebasestorage.googleapis.com/v0/b/sinabro-1526195392107.appspot.com/o/music%2Fangry%2FGhost_Chase_Thriller.mp3?alt=media&token=3bbae31a-38e7-4c97-ae67-1e1d66816554";
        else if(random==3)
            emoString="https://firebasestorage.googleapis.com/v0/b/sinabro-1526195392107.appspot.com/o/music%2Fangry%2FSpring.mp3?alt=media&token=f39a778d-c3a3-4c9c-bcc2-cfb77da9a942";
        else if(random==4)
            emoString="https://firebasestorage.googleapis.com/v0/b/sinabro-1526195392107.appspot.com/o/music%2Fangry%2FYou_re_Not_Wrong.mp3?alt=media&token=c27933a8-9609-4dd3-a3b9-48047ffcfffa";
        return emoString;
    }

}
