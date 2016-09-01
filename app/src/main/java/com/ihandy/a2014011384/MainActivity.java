package com.ihandy.a2014011384;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    static String res = "";

    protected  void createCategory()
    {
        Map<String,String> map = new HashMap();
        map.put("timestamp",System.currentTimeMillis()+"");
        OkHttpUtil.newCall("http://assignment.crazz.cn/news/en/category", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("No Network");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                MainRunner.run(getMainLooper(), new Runnable() {
                    @Override
                    public void run() {
                        SampleFragmentPagerAdapter.setCategory(NewsGetter.getCategory(str));
                        // Get the ViewPager and set it's PagerAdapter so that it can display items
                        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
                        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(),
                                MainActivity.this));

                        // Give the TabLayout the ViewPager
                        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
                        tabLayout.setupWithViewPager(viewPager);
                    }
                });
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createCategory();
    }

    /*

    public void onButton1Click(View view) throws InterruptedException
    {
    }*/
}
