package com.canyinghao.canadapterdemo.ui.fragment;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.canyinghao.canadapter.CanOnItemListener;
import com.canyinghao.canadapterdemo.App;
import com.canyinghao.canadapterdemo.R;
import com.canyinghao.canadapterdemo.adapter.NormalRecyclerViewAdapter;
import com.canyinghao.canadapterdemo.engine.ApiEngine;
import com.canyinghao.canadapterdemo.mode.NormalModel;
import com.canyinghao.canadapterdemo.ui.widget.Divider;

import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/6/28 下午1:30
 * 描述:
 */
public class RecyclerViewDemoFragment extends BaseFragment {
    private static final String TAG = RecyclerViewDemoFragment.class.getSimpleName();
    private NormalRecyclerViewAdapter mAdapter;
    private RecyclerView mDataRv;
    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_recyclerview);
        mDataRv = getViewById(R.id.rv_recyclerview_data);
    }

    @Override
    protected void setListener() {
        mAdapter = new NormalRecyclerViewAdapter(mDataRv);


        mAdapter.setOnItemListener(new CanOnItemListener() {


            @Override
            public void onItemChildClick( View childView, int position) {
                if (childView.getId() == R.id.tv_item_normal_delete) {
                    mAdapter.removeItem(position);
                }
            }

            @Override
            public boolean onItemChildLongClick( View childView, int position) {
                if (childView.getId() == R.id.tv_item_normal_delete) {
                    showSnackbar("长按了删除 " + mAdapter.getItem(position).title);
                    return true;
                }
                return false;
            }

            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                showSnackbar("点击了条目 " + mAdapter.getItem(position).title);
            }

            @Override
            public boolean onRVItemLongClick(ViewGroup parent, View itemView, int position) {
                showSnackbar("长按了条目 " + mAdapter.getItem(position).title);
                return true;
            }

            @Override
            public void onItemChildCheckedChanged( CompoundButton childView, int position, boolean isChecked) {
                // 在填充数据列表时，忽略选中状态变化
                if (!mAdapter.isIgnoreChange()) {
                    mAdapter.getItem(position).selected = isChecked;
                    if (isChecked) {
                        showSnackbar("选中 " + mAdapter.getItem(position).title);
                    } else {
                        showSnackbar("取消选中 " + mAdapter.getItem(position).title);
                    }
                }
            }


        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
//        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
//        layoutManager.setOrientation(GridLayoutManager.VERTICAL);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataRv.setLayoutManager(layoutManager);
        mDataRv.addItemDecoration(new Divider(mActivity));

        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback());
        mItemTouchHelper.attachToRecyclerView(mDataRv);
        mAdapter.setItemTouchHelper(mItemTouchHelper);

        mDataRv.setAdapter(mAdapter);
    }

    @Override
    protected void onUserVisible() {
        App.getInstance().getRetrofit().create(ApiEngine.class).getNormalModels().enqueue(new Callback<List<NormalModel>>() {
            @Override
            public void onResponse(Response<List<NormalModel>> response, Retrofit retrofit) {
                mAdapter.setList(response.body());
            }

            @Override
            public void onFailure(Throwable t) {
                showSnackbar("数据加载失败");
            }
        });
    }



    /**
     * 该类参考：https://github.com/iPaulPro/Android-ItemTouchHelper-Demo
     */
    private class ItemTouchHelperCallback extends ItemTouchHelper.Callback {
        public static final float ALPHA_FULL = 1.0f;

        @Override
        public boolean isLongPressDragEnabled() {
//            return true;
            return false;
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return true;
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
            if (source.getItemViewType() != target.getItemViewType()) {
                return false;
            }

            mAdapter.moveItem(source.getAdapterPosition(), target.getAdapterPosition());

            for (NormalModel normalModel : mAdapter.getList()) {
                Log.i(TAG, normalModel.title);
            }

            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            mAdapter.removeItem(viewHolder.getAdapterPosition());
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                View itemView = viewHolder.itemView;
                float alpha = ALPHA_FULL - Math.abs(dX) / (float) itemView.getWidth();
                ViewCompat.setAlpha(viewHolder.itemView, alpha);
            }
        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                viewHolder.itemView.setSelected(true);
            }
            super.onSelectedChanged(viewHolder, actionState);
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            ViewCompat.setAlpha(viewHolder.itemView, ALPHA_FULL);
            viewHolder.itemView.setSelected(false);
        }
    }
}
