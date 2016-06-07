package jason.xie.columns.articledetail;

import jason.xie.columns.BasePresenter;
import jason.xie.columns.BaseView;
import jason.xie.columns.defaultcolumns.DefaultColumnsContract;

/**
 * Created by Jason Xie on 2016/6/4.
 */

public interface ArticleDetailContract {

    interface View extends BaseView<DefaultColumnsContract.Presenter> {

        void showLoading();

        void showContent(String content);

        void showError();
    }

    interface Presenter extends BasePresenter {

        void loadContent(String slug);
    }
}
