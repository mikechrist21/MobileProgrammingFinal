package com.example.finalexam;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    Context ctx;
    ArrayList<News>listNews;
    NewsDB newsDB;
    NewsAdapterDelete del;

    public NewsAdapter(Context ctx, ArrayList<News>listNews){
        this.ctx = ctx;
        this.listNews = listNews;
        newsDB = new NewsDB(ctx);
        del = new NewsAdapterDelete(ctx,newsDB.getAllData());
    }

    public void setListNews(ArrayList<News>listNews){
        this.listNews = listNews;
        notifyDataSetChanged();
    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.news_item_row,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {
        News news = listNews.get(position);
        Glide.with(ctx).load(news.thumbnail).into(holder.ivThumbnail);
        holder.tvTitle.setText(news.newsTitle);
        holder.tvDescription.setText(Html.fromHtml(news.newsDescription));
        holder.itemView.setLongClickable(true);
    }

    @Override
    public int getItemCount() {
        int size = 0;
        try{
            size = listNews.size();
        }catch (Exception e){
            size = 0;
        }
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        ImageView ivThumbnail;
        TextView tvTitle;
        TextView tvDescription;
        Button btnSave;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivThumbnail = itemView.findViewById(R.id.ivThumbnail);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            btnSave = itemView.findViewById(R.id.btnSave);
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(btnSave.getVisibility() == View.VISIBLE){
                        btnSave.setVisibility(View.INVISIBLE);
                        int position = getAdapterPosition();
                        News news = listNews.get(position);
                        newsDB.insertNews(news);
                        Toast.makeText(ctx, "Insert News", Toast.LENGTH_SHORT).show();
                        del.setListNews(newsDB.getAllData());
                    }
                }
            });
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            News news = listNews.get(position);
            Intent intent = new Intent(ctx,NewsDetailActivity.class);
            intent.putExtra("newsTitle",news.newsTitle);
            intent.putExtra("thumbnail",news.thumbnail);
            intent.putExtra("newsContent",news.newsContent);
            intent.putExtra("newsDescription",news.newsDescription);
            intent.putExtra("newsURL",news.newsURL);
            ctx.startActivity(intent);

        }

        @Override
        public boolean onLongClick(View v) {
            int position = getAdapterPosition();
            final News news = listNews.get(position);
            if(btnSave.getVisibility() == View.VISIBLE){
                btnSave.setVisibility(View.INVISIBLE);
            }
            else{
                btnSave.setVisibility(View.VISIBLE);
            }

            return true;
        }

    }
}
