package jason.xie.columns.articles;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import jason.xie.columns.R;
import jason.xie.columns.model.Article;
import jason.xie.columns.utils.SystemUtils;

/**
 * Created by Jason Xie on 2016/6/4.
 */

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder> {

    private Context context;
    private List<Article> articles;
    private Callback callback;

    public ArticlesAdapter(Context context, List<Article> articles, Callback callback){
        this.context = context;
        this.articles = articles;
        this.callback = callback;
    }

    public void setArticles(List<Article> articles){
        this.articles = articles;
    }

    @Override
    public ArticlesAdapter.ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(context).inflate(R.layout.item_article, parent, false);
        final ArticlesAdapter.ArticleViewHolder viewHolder = new ArticlesAdapter.ArticleViewHolder(itemView);
        viewHolder.viewContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(callback != null){
                    callback.onItemClick(viewHolder.article);
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ArticlesAdapter.ArticleViewHolder holder, int position) {
        if(articles == null){
            return;
        }
        Article article = articles.get(position);
        holder.article = article;

        int margin = SystemUtils.dip2px(context, 12);
        int height = (int) ((SystemUtils.getScreenWidth(context) - SystemUtils.dip2px(context, 24)) * 0.618);
        LinearLayout.LayoutParams coverLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        coverLayoutParams.setMargins(margin, margin, margin, margin);
        holder.imageCover.setLayoutParams(coverLayoutParams);

        holder.textTitle.setText(article.title);
        if(article.titleImage != null && !article.titleImage.isEmpty()){
            Picasso.with(context)
                    .load(article.titleImage)
                    .into(holder.imageCover);
        }
    }

    @Override
    public int getItemCount() {
        if(articles == null){
            return 0;
        }
        return articles.size();
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder {
        public View viewContent;
        public ImageView imageCover;
        public TextView textTitle;
        public Article article;

        public ArticleViewHolder(View view){
            super(view);
            viewContent = view.findViewById(R.id.layout_content);
            imageCover = (ImageView) view.findViewById(R.id.image_cover);
            textTitle = (TextView) view.findViewById(R.id.text_title);
        }
    }

    public interface Callback {
        void onItemClick(Article article);
    }
}
