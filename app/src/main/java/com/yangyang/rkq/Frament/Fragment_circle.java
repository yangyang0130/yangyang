package com.yangyang.rkq.Frament;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yangyang.rkq.Body.NewsBean;
import com.yangyang.rkq.R;
import com.yangyang.rkq.Utils.NewsTask;
import com.yangyang.rkq.activity.MainActivity;
import com.yangyang.rkq.adapter.NewsAdapter;

import java.io.InputStream;
import java.util.ArrayList;

public class Fragment_circle extends Fragment {
    private ListView listView;  //定义listview
    private ArrayList<NewsBean> data = new ArrayList<>();
    private NewsAdapter adapter;
    private boolean isLoading = true, isDown = false;
    private View mView;
    //**************************************
    private static String URL = "http://v.juhe.cn/toutiao/index?type=top&key=6928eda123d4aef596b726b4addadf48";
    private String citySubing;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_circle, container, false);
        listView = (ListView) mView.findViewById(R.id.list_view);  //绑定
        //**********************添加带有底部视图**************************************

        loadData(URL);   //将列表项的数据加载到数据源（顺序表）中
        adapter = new NewsAdapter(getContext(), R.layout.activity_circle_item, data);
        listView.setAdapter(adapter);

        //添加列表的滚动事件
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if (isDown == true && scrollState == SCROLL_STATE_IDLE) {
                    loadData(URL);
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                if (i + i1 == i2) {
                    isDown = true;
                } else {
                    isDown = false;
                }
            }
        });
        return mView;
    }

    private void loadData(String URL) {
        if (isLoading) {
            isLoading = false;
            new NewsTask(new NewsTask.NewsCallBack() {
                @Override
                public void getResults(ArrayList<NewsBean> result) {//重写接接口方法
                    Log.d("EasyAndroid", "----->" + result);
                    InputStream inputStream = getResources().openRawResource(R.raw.news);


                    if (result == null || result.size() == 0){
                        return;
                    }
                    data.clear();
                    data.addAll(result);
                    adapter.notifyDataSetChanged();
                }
            }).execute(URL);
            isLoading = true;
        }
    }
}