package com.ihandy.a2014011384;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by lenovo on 2016/9/1.
 */
public class PageFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    public Category category;

    public int myid;

    private int mPage;

    ListAdapter adapter;

    public static PageFragment newInstance(int page ,Category cat) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        fragment.category = cat;
        fragment.myid = page - 1;
        return fragment;
    }

    protected void createNewsByCategory(final Category category)
    {
        Map<String,String> map = new HashMap<>();
        map.put("locale","en");
        map.put("category",category.name);
        OkHttpUtil.newCall("http://assignment.crazz.cn/news/query", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("No Network");

                List<News> arr = SQLHelper.readNews(category);

                InfStorage.news.put(category, SQLHelper.readNews(category));

                MainRunner.run(getContext().getMainLooper(), new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                int code = response.code();

                if (code == 200) SQLHelper.saveNews(NewsGetter.getNewsByCategory(str));
                else Log.d("Log",response.code()+"");

                List<News> arr = SQLHelper.readNews(category);

                InfStorage.news.put(category,SQLHelper.readNews(category));

                MainRunner.run(getContext().getMainLooper(), new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);

        ListView listview = (ListView) view.findViewById(R.id.listView);
        adapter = new ListAdapter();
        adapter.category = InfStorage.category.get(myid);
        adapter.context = getContext();
        listview.setAdapter(adapter);
        createNewsByCategory(category);
        return view;
    }
}