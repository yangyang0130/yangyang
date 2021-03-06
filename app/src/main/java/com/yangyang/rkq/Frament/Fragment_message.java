package com.yangyang.rkq.Frament;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.yangyang.rkq.Body.ChatData;
import com.yangyang.rkq.R;
import com.yangyang.rkq.adapter.ChatListAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Fragment_message extends Fragment implements View.OnClickListener{
    private View mView;
    private ListView lv_chat_list;
    private EditText ed_send;
    private Button btn_send;
    private List<ChatData> mList = new ArrayList<>();
    private ChatListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_message, container, false);
        initView();
        return mView;
    }

    private void initView() {
        lv_chat_list = (ListView) mView.findViewById(R.id.lv_chat_list);
        ed_send = (EditText) mView.findViewById(R.id.ed_send);
        btn_send = (Button) mView.findViewById(R.id.btn_send);
        lv_chat_list.setDivider(null);

        //设置适配器
        adapter = new ChatListAdapter(getContext(), mList);
        lv_chat_list.setAdapter(adapter);

        //设置发送按钮监听
        btn_send.setOnClickListener(this);

        //设置欢迎语
        addlefttext("你好呀！");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                String message = ed_send.getText().toString().trim();
                if (!TextUtils.isEmpty(message)) {
                    //点击发送后清空输入框
                    ed_send.setText("");
                    addrighttext(message);
                    //定义URL
                    //图灵机器人接口地址：http://www.tuling123.com/openapi/api
                    //key=后接在图灵官网申请到的apikey
                    //info后接输入的内容
                    String url = "http://www.tuling123.com/openapi/api?" +
                            "key=" + "3e4f8d6a4a484a46b34330e8693f7f9b" + "&info=" + message;
                    //RxVolley将信息发出（添加RxVolley依赖，
                    // 在app的build.gradle的ependencies中添加compile 'com.kymjs.rxvolley:rxvolley:1.1.4'）
                    RxVolley.get(url, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            //解析返回的JSON数据
                            pasingJson(t);
                        }
                    });

                } else {
                    return;
                }
                break;
        }
    }

    private void pasingJson(String message) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(message);
            //通过key（text）获取value
            String text = jsonObject.getString("text");
            addlefttext(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //添加右侧消息
    private void addrighttext(String message) {
        ChatData data = new ChatData();
        data.setType(ChatListAdapter.chat_right);
        data.setText(message);
        mList.add(data);
        //通知adapter刷新页面
        adapter.notifyDataSetChanged();
        lv_chat_list.setSelection(lv_chat_list.getBottom());

    }

    //添加左侧消息
    private void addlefttext(String message) {
        ChatData data = new ChatData();
        data.setType(ChatListAdapter.chat_left);
        data.setText(message);
        mList.add(data);
        adapter.notifyDataSetChanged();
        lv_chat_list.setSelection(lv_chat_list.getBottom());

    }
}

