package com.pelluch.hackernewsviewer;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.pelluch.hackernewsviewer.adapters.ArticleAdapter;
import com.pelluch.hackernewsviewer.http.RestAdapter;
import com.pelluch.hackernewsviewer.models.Article;
import com.pelluch.hackernewsviewer.models.ArticleResponse;
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
    private SwipeRefreshLayout refreshLayout;
    private ProgressBar progressBar;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        articleRecycler = view.findViewById(R.id.article_recycler);
        refreshLayout = view.findViewById(R.id.refresh_layout);
        progressBar = view.findViewById(R.id.progress_bar);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadArticles();
            }
        });
        return view;
    }

    private void stopRefreshing() {
        if(isAdded()) {
            refreshLayout.setRefreshing(false);
            progressBar.setVisibility(View.GONE);
        }
    }

    private void loadArticles() {
        RestAdapter.getArticlesEndpoint()
                .getArticles()
                .enqueue(new Callback<ArticleResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ArticleResponse> call,
                                           @NonNull Response<ArticleResponse> response) {
                        stopRefreshing();
                        if(response.isSuccessful()) {
                            adapter.setArticles(response.body().getArticles());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ArticleResponse> call,
                                          @NonNull Throwable t) {
                        stopRefreshing();
                    }
                });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ArticleAdapter();
        articleRecycler.setAdapter(adapter);
        articleRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        articleRecycler.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext())
            .build());
        loadArticles();
    }
}
