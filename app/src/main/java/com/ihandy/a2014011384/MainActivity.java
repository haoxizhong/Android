package com.ihandy.a2014011384;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    static String res = "";

    static CategoryFragmentPagerAdapter catadapter;

    String lock = "";

    private int now = 0, need = 0;

    DrawerLayout drawer;
    ListView drawerlist;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bar_action, menu);
        return true;
    }

    void setupTab() {
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        catadapter = new CategoryFragmentPagerAdapter(getSupportFragmentManager(), MainActivity.this);
        viewPager.setAdapter(catadapter);

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
                //Log.d("Log",str);
                List<Category> res = NewsGetter.getCategory(str);

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

    private void bilibili() {
        final ImageView view = new ImageView(this.getBaseContext());

        Picasso.with(this.getBaseContext()).load("http://static.hdslb.com/error/404.png").into(view, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                Bitmap map = ((BitmapDrawable) view.getDrawable()).getBitmap();
                SQLHelper.saveImage(new ImgHelper("http://static.hdslb.com/error/404.png", map));
            }

            @Override
            public void onError() {
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ListAdapter.context = this;
        SQLHelper.onCreate(this);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.setBackgroundColor(Color.WHITE);
        drawerlist = (ListView) findViewById(R.id.left_drawer);
        drawerlist.setBackgroundColor(Color.WHITE);
        drawerlist.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = null;
                if (position!=0) {

                    view= LayoutInflater.from(getBaseContext()).inflate(R.layout.drawer_layout, parent, false);
                    TextView textView = (TextView) view.findViewById(R.id.ttii);
                    textView.setText("Zhong Haoxi\n2014011384\nzhonghaoxi@yeah.net\n18813119711");
                    return textView;
                }
                else
                {
                    view = LayoutInflater.from(getBaseContext()).inflate(R.layout.img_layout, parent, false);
                }
                return view;
            }
        });

        createCategory();

        /*ListAdapter.context = this;
        SQLHelper.onCreate(this);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.setBackgroundColor(Color.WHITE);
        drawerlist = (ListView) findViewById(R.id.left_drawer);
        drawerlist.setBackgroundColor(Color.WHITE);
        drawerlist.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = null;
                if (position!=0) {

                    view= LayoutInflater.from(getBaseContext()).inflate(R.layout.drawer_layout, parent, false);
                    TextView textView = (TextView) view.findViewById(R.id.ttii);
                    textView.setText("Zhong Haoxi\n2014011384\nzhonghaoxi@yeah.net\n18813119711");
                    return textView;
                }
                else
                {
                    view = LayoutInflater.from(getBaseContext()).inflate(R.layout.img_layout, parent, false);
                }
                return view;
            }
        });

        createCategory();*/
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private static double prex = 0, prey = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case (MotionEvent.ACTION_DOWN): {
                prex = event.getX();
                prey = event.getY();
                return true;
            }
            case (MotionEvent.ACTION_UP): {
                onSwipe(prex, prey, event.getX(), event.getY());
                return true;
            }
        }

        return super.onTouchEvent(event);
    }

    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;

    private void onSwipe(double x1, double y1, double x2, double y2) {
        boolean result = false;
        try {
            double diffY = y2 - y1;
            double diffX = x2 - x1;
            System.out.println(diffX);
            System.out.println(diffY);
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD) {
                    if (diffX > 0) {
                        onSwipeRight();
                    } else {
                        onSwipeLeft();
                    }
                }
                result = true;
            } else if (Math.abs(diffY) > SWIPE_THRESHOLD) {
            }
            result = true;

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void onSwipeLeft() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        int nowid = viewPager.getCurrentItem();
        if (nowid != 0) viewPager.setCurrentItem(nowid - 1, true);
    }

    private void onSwipeRight() {
        int size = CategoryFragmentPagerAdapter.getSize();
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        int nowid = viewPager.getCurrentItem();
        if (nowid != size) viewPager.setCurrentItem(nowid + 1, true);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.ihandy.a2014011384/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.ihandy.a2014011384/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_fav:
                intent = new Intent(getBaseContext(),Main3Activity.class);
                startActivity(intent);
                return true;
            case R.id.action_cat:
                intent = new Intent(getBaseContext(),Main4Activity.class);
                startActivity(intent);
                this.finish();
                return true;

            //case R.id.action_pengyouquan:
            // User chose the "Favorite" action, mark the current item
            // as a favorite...
            //    Share(1);
            //    return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
