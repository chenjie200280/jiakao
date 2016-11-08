package com.ttsofts.jiakao;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.qq.e.ads.interstitial.AbstractInterstitialADListener;
import com.qq.e.ads.interstitial.InterstitialAD;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {


    private ViewPager viewPager;


    LinearLayout tab1;
    LinearLayout tab2;
    LinearLayout tab3;

    ImageButton img_tab1;
    ImageButton img_tab2;
    ImageButton img_tab3;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initAd();
        tab1 = (LinearLayout)findViewById(R.id.id_tab1);
        tab2 = (LinearLayout)findViewById(R.id.id_tab2);
        tab3 = (LinearLayout)findViewById(R.id.id_tab3);

        img_tab1 = (ImageButton)findViewById(R.id.id_tab1_img);
        img_tab2 = (ImageButton)findViewById(R.id.id_tab2_img);
        img_tab3 = (ImageButton)findViewById(R.id.id_tab3_img);


        viewPager = (ViewPager)findViewById(R.id.viewPager);
        LayoutInflater lf = getLayoutInflater().from(this);
        ArrayList<View> views = new ArrayList<>();
        View view1 = new OneView(this);
        View view2 = new TwoView(this);// lf.inflate(R.layout.view_two,null);
        View view3 = new ThreeView(this);
        views.add(view1);
        views.add(view2);
        views.add(view3);
        viewPager.setAdapter(new MyViewPageAdapter(views));
        viewPager.setCurrentItem(0);

        initEvents();
    }

    private void initEvents() {
        viewPager.setOnPageChangeListener(tabPageChanageListener);
        tab1.setOnClickListener(tabClickListener);
        tab2.setOnClickListener(tabClickListener);
        tab3.setOnClickListener(tabClickListener);
    }


    private void initAd(){
        initBannerAd();
        initInterstitialAD();

    }

    private void initBannerAd(){
        RelativeLayout l = (RelativeLayout)findViewById(R.id.bannerAd);
        BannerView banner = new BannerView(this, ADSize.BANNER, "1104704646", "1000408464691674");
        banner.setRefresh(30);
        banner.setADListener(new AbstractBannerADListener() {
            @Override
            public void onNoAD(int i) {
                Log.i("AD_DEMO", "BannerNoAD，eCode=" + i);
            }
            @Override
            public void onADReceiv() {
                Log.i("AD_DEMO", "ONBannerReceive");
            }
        });
        banner.loadAD();
        l.addView(banner);
    }


    private void initInterstitialAD(){
        final InterstitialAD iad = new InterstitialAD(this, "1104704646", "6020202404899655");
        iad.setADListener(new AbstractInterstitialADListener() {
            @Override
            public void onADReceive() {
                iad.show();
            }
            @Override
            public void onNoAD(int i) {}
        });
        iad.loadAD();
    }


    View.OnClickListener tabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.id_tab1:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.id_tab2:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.id_tab3:
                    viewPager.setCurrentItem(2);
                    break;
            }
        }
    };

    OnPageChangeListener tabPageChanageListener = new OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            int currentItem = viewPager.getCurrentItem();
            resetImg();
            switch (currentItem)
            {
                case 0:
                    img_tab1.setImageResource(R.mipmap.icon_light_selected);
                    break;
                case 1:
                    img_tab2.setImageResource(R.mipmap.icon_num_3_selected);
                    break;
                case 2:
                    img_tab3.setImageResource(R.mipmap.icon_setting_selected);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void resetImg(){
        img_tab1.setImageResource(R.mipmap.icon_light_gray);
        img_tab2.setImageResource(R.mipmap.icon_num_3_gray);
        img_tab3.setImageResource(R.mipmap.icon_setting_gray);
    }

    public class  MyViewPageAdapter extends PagerAdapter{
        private List<View> myListViews;

        public MyViewPageAdapter(List<View> myListViews) {
            this.myListViews = myListViews;
        }

        @Override
        public int getCount() {
           return myListViews.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(myListViews.get(position),0);
            return  myListViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(myListViews.get(position));
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return  view==object;
        }
    }



}
