package jason.xie.columns.articledetail;

import jason.xie.columns.ColumnsApplication;
import jason.xie.columns.model.APIService;
import jason.xie.columns.model.ArticleDetail;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Jason Xie on 2016/6/4.
 */

public class ArticleDetailPresenter implements ArticleDetailContract.Presenter {

    private Subscription mSubscription;
    private ColumnsApplication mApplication;
    private APIService mAPIService;
    private ArticleDetailContract.View mArticleDetailView;

    public ArticleDetailPresenter(ArticleDetailContract.View articleDetailView){
        this.mArticleDetailView = articleDetailView;
        mApplication = ColumnsApplication.get();
        mAPIService = mApplication.getAPIService();
    }

    @Override
    public void loadContent(String slug) {
        mArticleDetailView.showLoading();
        if(mSubscription != null){
            mSubscription.unsubscribe();
        }
        mSubscription = mAPIService.getArticleContent(slug)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(mApplication.defaultSubscribeScheduler())
                .subscribe(new Subscriber<ArticleDetail>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ArticleDetail articleDetail) {
                        mArticleDetailView.showContent(articleDetail.content);
                    }
                });
    }

    @Override
    public void start() {

    }

    @Override
    public void stop(){
        mArticleDetailView = null;
        if(mSubscription != null){
            mSubscription.unsubscribe();
        }
    }
}
