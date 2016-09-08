package com.ihandy.a2014011384;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class Main4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        ListView view = (ListView) findViewById(R.id.listv);
        OtherListAdapter.context = getBaseContext();
        OtherListAdapter.category = SQLHelper.readCategory();
        final OtherListAdapter adapter = new OtherListAdapter();
        view.setAdapter(adapter);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Click Item",position+"");
                Category category = OtherListAdapter.category.get(position);
                category.prefer = 3 - category.prefer;
                SQLHelper.saveCategory(category);
                OtherListAdapter.category = SQLHelper.readCategory();
                adapter.notifyDataSetChanged();
                InfStorage.category = SQLHelper.readPreferCategory();
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(getBaseContext(),MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}
