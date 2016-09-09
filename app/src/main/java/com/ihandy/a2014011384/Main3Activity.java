package com.ihandy.a2014011384;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

/**
 * The third activity
 */
public class Main3Activity extends AppCompatActivity {
    List<News> prefer = SQLHelper.readPreferNews();
    public static boolean on3 = false;
    public static AnotherListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        ListView view = (ListView) findViewById(R.id.listviewview);
        AnotherListAdapter.context = getBaseContext();
        AnotherListAdapter.prefer = prefer;
        adapter = new AnotherListAdapter();
        view.setAdapter(adapter);
        on3 = true;
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Click Item",position+"");
                Main2Activity.news = prefer.get(position);
                Intent intent = new Intent(getBaseContext(),Main2Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        on3 = false;
        super.onBackPressed();
    }
}
