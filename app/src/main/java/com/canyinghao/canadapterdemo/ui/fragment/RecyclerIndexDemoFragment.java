package com.canyinghao.canadapterdemo.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.canyinghao.canadapter.CanOnItemListener;
import com.canyinghao.canadapterdemo.R;
import com.canyinghao.canadapterdemo.adapter.RecyclerIndexAdapter;
import com.canyinghao.canadapterdemo.engine.DataEngine;
import com.canyinghao.canadapterdemo.mode.IndexModel;
import com.canyinghao.canadapterdemo.ui.widget.Divider;
import com.canyinghao.canadapterdemo.ui.widget.IndexView;

import java.util.List;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 10:06
 * 描述:
 */
public class RecyclerIndexDemoFragment extends BaseFragment  {
    private static final String TAG = RecyclerIndexDemoFragment.class.getSimpleName();
    private RecyclerIndexAdapter mAdapter;
    private List<IndexModel> mDatas;
    private RecyclerView mDataRv;
    private LinearLayoutManager mLayoutManager;
    private IndexView mIndexView;
    private TextView mTipTv;
    private TextView mTopcTv;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_recyclerindexview);
        mDataRv = getViewById(R.id.rv_recyclerindexview_data);
        mTopcTv = getViewById(R.id.tv_recyclerindexview_topc);

        mIndexView = getViewById(R.id.indexview);
        mTipTv = getViewById(R.id.tv_recyclerindexview_tip);
    }

    @Override
    protected void setListener() {
        mAdapter = new RecyclerIndexAdapter(mDataRv);
        mAdapter.setOnItemListener(new CanOnItemListener(){
            @Override
            public void onItemChildClick(View childView, int position) {
                if (childView.getId() == R.id.tv_item_indexview_name) {
                    showSnackbar("选择了城市 " + mAdapter.getItem(position).name);
                }
            }
        });

        mIndexView.setOnChangedListener(new IndexView.OnChangedListener() {
            @Override
            public void onChanged(String text) {
                final int position = mAdapter.getPositionForSection(text.charAt(0));
                if (position != -1) {
                    // position的item滑动到RecyclerView的可见区域，如果已经可见则不会滑动
//                    mDataRv.scrollToPosition(position);


//                  View v =   mDataRv.findViewHolderForAdapterPosition(position).itemView;
////                    View v =   mLayoutManager.findViewByPosition(position);
//                     int y = v.getTop();
//
////                    Log.e("setOnChangedListener", y + "setOnChangedListener");
//
//                    mLayoutManager.scrollToPositionWithOffset(0, y);



//                   float ca =  calculateScrollProgress(mDataRv);
//                  int p =  getPositionFromScrollProgress(position/(float)mAdapter.getItemCount());
                    mDataRv.scrollToPosition(mAdapter.getItemCount()-1);

                    mDataRv.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mDataRv.scrollToPosition(position);
                        }
                    },10);
//                    mDataRv.scrollTo(0,y);
                }
            }
        });
        mDataRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (mAdapter.getItemCount() > 0) {
                    mTopcTv.setText(mAdapter.getItem(mLayoutManager.findFirstVisibleItemPosition()).topc);
                }
            }
        });
    }


    private int getPositionFromScrollProgress(float scrollProgress) {
        return (int) (mDataRv.getAdapter().getItemCount() * scrollProgress);
    }


    public float calculateScrollProgress(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int lastFullyVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition();

        View visibleChild = recyclerView.getChildAt(0);
        if (visibleChild == null) {
            return 0;
        }
        RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(visibleChild);
        int itemHeight = holder.itemView.getHeight();
        int recyclerHeight = recyclerView.getHeight();
        int itemsInWindow = recyclerHeight / itemHeight;

        int numItemsInList = recyclerView.getAdapter().getItemCount();
        int numScrollableSectionsInList = numItemsInList - itemsInWindow;
        int indexOfLastFullyVisibleItemInFirstSection = numItemsInList - numScrollableSectionsInList - 1;

        int currentSection = lastFullyVisiblePosition - indexOfLastFullyVisibleItemInFirstSection;

        return (float) currentSection / numScrollableSectionsInList;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mIndexView.setTipTv(mTipTv);

        mDataRv.addItemDecoration(new Divider(mActivity));
        mLayoutManager = new LinearLayoutManager(mActivity);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        mLayoutManager.setReverseLayout(true);
        mDataRv.setLayoutManager(mLayoutManager);


        mDatas = DataEngine.loadIndexModelDatas();
        mAdapter.setList(mDatas);
        mDataRv.setAdapter(mAdapter);

        mTopcTv.setText(mAdapter.getItem(0).topc);
    }

    @Override
    protected void onUserVisible() {
    }

    public int dp2Px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}