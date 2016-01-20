package com.canyinghao.canadapterdemo.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.canyinghao.canadapter.CanOnItemListener;
import com.canyinghao.canadapterdemo.R;
import com.canyinghao.canadapterdemo.adapter.ListIndexAdapter;
import com.canyinghao.canadapterdemo.engine.DataEngine;
import com.canyinghao.canadapterdemo.mode.IndexModel;
import com.canyinghao.canadapterdemo.ui.widget.IndexView;

import java.util.List;



/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 10:06
 * 描述:
 */
public class ListIndexViewDemoFragment extends BaseFragment  {
    private static final String TAG = ListIndexViewDemoFragment.class.getSimpleName();
    private List<IndexModel> mDatas;
    private ListView mDataLv;
    private IndexView mIndexView;
    private TextView mTipTv;
    private TextView mTopcTv;

    private ListIndexAdapter mAdapter;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_listindexview);
        mDataLv = getViewById(R.id.lv_listindexview_data);
        mTopcTv = getViewById(R.id.tv_listindexview_topc);

        mIndexView = getViewById(R.id.indexview);
        mTipTv = getViewById(R.id.tv_listindexview_tip);
    }

    @Override
    protected void setListener() {
        mAdapter = new ListIndexAdapter(mActivity);
        mAdapter.setOnItemListener(new CanOnItemListener(){

            @Override
            public void onItemChildClick( View childView, int position) {
                if (childView.getId() == R.id.tv_item_indexview_name) {
                    showSnackbar("选择了城市 " + mAdapter.getItem(position).name);
                }
            }
        });

        mIndexView.setOnChangedListener(new IndexView.OnChangedListener() {
            @Override
            public void onChanged(String text) {
                int position = mAdapter.getPositionForSection(text.charAt(0));
                if (position != -1) {
                    // position的item滑动到ListView的第一个可见条目
                    mDataLv.setSelection(position);
                }
            }
        });

        mDataLv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (mAdapter.getCount() > 0) {
                    mTopcTv.setText(mAdapter.getItem(firstVisibleItem).topc);
                }
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mIndexView.setTipTv(mTipTv);

        mDatas = DataEngine.loadIndexModelDatas();
        mAdapter.setList(mDatas);
        mDataLv.setAdapter(mAdapter);

        mTopcTv.setText(mAdapter.getItem(0).topc);
    }

    @Override
    protected void onUserVisible() {
    }


}