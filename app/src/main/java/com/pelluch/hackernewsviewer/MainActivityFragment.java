package com.pelluch.hackernewsviewer;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pelluch.hackernewsviewer.http.RestAdapter;
import com.pelluch.hackernewsviewer.models.Article;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RestAdapter.getArticlesEndpoint()
                .getArticles()
                .enqueue(new Callback<List<Article>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Article>> call,
                                           @NonNull Response<List<Article>> response) {
                        Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT)
                                .show();
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
