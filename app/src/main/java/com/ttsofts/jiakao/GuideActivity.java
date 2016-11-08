package com.ttsofts.jiakao;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by chenjie on 16/10/12.
 */
public class GuideActivity extends Activity implements ViewPager.OnPageChangeListener {

    private ViewPager vp;
    private ViewPagerAdapter vpAdapter;
    private List<View> views;
    // 底部小点图片
    private ImageView[] dots;
    // 记录当前选中位置
    private int currentIndex;

    TextView guid_entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.guide);
        guid_entry = (TextView)findViewById(R.id.guid_entry);
        guid_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vpAdapter.goIndex();
            }
        });

        // 初始化页面
        initViews();
        // 初始化底部小点
        initDots();
        spark();
        //字体闪烁
    }
    int col = 0;
    Handler handler = new Handler();
    // 构建Runnable对象，在runnable中更新界面
    Runnable   runnableUi=new  Runnable(){
        @Override
        public void run() {
            //更新界面
            if(col==0){
                col = 1;
                guid_entry.setTextSize(24);
            }
            if(col==1){
                col = 2;
                guid_entry.setTextSize(29);
            }
            if(col==2){
                col = 3;
                guid_entry.setTextSize(34);
            }
            if(col==3){
                col = 0;
                guid_entry.setTextSize(39);
            }
        }

    };
    public void spark(){

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {



                new Thread(){
                    @Override
                    public void run() {
                        handler.post(runnableUi);
                    }
                }.start();

                /*
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if(col==0){
                            col = 1;
                            guid_entry.setTextColor(Color.BLUE);
                        }
                        if(col==1){
                            col = 2;
                            //guid_entry.setTextSize(29);
                        }
                        if(col==2){
                            col = 3;
                            //guid_entry.setTextSize(34);
                        }
                        if(col==3){
                            col = 0;
                            //guid_entry.setTextSize(39);
                        }
                    }
                }).start();
                */

            }
        };
        timer.schedule(timerTask,1,300);
    }

    private void initViews() {
        LayoutInflater inflater = LayoutInflater.from(this);
        views = new ArrayList<View>();
        // 初始化引导图片列表
        views.add(inflater.inflate(R.layout.help_company, null));
        views.add(inflater.inflate(R.layout.help_voice, null));
        views.add(inflater.inflate(R.layout.help_start,null));
        // 初始化Adapter
        vpAdapter = new ViewPagerAdapter(views, this);
        vp = (ViewPager) findViewById(R.id.viewpager);
        vp.setAdapter(vpAdapter);
        vpAdapter.setNoGuide();
        // 绑定回调
        vp.setOnPageChangeListener(this);
    }

    private void initDots() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);

        dots = new ImageView[views.size()];

        // 循环取得小点图片
        for (int i = 0; i < views.size(); i++) {
            dots[i] = (ImageView) ll.getChildAt(i);
            dots[i].setEnabled(true);// 都设为灰色
        }

        currentIndex = 0;
        dots[currentIndex].setEnabled(false);// 设置为白色，即选中状态
    }

    private void setCurrentDot(int position) {
        if (position < 0 || position > views.size() - 1
                || currentIndex == position) {
            return;
        }

        dots[position].setEnabled(false);
        dots[currentIndex].setEnabled(true);

        currentIndex = position;
    }

    //当滑动状态改变时调用
    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    //当当前页面被滑动时调用
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    //当新的页面被选中时调用
    @Override
    public void onPageSelected(int arg0) {
        // 设置底部小点选中状态
        setCurrentDot(arg0);
    }
}
