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

import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener;
import com.pelluch.hackernewsviewer.adapters.ArticleAdapter;
import com.pelluch.hackernewsviewer.http.RestAdapter;
import com.pelluch.hackernewsviewer.models.Article;
import com.pelluch.hackernewsviewer.models.ArticleDao;
import com.pelluch.hackernewsviewer.models.ArticleResponse;
import com.pelluch.hackernewsviewer.models.DeletedArticle;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private RecyclerTouchListener onTouchListener;

    public MainActivityFragment() {
    }

    @Override
    public void onPause() {
        articleRecycler.removeOnItemTouchListener(onTouchListener);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        articleRecycler.addOnItemTouchListener(onTouchListener);
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

    private ArticleDao getArticleDao() {
        return DaoHelper.getInstance(getContext())
                .getSession()
                .getArticleDao();
    }

    private List<DeletedArticle> getDeletedArticles() {
        return DaoHelper.getInstance(getContext())
                .getSession()
                .getDeletedArticleDao()
                .loadAll();
    }

    private List<Article> loadArticlesInOrder() {
        List<DeletedArticle> deleted = getDeletedArticles();
        return getArticleDao()
                .queryBuilder()
                .orderDesc(ArticleDao.Properties.CreatedAt)
                .where(ArticleDao.Properties.ObjectID.notIn(deleted))
                .list();

    }

    /**
     * Load articles from API using retrofit
     */
    private void loadArticles() {
        RestAdapter.getArticlesEndpoint()
                .getArticles()
                .enqueue(new Callback<ArticleResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ArticleResponse> call,
                                           @NonNull Response<ArticleResponse> response) {
                        stopRefreshing();
                        if(response.isSuccessful()) {
                            final List<Article> articles = response.body()
                                    .getArticles();

                            // For somewhat more efficient removal of deleted items,
                            // first put articles into Hash
                            final Map<String, Article> articleMap = new HashMap<>();
                            for(Article article : articles) {
                                articleMap.put(
                                        article.getObjectID(), article
                                );
                            }

                            // Now iterate through deleted articles
                            // This has a lot of room for optimization, especially
                            // considering cases when number of deleted articles
                            // is large and grows with time
                            for(DeletedArticle deleted : getDeletedArticles()) {
                                articleMap.remove(deleted.getObjectID());
                            }

                            // Remove existing articles
                            // It's assumed from requirements that we don't store
                            // older articles unless we are offline or an error occurs
                            getArticleDao().deleteAll();
                            getArticleDao().insertOrReplaceInTx(articleMap.values());
                            loadFromLocalStorage();
                        } else {
                            loadFromLocalStorage();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ArticleResponse> call,
                                          @NonNull Throwable t) {
                        stopRefreshing();
                        loadFromLocalStorage();
                    }
                });
    }

    private void loadFromLocalStorage() {
        List<Article> articles =
                loadArticlesInOrder();
        adapter.setArticles(articles);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ArticleAdapter();
        articleRecycler.setAdapter(adapter);
        articleRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        articleRecycler.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext())
            .build());

        // Swipe layout configuration
        onTouchListener = new RecyclerTouchListener(getActivity(),
                articleRecycler)
                .setSwipeOptionViews(R.id.rl_match_trash)
                .setSwipeable(R.id.swipe_foreground, R.id.swipe_background, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
                    @Override
                    public void onSwipeOptionClicked(int viewID, int position) {

                    }
                });
        loadArticles();
    }
}
