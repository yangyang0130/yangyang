package com.yangyang.rkq.Utils;

import android.os.AsyncTask;

import com.yangyang.rkq.Body.NewsBean;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
//创建访问网络异步类。
public class NewsTask  extends AsyncTask<String,Void, ArrayList<NewsBean>> {
    private NewsCallBack  newsCallBack;
    public NewsTask(NewsCallBack newsCallBack) {
        this.newsCallBack = newsCallBack;
    }
    @Override
    protected ArrayList<NewsBean> doInBackground(String... strings) {
        //************************访问网络获取数据，得到列表项的数据*****************
        ArrayList<NewsBean> result=null;  //创建arraylist ,接收数据

        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(strings[0]).build();
        try {
            Response response=client.newCall(request).execute();
            String jsonData=response.body().string();
            result=  JSONUtils.parseJson(jsonData);   //调用,返回Arraylist

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<NewsBean> result) {
        //在主线程中执行
        if(newsCallBack!=null)
            newsCallBack.getResults(result);
        super.onPostExecute(result);
    }


    @Override
    protected void onPreExecute() {
        //在主线程执行
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        //做进度显示的操作
        super.onProgressUpdate(values);
    }
    //定义接口
    public interface NewsCallBack{
        void getResults(ArrayList<NewsBean> result);
    }
}