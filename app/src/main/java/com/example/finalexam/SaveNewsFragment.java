package com.example.finalexam;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SaveNewsFragment extends Fragment {
    NewsAdapterDelete newsAdapter;
    RecyclerView rvSaveNews;
    NewsDB newsDB;


    public SaveNewsFragment() {
        // Required empty public constructor


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            newsAdapter.setListNews(newsDB.getAllData());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_save_news, container, false);
        newsDB = new NewsDB(view.getContext());
        newsAdapter = new NewsAdapterDelete(getContext(), newsDB.getAllData());
        rvSaveNews = view.findViewById(R.id.rvSaveNews);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvSaveNews.setLayoutManager(linearLayoutManager);
        rvSaveNews.setAdapter(newsAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        newsAdapter.setListNews(newsDB.getAllData());
    }
}
