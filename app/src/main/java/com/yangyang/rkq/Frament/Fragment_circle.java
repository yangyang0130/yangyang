package com.yangyang.rkq.Frament;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.yangyang.rkq.Body.NewsBean;
import com.yangyang.rkq.R;
import com.yangyang.rkq.adapter.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

public class Fragment_circle extends Fragment {
    private View mView;
    private RecyclerView mRecyclerView;
    /**
     * 新闻列表请求接口
     */
    public static final String URL = "http://v.juhe.cn/toutiao/index?type=&page=&page_size=&key=3d055ce1931a8aab2c9dd83fda5635f5";
    /**
     * 新闻集合对象
     */
    private List<NewsBean> datas;
    /**
     * 自定义的Adapter对象
     */
    private NewsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_circle, container, false);
        initView();
        return mView;
    }

    private void initView() {
        mRecyclerView = mView.findViewById(R.id.recyclerView);
        datas = new ArrayList<NewsBean>();


    }
    /**
     * 通过接口获取新闻列表的方法
     * @param url
     */


}
