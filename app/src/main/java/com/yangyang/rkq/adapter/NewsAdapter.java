package com.yangyang.rkq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yangyang.rkq.Body.NewsBean;
import com.yangyang.rkq.Frament.Fragment_circle;
import com.yangyang.rkq.R;
import com.yangyang.rkq.Utils.ImageHelper;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context mContext;
    private List<NewsBean> mList =new ArrayList<NewsBean>();

    public NewsAdapter(Context mContext, List<NewsBean> mList) {
        this.mContext = mContext;
        this.mList = mList;

    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_circle_item, parent, false);
        return new NewsAdapter.DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DataViewHolder dataViewHolder= (DataViewHolder) holder;
        dataViewHolder.data.setText(mList.get(position).getNewsDate());
        dataViewHolder.createTime.setText(mList.get(position).getNewsTime());
        Glide.with(mContext)
                .load(mList.get(position).getNewsImgUrl())
                .placeholder(R.drawable.ic_loading)
                .error(R.drawable.ic_failed_to_load)
                .into(dataViewHolder.imageView);
        dataViewHolder.title.setText(mList.get(position).getNewsTitle());
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView data;
        private TextView createTime;
        private ImageView imageView;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            data = itemView.findViewById(R.id.data);
            createTime = itemView.findViewById(R.id.time);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
