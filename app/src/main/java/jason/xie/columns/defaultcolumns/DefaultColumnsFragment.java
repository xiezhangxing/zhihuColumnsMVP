package jason.xie.columns.defaultcolumns;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.classic.common.MultipleStatusView;

import java.util.List;

import jason.xie.columns.ColumnsAdapter;
import jason.xie.columns.R;
import jason.xie.columns.articles.ArticlesActivity;
import jason.xie.columns.model.Column;

/**
 * Created by Jason Xie on 2016/6/4.
 */

public class DefaultColumnsFragment extends Fragment implements DefaultColumnsContract.View, ColumnsAdapter.Callback {

    private DefaultColumnsContract.Presenter mPresenter;
    private ColumnsAdapter mAdapter;
    private List<Column> mColumns;
    private String[] mDefaultIds;

    private MultipleStatusView mMultipleStatusView;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecylerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        mDefaultIds = getActivity().getResources().getStringArray(R.array.default_columns_ids);
        mPresenter = new DefaultColumnsPresenter(this, mDefaultIds);
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_columns, container, false);
        mRecylerView = (RecyclerView) view.findViewById(R.id.view_recycler);
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
        mRecylerView.setLayoutManager(layoutManager);
        mAdapter = new ColumnsAdapter(getActivity(), mColumns, this);
        mRecylerView.setAdapter(mAdapter);

        mPresenter.start();
        return view;
    }

    @Override
    public void showLoading() {
        mMultipleStatusView.showLoading();
    }

    @Override
    public void showColumns(List<Column> columns) {
        mRefreshLayout.setRefreshing(false);
        mMultipleStatusView.showContent();
        mColumns = columns;
        mAdapter.setColumns(columns);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {
        mMultipleStatusView.showError();
    }

    @Override
    public void setPresenter(DefaultColumnsContract.Presenter presenter) {

    }

    @Override
    public Context getContext(){
        return getActivity();
    }

    @Override
    public void onItemClick(Column column) {
        Intent intent = new Intent(getActivity(), ArticlesActivity.class);
        intent.putExtra("id", column.slug);
        getActivity().startActivity(intent);
    }
}
