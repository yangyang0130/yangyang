package com.yangyang.rkq.Utils;
//创建访问网络异步类。
import com.yangyang.rkq.Body.NewsBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
//在创建访问网络异步类前，首先自己定义一个解析网络返回的JSON数据的类。
public class JSONUtils {
    public static ArrayList<NewsBean> parseJson(String jsonData) {
        ArrayList<NewsBean> result = new ArrayList<>();
        JSONObject jo = null;
        NewsBean news;
        try {
            jo = new JSONObject(jsonData);
            if (jo.getString("reason").equals("成功的返回")) {
                JSONObject jo1 = jo.getJSONObject("result");
                JSONArray ja = jo1.getJSONArray("data");
                for (int i = 0; i < ja.length(); i++) {
                    news = new NewsBean();
                    JSONObject obj = ja.getJSONObject(i);
                    news.setTitle(obj.getString("title"));
                    news.setDate(obj.getString("date"));
                    news.setAuthor_name(obj.getString("author_name"));
                    news.setThumbnail_pic_s(obj.getString("thumbnail_pic_s"));
                    news.setUrl(obj.getString("url"));
                    result.add(news);
                }
            }
            return  result;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
