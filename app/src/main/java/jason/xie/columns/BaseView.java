package jason.xie.columns;

import android.content.Context;

/**
 * Created by Jason Xie on 2016/6/4.
 */

public interface BaseView<T> {

    void setPresenter(T presenter);

    Context getContext();
}
