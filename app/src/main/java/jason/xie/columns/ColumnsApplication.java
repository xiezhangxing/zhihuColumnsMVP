package jason.xie.columns;

import android.app.Application;
import android.content.Context;

import jason.xie.columns.model.APIService;
import jason.xie.columns.model.SharedPreferencesHelper;
import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by Jason Xie on 2016/6/4.
 */

public class ColumnsApplication extends Application {

    private APIService mApiService;
    private Scheduler mDefaultSubscribeScheduler;
    private static ColumnsApplication mApplication;
    private SharedPreferencesHelper mPreferencesHelper;
    private Context mContext;

    @Override
    public void onCreate(){
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static ColumnsApplication get() {
        if (mApplication == null) {
            mApplication = new ColumnsApplication();
        }
        return mApplication;
    }

    public APIService getAPIService() {
        if (mApiService == null) {
            mApiService = APIService.Factory.create();
        }
        return mApiService;
    }

    public Scheduler defaultSubscribeScheduler() {
        if (mDefaultSubscribeScheduler == null) {
            mDefaultSubscribeScheduler = Schedulers.io();
        }
        return mDefaultSubscribeScheduler;
    }

    public SharedPreferencesHelper getPreferencesHelper(){
        if(mPreferencesHelper == null){
            mPreferencesHelper = new SharedPreferencesHelper(mContext);
        }
        return  mPreferencesHelper;
    }

}
