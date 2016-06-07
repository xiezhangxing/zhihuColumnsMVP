package jason.xie.columns.articles;

import java.util.List;

import jason.xie.columns.BasePresenter;
import jason.xie.columns.BaseView;
import jason.xie.columns.defaultcolumns.DefaultColumnsContract;
import jason.xie.columns.model.Article;

/**
 * Created by Jason Xie on 2016/6/4.
 */

public interface ArticlesContract {

    interface View extends BaseView<DefaultColumnsContract.Presenter> {

        void showLoading();

        void showArticles(List<Article> articles);

        void showError();

        void showEmpty();
    }

    interface Presenter extends BasePresenter {

        void loadArticles(String id, int limit, int offset);
    }
}
