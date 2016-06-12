package jason.xie.columns.articles;

import java.util.ArrayList;
import java.util.List;

import jason.xie.columns.ColumnsApplication;
import jason.xie.columns.model.APIService;
import jason.xie.columns.model.Article;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Jason Xie on 2016/6/4.
 */

public class ArticlesPresenter implements ArticlesContract.Presenter {

    private Subscription mSubscription;
    private ColumnsApplication mApplication;
    private APIService mAPIService;
    private ArticlesContract.View mArticlesView;
    private List<Article> mArticles = new ArrayList<>();

    public ArticlesPresenter(ArticlesContract.View articlesView){
        this.mArticlesView = articlesView;
        mApplication = ColumnsApplication.get();
        mAPIService = mApplication.getAPIService();
    }

    @Override
    public void loadArticles(String id, int limit, int offset) {
        if(mArticles == null || mArticles.size() < 1){
            mArticlesView.showLoading();
        }
        if(offset == 0){
            mArticles.clear();
        }
        if(mSubscription != null){
            mSubscription.unsubscribe();
        }
        mSubscription = mAPIService.getArticleList(id, limit, offset)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(mApplication.defaultSubscribeScheduler())
                .subscribe(new Subscriber<List<Article>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Article> articles) {
                        mArticles.addAll(articles);
                        mArticlesView.showArticles(mArticles);
                    }
                });
    }

    @Override
    public void start() {

    }

    @Override
    public void stop(){
        mArticlesView = null;
        if(mSubscription != null){
            mSubscription.unsubscribe();
        }
    }
}
