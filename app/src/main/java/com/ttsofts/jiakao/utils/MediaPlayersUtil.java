package com.ttsofts.jiakao.utils;


import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;


import java.io.FileDescriptor;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenjie on 16/10/7.
 */
public class MediaPlayersUtil {


    public static Map<String,String> videoMap = new HashMap<>();
    static MediaPlayer mediaPlayer = null;
    static {
        videoMap.put("灯光1","light1.mp3");
        videoMap.put("灯光2","light2.mp3");
        videoMap.put("灯光3","light3.mp3");
        videoMap.put("灯光4","light4.mp3");
        videoMap.put("灯光5","light5.mp3");
        videoMap.put("灯光6","light6.mp3");

        videoMap.put("变更车道","road7.mp3");
        videoMap.put("路口左转","road8.mp3");
        videoMap.put("路口右转","road9.mp3");
        videoMap.put("前方掉头","road10.mp3");
        videoMap.put("减速慢行","road11.mp3");
        videoMap.put("通过学校","road12.mp3");
        videoMap.put("公共车站","road13.mp3");
        videoMap.put("路口直行","road14.mp3");
        videoMap.put("直线行驶","road15.mp3");
        videoMap.put("结束直行","road16.mp3");
        videoMap.put("靠边停车","road17.mp3");

    }

    public static void play(AssetFileDescriptor afd){
        try {
            if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
                mediaPlayer.stop();
            }
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setLooping(false);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.stop();
                }
            });
            mediaPlayer.prepare();
            mediaPlayer.start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
