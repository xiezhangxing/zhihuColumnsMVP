package jason.xie.columns.defaultcolumns;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import jason.xie.columns.ColumnsApplication;
import jason.xie.columns.model.APIService;
import jason.xie.columns.model.Column;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.Subscription;
import rx.android.MainThreadSubscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Jason Xie on 2016/6/4.
 */

public class DefaultColumnsPresenter implements DefaultColumnsContract.Presenter {

    private DefaultColumnsContract.View mDefaultColumnsView;
    private String[] mDefaultIds;
    private Subscription mSubscription;
    private ColumnsApplication mApplication;
    private APIService mAPIService;
    private List<Column> mColumns = new ArrayList<>();

    public DefaultColumnsPresenter(DefaultColumnsContract.View defaultColumnsView, String[] defaultIds) {
        mDefaultColumnsView = defaultColumnsView;
        mDefaultIds = defaultIds;
        mApplication = ColumnsApplication.get();
        mAPIService = mApplication.getAPIService();
    }

    @Override
    public void loadColumns() {
        mColumns.clear();
        mDefaultColumnsView.showLoading();
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
        
        mSubscription = rx.Observable.from(mDefaultIds)
                .flatMap(new Func1<String, rx.Observable<Column>>() {
                    @Override
                    public rx.Observable<Column> call(String s) {
                        return mAPIService.getColumnById(s);
                    }
                })
                .subscribeOn(mApplication.defaultSubscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Column>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onNext(Column column) {
                        mColumns.add(column);
                        mDefaultColumnsView.showColumns(mColumns);
                    }
                });
    }

    @Override
    public void start() {
        loadColumns();
    }

    @Override
    public void stop() {
        mDefaultColumnsView = null;
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }
}
