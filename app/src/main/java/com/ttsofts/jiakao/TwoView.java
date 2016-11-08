package com.ttsofts.jiakao;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;

import com.ttsofts.jiakao.utils.MediaPlayersUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenjie on 16/10/7.
 */
public class TwoView extends LinearLayout {
    private GridView gview;
    private int[] icon = {R.mipmap.icon_road7,R.mipmap.icon_road8,R.mipmap.icon_road9,
            R.mipmap.icon_road10,R.mipmap.icon_road11,R.mipmap.icon_road12,R.mipmap.icon_road13,
            R.mipmap.icon_road14,R.mipmap.icon_road15,R.mipmap.icon_road16,R.mipmap.icon_road17};
    private String[] iconName = {"变更车道","路口左转","路口右转","前方掉头","减速慢行","通过学校",
            "公共车站","路口直行","直线行驶","结束直行","靠边停车"};
    private List<Map<String,Object>> data_list;
    private SimpleAdapter simpleAdapter;
    public TwoView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_two, this);
        gview = (GridView)findViewById(R.id.road_grid_view);
        data_list = new ArrayList<>();
        getData();
        String [] from = {"image","text"};
        int [] to = {R.id.light_image,R.id.light_text};
        simpleAdapter = new SimpleAdapter(this.getContext(),data_list,R.layout.road_item,from,to);
        gview.setAdapter(simpleAdapter);

        gview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    AssetFileDescriptor afd = getResources().getAssets().openFd("road"+(position+7)+".mp3");
                    MediaPlayersUtil.play(afd);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    public void getData(){
        for(int i=0;i<icon.length;i++){
            Map<String,Object> map = new HashMap<>();
            map.put("image",icon[i]);
            map.put("text",iconName[i]);
            data_list.add(map);
        }
    }

}
