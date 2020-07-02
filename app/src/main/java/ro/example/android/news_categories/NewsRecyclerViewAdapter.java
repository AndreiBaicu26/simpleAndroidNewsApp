package ro.example.android.news_categories;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ro.example.android.R;
import ro.example.android.data.local.ArticleEntity;
import ro.example.android.databinding.NewsCardViewBinding;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private List<ArticleEntity> articles = new ArrayList<>();


    public NewsRecyclerViewAdapter(Context context) {
        this.context = context;

    }

    public void setArticles(List<ArticleEntity> articleList){
        this.articles = articleList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        NewsCardViewBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.news_card_view,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(binding);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ArticleEntity articleEntity = articles.get(position);
        Picasso.get().load(articleEntity.urlToImage).resize(2048, 1600).into(holder.binding.newsImageView);
        holder.binding.articleCardViewHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(articleEntity.url));
                view.getContext().startActivity(browser);
            }
        });
        holder.binding.setArticle(articleEntity);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        NewsCardViewBinding binding;
        public MyViewHolder(@NonNull NewsCardViewBinding itemView) {
            super(itemView.getRoot());

            binding = itemView;

        }
    }

    public interface OnArticleClick{
        void onArticleClick(int position);
    }
}
