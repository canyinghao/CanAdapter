package com.canyinghao.canadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by canyinghao on 16/5/20.
 */
public class CanRViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    protected Context mContext;
    protected CanHolderHelper mHolderHelper;
    protected RecyclerView mRecyclerView;
    protected CanOnItemListener mOnItemListener;

    public CanRViewHolder(RecyclerView recyclerView, View itemView) {
        this(recyclerView,itemView,0);
    }


    public CanRViewHolder(RecyclerView recyclerView, View itemView, int ratio) {
        super(itemView);

        if (ratio > 0) {

            ViewGroup.LayoutParams lp = itemView.getLayoutParams() == null ? new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT) : itemView.getLayoutParams();
            if (recyclerView.getLayoutManager().canScrollHorizontally()) {
                lp.width = (recyclerView.getWidth() / ratio - recyclerView.getPaddingLeft() - recyclerView.getPaddingRight());
            } else {
                lp.height = recyclerView.getHeight() / ratio - recyclerView.getPaddingTop() - recyclerView.getPaddingBottom();
            }

            itemView.setLayoutParams(lp);
        }


        this.mRecyclerView = recyclerView;
        this.mContext = mRecyclerView.getContext();
        this.mHolderHelper = CanHolderHelper.holderHelperByRecyclerView(itemView);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public CanHolderHelper getCanHolderHelper() {
        return mHolderHelper;
    }

    public void setOnItemListener(CanOnItemListener onItemListener) {

        this.mOnItemListener = onItemListener;

    }

    @Override
    public void onClick(View view) {

        if (mOnItemListener != null) {
            mOnItemListener.onRVItemClick(mRecyclerView, view, getAdapterPosition());
        }

    }

    @Override
    public boolean onLongClick(View view) {

        if (mOnItemListener != null) {
            return mOnItemListener.onRVItemLongClick(mRecyclerView, view, getAdapterPosition());
        }
        return false;
    }
}
