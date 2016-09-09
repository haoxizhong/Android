package com.ihandy.a2014011384;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

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

/**
 * PageFrament class
 */
public class PageFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    public Category category;

    public int myid;

    private int mPage;

    ListAdapter adapter;

    /**
     * Genereate new page
     * @param page the id
     * @param cat the category
     * @return New page instance
     */
    public static PageFragment newInstance(int page ,Category cat) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        fragment.category = cat;
        fragment.myid = page - 1;
        return fragment;
    }

    /**
     * Get news by category
     * @param category category
     */
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
                else Log.d("Error Code",response.code()+"");

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

    //PullToRefreshListView listview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("MJY","error");
        try{
        View view = inflater.inflate(R.layout.fragment_page, container, false);

       // listview = (PullToRefreshListView) view.findViewById(R.id.listView);
        ListView listview = (ListView) view.findViewById(R.id.listView);
        adapter = new ListAdapter();
        adapter.category = InfStorage.category.get(myid);
        adapter.context = getContext();
        listview.setAdapter(adapter);
        //    listview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Click Item",position+"");
                Main2Activity.news = InfStorage.news.get(category).get(position);
                Intent intent = new Intent(getContext(),Main2Activity.class);
                startActivity(intent);
            }
        });
        /*listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                refreshView.onRefreshComplete();
                listview.onRefreshComplete();
                listview.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
//                createNewsByCategory(category);
//                adapter.notifyDataSetChanged();
        });*/
        createNewsByCategory(category);
        return view;}
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(0);
            return new View(getContext());
        }
    }
}