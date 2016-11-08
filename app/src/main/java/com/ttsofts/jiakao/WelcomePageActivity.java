package com.ttsofts.jiakao;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import com.ttsofts.jiakao.utils.Constant;

/**
 * Created by chenjie on 16/10/12.
 */
public class WelcomePageActivity extends Activity {

    private static final int LOWBRIGHTNESS = -230;
    private static final String SHAREDPREFERENCES_NAME = "first_pref";
    private static final double everycut = 5;
    private boolean isFirstIn = false;

    ImageView main_image;
    Handler mHandler;
    int brightness;
    Bitmap bmp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.welcome);
        //回收图片资源
        if(bmp!=null&&!bmp.isRecycled()){
            bmp.recycle();
        }
        bmp = BitmapFactory.decodeResource(this.getBaseContext().getResources(),R.mipmap.main);
        main_image = (ImageView)findViewById(R.id.welcome_main_image);
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case Constant.DECREASE:
                        brightness -= everycut * 1.05;
                        //brightChanged(brightness, bmp, main_image);
                        break;
                    case Constant.GO_GUIDE:
                        goGuide();
                        break;
                    case Constant.GO_INDEX:
                        goIndex();
                        break;
                }
                super.handleMessage(msg);
            }
        };
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    mHandler.sendEmptyMessage(Constant.DECREASE);
                    if (brightness < LOWBRIGHTNESS) {
                        init();
                        break;
                    }
                    try {
                        Thread.sleep(30);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private void init() {

        SharedPreferences preferences = getSharedPreferences(
                SHAREDPREFERENCES_NAME, MODE_PRIVATE);
        isFirstIn = preferences.getBoolean("isFirstIn", true);
        if (!isFirstIn) {
            mHandler.sendEmptyMessageDelayed(Constant.GO_INDEX, Constant.KEEP_INDEX_TIME);
        } else {
            mHandler.sendEmptyMessageDelayed(Constant.GO_GUIDE, Constant.KEEP_INDEX_TIME);
        }

    }

    private void goIndex() {
        Intent intent = new Intent(WelcomePageActivity.this, MainActivity.class);
        WelcomePageActivity.this.startActivity(intent);
        WelcomePageActivity.this.finish();
    }

    private void goGuide() {
        Intent intent = new Intent(WelcomePageActivity.this, GuideActivity.class);
        WelcomePageActivity.this.startActivity(intent);
        WelcomePageActivity.this.finish();
    }

    public void brightChanged(int brightness, Bitmap bitmap,
                              ImageView iv) {
        int imgHeight = bitmap.getHeight();
        int imgWidth = bitmap.getWidth();
        Bitmap bmp = Bitmap.createBitmap(imgWidth, imgHeight, Bitmap.Config.ARGB_8888);
        ColorMatrix cMatrix = new ColorMatrix();
        cMatrix.set(
                new float[] {
                        1, 0, 0, 0, brightness,
                        0, 1, 0, 0, brightness,
                        0, 0, 1, 0, brightness,
                        0, 0, 0, 1, 0
                });

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));

        Canvas canvas = new Canvas(bmp);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        iv.setImageBitmap(bmp);
    }
}
