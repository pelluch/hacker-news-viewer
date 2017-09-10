package com.pelluch.hackernewsviewer.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pelluch.hackernewsviewer.ArticleActivity;
import com.pelluch.hackernewsviewer.DaoHelper;
import com.pelluch.hackernewsviewer.R;
import com.pelluch.hackernewsviewer.models.Article;
import com.pelluch.hackernewsviewer.models.DeletedArticle;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Minutes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by pablo on 9/9/17.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private final static SimpleDateFormat timeFormat = new
            SimpleDateFormat("HH:mm", Locale.getDefault());
    private final static SimpleDateFormat dateFormat = new
            SimpleDateFormat("dd/MMM/yy", Locale.getDefault());

    private List<Article> articles = new ArrayList<>();

    public void setArticles(List<Article> articles) {
        /* Collections.sort(articles, new Comparator<Article>() {
            @Override
            public int compare(Article o1, Article o2) {
                return o2.getCreatedAtI() - o1.getCreatedAtI();
            }
        });
        */
        this.articles = articles;
        notifyDataSetChanged();
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_row, parent, false);
        final ArticleViewHolder holder = new ArticleViewHolder(view);
        holder.deleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Article deleted = articles.remove(position);
                notifyItemRemoved(position);
                DaoHelper.getInstance(parent.getContext())
                        .getSession()
                        .getDeletedArticleDao()
                        .insertInTx(new DeletedArticle(deleted.getObjectID()));
            }
        });
        holder.topView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Article article = articles.get(holder.getAdapterPosition());
                if(URLUtil.isValidUrl(article.getRealUrl())) {
                    Intent intent = new Intent(parent.getContext(), ArticleActivity.class);
                    intent.putExtra("url", article.getRealUrl());
                    parent.getContext().startActivity(intent);
                } else {
                    Toast.makeText(parent.getContext(),
                            "No valid url for this story",
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.titleText.setText(article.getRealTitle());

        DateTime articleDate = article.getCreatedAt();
        DateTime currentDate = DateTime.now();

        String suffix;
        String dateString = dateFormat.format(articleDate.toDate());

        int minutes = Minutes.minutesBetween(articleDate, currentDate).getMinutes();
        if(minutes < 60) {
            suffix = minutes + "m";
        } else {
            DateTime midnightToday = DateTime.now().withTimeAtStartOfDay();
            if(articleDate.isAfter(midnightToday)) {
                Hours hours = Hours.hoursBetween(articleDate, currentDate);
                suffix = hours.getHours() + "h";
            } else if(articleDate.isAfter(midnightToday.minusDays(1))) {
                suffix = "Yesterday";
            } else {
                suffix = dateString.replace('.', Character.MIN_VALUE);
            }
        }
        holder.authorText.setText(article.getAuthor() + " - " + suffix);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    static class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView titleText;
        TextView authorText;
        RelativeLayout deleteLayout;
        LinearLayout topView;
        ArticleViewHolder(View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.title_text);
            authorText = itemView.findViewById(R.id.author_text);
            deleteLayout = itemView.findViewById(R.id.rl_match_trash);
            topView = itemView.findViewById(R.id.top_view);
        }
    }
}
