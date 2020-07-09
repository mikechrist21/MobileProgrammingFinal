package com.example.finalexam;


import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchNewsFragment extends Fragment {

    ArrayList<News> listNews;
    NewsAdapter newsAdapter;
    RecyclerView rvSearchNews;
    RequestQueue queue;
    EditText etSearchBar;
    String keyWords;

    public static final String BASE_URL = "http://newsapi.org/v2/everything";
    public static final String API_KEY = "675e7fd00b534e1a9a831adffc7f01e7";

    public SearchNewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_news, container, false);

        listNews = new ArrayList<>();
        queue = Volley.newRequestQueue(getContext());
        queue.add(getNews("World"));
        // Inflate the layout for this fragment
        newsAdapter = new NewsAdapter(getContext(), listNews);
        rvSearchNews = view.findViewById(R.id.rvSearchNews);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvSearchNews.setLayoutManager(linearLayoutManager);
        rvSearchNews.setAdapter(newsAdapter);


        etSearchBar = view.findViewById(R.id.etSearchBar);
        etSearchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_GO){
                    keyWords = etSearchBar.getText().toString();
                    queue.add(getNews(keyWords));
                }
                return false;
            }
        });
        return view;
    }

    JsonObjectRequest getNews(String term){
        Uri uri = Uri.parse(BASE_URL).buildUpon().appendQueryParameter("q",term).appendQueryParameter("apiKey",API_KEY).build();
        String url = uri.toString();

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET ,url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray arrResults = response.getJSONArray("articles");
                    listNews.clear();
                    for (int i = 0; i < arrResults.length(); i++) {
                        JSONObject objResult = arrResults.getJSONObject(i);
                        News news = new News();
                        news.newsTitle = objResult.getString("title");
                        news.newsDescription = objResult.getString("description");
                        news.newsContent = objResult.getString("content");
                        news.newsURL = objResult.getString("url");
                        news.thumbnail = objResult.getString("urlToImage");
                        listNews.add(news);
                    }
                    newsAdapter.setListNews(listNews);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("MainActivity", error.getLocalizedMessage());
            }
        });
        return req;
    }

}
