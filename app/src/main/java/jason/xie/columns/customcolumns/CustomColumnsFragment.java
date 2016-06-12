package jason.xie.columns.customcolumns;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.classic.common.MultipleStatusView;

import java.util.List;

import jason.xie.columns.ColumnsAdapter;
import jason.xie.columns.R;
import jason.xie.columns.articles.ArticlesActivity;
import jason.xie.columns.model.Column;
import jason.xie.columns.model.SharedPreferencesHelper;

/**
 * Created by Jason Xie on 2016/6/4.
 */

public class CustomColumnsFragment extends Fragment implements CustomColumnsContract.View, ColumnsAdapter.Callback, View.OnClickListener {

    private CustomColumnsContract.Presenter mPresenter;
    private ColumnsAdapter mAdapter;
    private List<Column> mColumns;
    private List<String> mCustomIds;
    private SharedPreferencesHelper preferencesHelper;

    private MultipleStatusView mMultipleStatusView;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFab;
    private TextView mEmptyView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //preferencesHelper = ColumnsApplication.get().getPreferencesHelper();
        preferencesHelper = new SharedPreferencesHelper(getActivity());
        mCustomIds = preferencesHelper.getCustomIds();
        mPresenter = new CustomColumnsPresenter(this, mCustomIds);
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_columns_custom, container, false);
        mEmptyView = (TextView) view.findViewById(R.id.text_empty);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.view_recycler);
        mMultipleStatusView = (MultipleStatusView) view.findViewById(R.id.view_multiple_status);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.content_view);
        mRefreshLayout.setColorSchemeColors(R.color.colorPrimary, R.color.colorAccent);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshLayout.setRefreshing(true);
                mPresenter.start();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new ColumnsAdapter(getActivity(), mColumns, this);
        mRecyclerView.setAdapter(mAdapter);
        mFab = (FloatingActionButton) view.findViewById(R.id.fab);
        mFab.setOnClickListener(this);

        mPresenter.start();
        return view;
    }

    @Override
    public void onPause(){
        super.onPause();
        mPresenter.saveColumns();
    }

    @Override
    public void showLoading() {
        mRefreshLayout.setRefreshing(false);
        mMultipleStatusView.showLoading();
        mEmptyView.setVisibility(View.GONE);
    }

    @Override
    public void showColumns(List<Column> columns) {
        mMultipleStatusView.showContent();
        mEmptyView.setVisibility(View.GONE);
        mColumns = columns;
        mAdapter.setColumns(columns);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {
        mMultipleStatusView.showError();
        mEmptyView.setVisibility(View.GONE);
    }

    @Override
    public void showEmpty(){
        mMultipleStatusView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setPresenter(CustomColumnsContract.Presenter presenter) {

    }

    @Override
    public Context getContext(){
        return getActivity();
    }

    @Override
    public void onItemClick(Column column) {
        Intent intent = new Intent(getActivity(), ArticlesActivity.class);
        intent.putExtra("id", column.slug);
        intent.putExtra("name", column.name);
        getActivity().startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab:
                final MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                        .title("请输入专栏")
                        .content("如博山炉(https://zhuanlan.zhihu.com/boshanlu)id为boshanlu")
                        .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS)
                        .input("", "", new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {

                            }
                        }).build();

                dialog.setActionButton(DialogAction.NEGATIVE, "取消");
                dialog.setActionButton(DialogAction.POSITIVE, "确定");

                dialog.getActionButton(DialogAction.NEGATIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.getActionButton(DialogAction.POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String columnId = dialog.getInputEditText().getText().toString();
                        if (!columnId.isEmpty()){
                            mPresenter.addColumn(columnId);
                        }
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;

            default:
                break;
        }
    }
}
