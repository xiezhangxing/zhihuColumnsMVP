package jason.xie.columns.articles;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.classic.common.MultipleStatusView;

import java.util.ArrayList;
import java.util.List;

import jason.xie.columns.BaseActivity;
import jason.xie.columns.R;
import jason.xie.columns.articledetail.ArticleDetailActivity;
import jason.xie.columns.defaultcolumns.DefaultColumnsContract;
import jason.xie.columns.model.Article;

/**
 * Created by Jason Xie on 2016/6/4.
 */

public class ArticlesActivity extends BaseActivity implements ArticlesContract.View, ArticlesAdapter.Callback {

    private String mId;
    private String mName;
    private ArticlesContract.Presenter mPresenter;
    private ArticlesAdapter mAdapter;
    private List<Article> mArticles;
    private boolean mIsLoadingMore = false;

    private MultipleStatusView mMultipleStatusView;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle onSavedInstance){
        super.onCreate(onSavedInstance);
        setContentView(R.layout.activity_articles);
        mId = getIntent().getStringExtra("id");
        mName = getIntent().getStringExtra("name");
        setTitle(mName);

        mRecyclerView = (RecyclerView) findViewById(R.id.view_recycler);
        mMultipleStatusView = (MultipleStatusView) findViewById(R.id.view_multiple_status);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.content_view);
        mRefreshLayout.setColorSchemeColors(R.color.colorPrimary, R.color.colorAccent);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshLayout.setRefreshing(true);
                mPresenter.loadArticles(mId, 10, 0);
            }
        });
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                int totalItem = layoutManager.getItemCount();
                //当剩下2个item时加载下一页
                if (lastVisibleItem > totalItem - 2 && dy > 0 && !mIsLoadingMore) {
                    mIsLoadingMore = true;
                    mPresenter.loadArticles(mId, 10, mArticles.size());
                }
            }
        });
        mAdapter = new ArticlesAdapter(this, mArticles, this);
        mRecyclerView.setAdapter(mAdapter);

        mPresenter = new ArticlesPresenter(this);
        mPresenter.loadArticles(mId, 10, 0);
    }

    @Override
    public void showLoading() {
        mMultipleStatusView.showLoading();
    }

    @Override
    public void showArticles(List<Article> articles) {
        mIsLoadingMore = false;
        mRefreshLayout.setRefreshing(false);
        mMultipleStatusView.showContent();
        mArticles = articles;
        mAdapter.setArticles(mArticles);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {
        mMultipleStatusView.showError();
    }

    @Override
    public void showEmpty() {
        mMultipleStatusView.showEmpty();
    }

    @Override
    public void setPresenter(DefaultColumnsContract.Presenter presenter) {

    }

    @Override
    public Context getContext(){
        return this;
    }

    @Override
    public void onItemClick(Article article) {
        Intent intent = new Intent(this, ArticleDetailActivity.class);
        intent.putExtra("slug", article.slug);
        intent.putExtra("title", article.title);
        startActivity(intent);
    }
}
