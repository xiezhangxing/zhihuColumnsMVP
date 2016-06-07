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

    private APIService githubService;
    private Scheduler defaultSubscribeScheduler;
    private static ColumnsApplication mApplication;
    private SharedPreferencesHelper preferencesHelper;
    private Context context;

    @Override
    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();
    }

    public static ColumnsApplication get() {
        if (mApplication == null) {
            mApplication = new ColumnsApplication();
        }
        return mApplication;
    }

    public APIService getAPIService() {
        if (githubService == null) {
            githubService = APIService.Factory.create();
        }
        return githubService;
    }

    public Scheduler defaultSubscribeScheduler() {
        if (defaultSubscribeScheduler == null) {
            defaultSubscribeScheduler = Schedulers.io();
        }
        return defaultSubscribeScheduler;
    }

    public SharedPreferencesHelper getPreferencesHelper(){
        if(preferencesHelper == null){
            preferencesHelper = new SharedPreferencesHelper(context);
        }
        return  preferencesHelper;
    }

}
