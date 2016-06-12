package jason.xie.columns.customcolumns;

import java.util.List;

import jason.xie.columns.BasePresenter;
import jason.xie.columns.BaseView;
import jason.xie.columns.defaultcolumns.DefaultColumnsContract;
import jason.xie.columns.model.Column;

/**
 * Created by Jason Xie on 2016/6/4.
 */

public interface CustomColumnsContract {

    interface View extends BaseView<CustomColumnsContract.Presenter> {

        void showLoading();

        void showColumns(List<Column> columns);

        void showError();

        void showEmpty();
    }

    interface Presenter extends BasePresenter {

        void loadColumns();

        void addColumn(String id);

        void removeColumn(int position);

        void saveColumns();
    }
}
