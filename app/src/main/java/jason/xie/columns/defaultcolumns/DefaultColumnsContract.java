package jason.xie.columns.defaultcolumns;

import java.util.List;

import jason.xie.columns.BasePresenter;
import jason.xie.columns.BaseView;
import jason.xie.columns.model.Column;

/**
 * Created by Jason Xie on 2016/6/4.
 */

public interface DefaultColumnsContract {

    interface View extends BaseView<Presenter>{

        void showLoading();

        void showColumns(List<Column> columns);

        void showError();
    }

    interface Presenter extends BasePresenter{
        void loadColumns();
    }
}
