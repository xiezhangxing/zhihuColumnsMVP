package jason.xie.columns.customcolumns;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jason.xie.columns.ColumnsApplication;
import jason.xie.columns.model.APIService;
import jason.xie.columns.model.Column;
import jason.xie.columns.model.SharedPreferencesHelper;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Jason Xie on 2016/6/4.
 */

public class CustomColumnsPresenter implements CustomColumnsContract.Presenter {

    private CustomColumnsContract.View mCustomColumnsView;
    private List<String> mColumnsId = new ArrayList<>();
    private Subscription mSubscription;
    private ColumnsApplication mApplication;
    private APIService mAPIService;
    private List<Column> mColumns = new ArrayList<>();
    private SharedPreferencesHelper preferencesHelper;

    public CustomColumnsPresenter(CustomColumnsContract.View customColumnsView, List<String> columnsId){
        mCustomColumnsView = customColumnsView;
        mColumnsId.addAll(columnsId);
        mApplication = ColumnsApplication.get();
        mAPIService = mApplication.getAPIService();
        //preferencesHelper = ColumnsApplication.get().getPreferencesHelper();
        preferencesHelper = new SharedPreferencesHelper(mCustomColumnsView.getContext());
    }

    @Override
    public void loadColumns() {
        mColumns.clear();
        if(mColumnsId == null || mColumnsId.size() < 1){
            mCustomColumnsView.showEmpty();
            return;
        }
        mCustomColumnsView.showLoading();
        if(mSubscription != null){
            mSubscription.unsubscribe();
        }
        for(String id : mColumnsId){
        mSubscription = mAPIService.getColumnById(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(mApplication.defaultSubscribeScheduler())
                .subscribe(new Subscriber<Column>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Column column) {
                        mColumns.add(column);
                        mCustomColumnsView.showColumns(mColumns);
                    }
                });
        }
    }

    @Override
    public void addColum(String id) {
        mColumnsId.add(id);
        loadColumns();
    }

    @Override
    public void removeColumn(int position) {
        mColumnsId.remove(position);
        mColumns.remove(position);
        mCustomColumnsView.showColumns(mColumns);
    }

    @Override
    public void saveColumns() {
        preferencesHelper.saveCustomIds(mColumnsId);
    }

    @Override
    public void start() {
        loadColumns();
    }

    @Override
    public void stop() {
        mCustomColumnsView = null;
        if(mSubscription != null){
            mSubscription.unsubscribe();
        }
    }
}
