package com.example.finalexam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class NewsDetailActivity extends AppCompatActivity {


    TextView tvNewsTitle;
    ImageView ivNewsImage;
    TextView tvNewsContent;
    Button btnSaveDelete;
    Button btnReadFullNews;
    String newsTitle;
    String newsThumbnail;
    String newsDescription;
    String newsContent;
    String newsURL;
    ArrayList<News> listNews;
    NewsDB newsDB = new NewsDB(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        // Declaration
        tvNewsTitle = findViewById(R.id.tvNewsTitle);
        ivNewsImage = findViewById(R.id.ivNewsImage);
        tvNewsContent = findViewById(R.id.tvNewsContent);
        btnSaveDelete = findViewById(R.id.btnSaveDelete);
        btnReadFullNews = findViewById(R.id.btnReadFullNews);

        Intent intent = getIntent();
        newsTitle = intent.getStringExtra("newsTitle");
        newsThumbnail = intent.getStringExtra("thumbnail");
        newsContent = intent.getStringExtra("newsContent");
        newsDescription = intent.getStringExtra("newsDescription");
        newsURL = intent.getStringExtra("newsURL");
        tvNewsTitle.setText(newsTitle);
        tvNewsContent.setText(Html.fromHtml(newsContent));
        Glide.with(this).load(newsThumbnail).into(ivNewsImage);
        btnReadFullNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(newsURL));
                startActivity(intent);
            }
        });

        btnSaveDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newsDB.isExist(newsTitle)){
                    newsDB.deleteNews(new News(newsTitle, newsDescription, newsContent, newsURL, newsThumbnail));
                    Toast.makeText(NewsDetailActivity.this, "Delete News", Toast.LENGTH_SHORT).show();

                }
                else{
                    newsDB.insertNews(new News(newsTitle, newsDescription, newsContent, newsURL, newsThumbnail));
                    Toast.makeText(NewsDetailActivity.this, "Insert News", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
