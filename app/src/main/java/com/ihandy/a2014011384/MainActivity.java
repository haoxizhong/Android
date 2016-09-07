package com.ihandy.a2014011384;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
{
    static String res = "";

    String lock = "";

    private int now = 0, need = 0;

    void setupTab()
    {
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CategoryFragmentPagerAdapter(getSupportFragmentManager(),MainActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    protected void createCategory() {
        Map<String, String> map = new HashMap();
        map.put("timestamp", System.currentTimeMillis() + "");

        OkHttpUtil.newCall("http://assignment.crazz.cn/news/en/category", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("No Network");
                List<Category> res = SQLHelper.readPreferCategory();
                CategoryFragmentPagerAdapter.setCategory(res);

                MainRunner.run(getMainLooper(), new Runnable() {
                    @Override
                    public void run() {
                        setupTab();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                Log.d("Log",str);
                List<Category> res  = NewsGetter.getCategory(str);

                SQLHelper.saveCategory(res);
                res = SQLHelper.readPreferCategory();
                CategoryFragmentPagerAdapter.setCategory(res);

                MainRunner.run(getMainLooper(), new Runnable() {
                    @Override
                    public void run() {
                        setupTab();
                    }
                });
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        ListAdapter.context = this;
        SQLHelper.onCreate(this);

        createCategory();
    }

    private static double prex = 0, prey = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event){

        int action = MotionEventCompat.getActionMasked(event);
        switch (action)
        {
            case (MotionEvent.ACTION_DOWN) :
            {
                prex = event.getX();
                prey = event.getY();
                return true;
            }
            case (MotionEvent.ACTION_UP) :
            {
                onSwipe(prex,prey,event.getX(),event.getY());
                return true;
            }
        }

        return super.onTouchEvent(event);
    }

    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;

    private void onSwipe(double x1,double y1,double x2,double y2)
    {
        boolean result = false;
        try
        {
            double diffY = y2-y1;
            double diffX = x2-x1;
            System.out.println(diffX);
            System.out.println(diffY);
            if (Math.abs(diffX) > Math.abs(diffY))
            {
                if (Math.abs(diffX) > SWIPE_THRESHOLD)
                {
                    if (diffX > 0)
                    {
                        onSwipeRight();
                    } else
                    {
                        onSwipeLeft();
                    }
                }
                result = true;
            }
            else if (Math.abs(diffY) > SWIPE_THRESHOLD )
            {
            }
            result = true;

        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    private void onSwipeLeft()
    {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        int nowid = viewPager.getCurrentItem();
        if (nowid != 0) viewPager.setCurrentItem(nowid-1,true);
    }
    private void onSwipeRight()
    {
        int size = CategoryFragmentPagerAdapter.getSize();
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        int nowid = viewPager.getCurrentItem();
        if (nowid != size) viewPager.setCurrentItem(nowid+1,true);
    }
}
