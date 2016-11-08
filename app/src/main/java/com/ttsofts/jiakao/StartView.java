package com.ttsofts.jiakao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * Created by chenjie on 16/10/14.
 */
public class StartView extends RelativeLayout {

    Button startBtn;
    public StartView(Context context) {
        super(context);
    }
    public StartView(Context context,final ViewPagerAdapter viewPagerAdapter) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.help_start, this);
        startBtn = (Button) findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPagerAdapter.goIndex();
            }
        });
    }
}
