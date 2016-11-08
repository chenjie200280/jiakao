package com.ttsofts.jiakao;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;

import com.ttsofts.jiakao.utils.MediaPlayersUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by chenjie on 16/10/10.
 */
public class OneView extends LinearLayout {
    private GridView gview;
    private int[] icon = {R.mipmap.icon_light1,R.mipmap.icon_light2,R.mipmap.icon_light3,
            R.mipmap.icon_light4,R.mipmap.icon_light5,R.mipmap.icon_light6};
    private String[] iconName = {"灯光1","灯光2","灯光3","灯光4","灯光5","灯光6"};
    private List<Map<String,Object>> data_list;
    private SimpleAdapter simpleAdapter;
    public OneView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_one, this);
        gview = (GridView)findViewById(R.id.light_grid_view);
        data_list = new ArrayList<>();
        getData();
        String [] from = {"image","text"};
        int [] to = {R.id.light_image,R.id.light_text};
        simpleAdapter = new SimpleAdapter(this.getContext(),data_list,R.layout.light_item,from,to);
        gview.setAdapter(simpleAdapter);

        gview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    AssetFileDescriptor afd = getResources().getAssets().openFd("light"+(position+1)+".mp3");
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
