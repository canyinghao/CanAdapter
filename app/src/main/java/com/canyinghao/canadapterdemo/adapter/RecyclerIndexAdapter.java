package com.canyinghao.canadapterdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.canyinghao.canadapter.CanHolderHelper;
import com.canyinghao.canadapter.CanRVAdapter;
import com.canyinghao.canadapterdemo.R;
import com.canyinghao.canadapterdemo.mode.IndexModel;


/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 16:31
 * 描述:
 */
public class RecyclerIndexAdapter extends CanRVAdapter<IndexModel> {
    public RecyclerIndexAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_indexview);
    }

    @Override
    public void setItemListener(CanHolderHelper viewHolderHelper) {
        viewHolderHelper.setItemChildClickListener(R.id.tv_item_indexview_name);
    }

    @Override
    public void setView(CanHolderHelper viewHolderHelper, int position, IndexModel model) {
        int section = getSectionForPosition(position);
        if (position == getPositionForSection(section)) {
            viewHolderHelper.setVisibility(R.id.tv_item_indexview_catalog, View.VISIBLE);
            viewHolderHelper.setText(R.id.tv_item_indexview_catalog, model.topc);
        } else {
            viewHolderHelper.setVisibility(R.id.tv_item_indexview_catalog, View.GONE);
        }
        viewHolderHelper.setText(R.id.tv_item_indexview_name, model.name);
    }

    public int getSectionForPosition(int position) {
        return mList.get(position).topc.charAt(0);
    }

    public int getPositionForSection(int section) {
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = mList.get(i).topc;
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }
}