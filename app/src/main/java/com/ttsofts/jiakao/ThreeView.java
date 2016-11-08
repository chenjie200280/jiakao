package com.ttsofts.jiakao;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.qq.e.ads.appwall.APPWall;
import com.ttsofts.jiakao.utils.ConstantsAdvert;

/**
 * Created by chenjie on 16/10/7.
 */
public class ThreeView  extends TableLayout {


    RelativeLayout setting_app;

    TextView version_text;

    public ThreeView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_three, this);

        setting_app = (RelativeLayout)findViewById(R.id.setting_app);

        version_text = (TextView)findViewById(R.id.version_text);

        final APPWall wall = new APPWall(this.getContext(),ConstantsAdvert.APPID,ConstantsAdvert.APPWallPosID);

        version_text.setText("当前软件版本："+getVersion());

        setting_app.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                wall.doShowAppWall();
            }
        });
    }

    /**
     * 获取版本号
     * @return
     */
    public String getVersion(){
        try{
            PackageManager manager = this.getContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getContext().getPackageName(),0);
            String version = info.versionName;
            return version;
        }catch (Exception e) {
            return "1.0";
        }

    }
}
