package com.yangyang.rkq.Frament;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.header.BezierRadarHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.yangyang.rkq.Body.NewsBean;
import com.yangyang.rkq.R;
import com.yangyang.rkq.activity.NewInfoAty;
import com.yangyang.rkq.adapter.NewsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Fragment_circle extends Fragment {

    private final String TAG = this.getClass().getSimpleName();

    private View mView;
    private RecyclerView mRecyclerView;
    /**
     * 新闻列表请求接口
     */
    public static final String url = "https://v.juhe.cn/toutiao/index";
    /**
     * 新闻集合对象
     */
    private List<NewsBean> dataList = new ArrayList<>();
    /**
     * 自定义的Adapter对象
     */
    private NewsAdapter adapter;
    private SmartRefreshLayout refreshLayout;

    private int currentPageSize = 1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_circle, container, false);
        initView();
        intData();
        return mView;
    }

    private void initView() {
        mRecyclerView = mView.findViewById(R.id.recyclerView);
        refreshLayout = mView.findViewById(R.id.srl_refresh_page);
        dataList = new ArrayList<NewsBean>();
        getDatas(url, "", 1);
        adapter = new NewsAdapter(getActivity(), dataList);
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        //refreshLayout.setHeader
        refreshLayout.setRefreshHeader(new BezierRadarHeader(getContext()).setEnableHorizontalDrag(true));
        refreshLayout.setRefreshFooter(new BallPulseFooter(getContext()).setSpinnerStyle(SpinnerStyle.Scale));
    }

    private void intData() {
        adapter.setListener(new NewsAdapter.Listener() {
            @Override
            public void itemOnClick(int position) {
                Intent intent = new Intent(getActivity(), NewInfoAty.class);

                /**
                 * 在datas中通过点击的位置position通过get()方法获得具体某个新闻
                 * 的数据然后通过Intent的putExtra()传递到NewsInfoActivity中
                 */
                intent.putExtra("newsUrl", dataList.get(position).getNewsUrl());
                startActivity(intent);//启动Activity
            }
        });


        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                // 加载更多
                currentPageSize++;
                getDatas(url, "", currentPageSize);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                // 刷新
                currentPageSize = 1;
                getDatas(url, "", currentPageSize);
            }
        });

    }

    /**
     * 通过接口获取新闻列表的方法
     *
     * @param url
     */
    public void getDatas(String url, String type, int page) {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.finishRefresh();
            dataList.clear();
        }
        if (refreshLayout.isLoading()) {
            refreshLayout.finishLoadMore();
        }
        final RequestQueue mQueue = Volley.newRequestQueue(getContext());

        JsonObjectRequest stringRequest = new JsonObjectRequest(
                url + "?key=" +"3d055ce1931a8aab2c9dd83fda5635f5" + "&page_size=10&page=" + page
                , null,
                jsonObject -> {
            Log.d("TAG", String.valueOf(jsonObject));
            try {
                // 对返回的json数据进行解析,然后装入datas集合中
                JSONObject resultObj = jsonObject.getJSONObject("result");
                JSONArray dataArray = resultObj.getJSONArray("data");
                int arraySize = dataArray.length();
                for (int i = 0; i < arraySize; i++) {
                    JSONObject item = dataArray.getJSONObject(i);
                    NewsBean data = new NewsBean();
                    data.setNewsTitle(item.getString("title"));
                    data.setNewsImgUrl(item.getString("thumbnail_pic_s"));
                    data.setNewsUrl(item.getString("url"));
                    data.setNewsTime(item.getString("date"));
                    data.setNewsDate(item.getString("author_name"));
                    dataList.add(data);
                }
                Log.d("EasyAndroid", "----->>> " + dataList.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapter.setList(dataList);
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }
        );
        mQueue.add(stringRequest);
    }

}



