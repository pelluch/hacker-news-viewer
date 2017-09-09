package com.pelluch.hackernewsviewer;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pelluch.hackernewsviewer.adapters.ArticleAdapter;
import com.pelluch.hackernewsviewer.http.RestAdapter;
import com.pelluch.hackernewsviewer.models.Article;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ArticleAdapter adapter;
    private RecyclerView articleRecycler;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        articleRecycler = view.findViewById(R.id.article_recycler);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ArticleAdapter();
        articleRecycler.setAdapter(adapter);
        articleRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        articleRecycler.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext())
            .build());
        RestAdapter.getArticlesEndpoint()
                .getArticles()
                .enqueue(new Callback<List<Article>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Article>> call,
                                           @NonNull Response<List<Article>> response) {
                        Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT)
                                .show();
                        if(response.isSuccessful()) {
                            adapter.setArticles(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Article>> call,
                                          @NonNull Throwable t) {
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT)
                                .show();

                    }
                });
    }
}
