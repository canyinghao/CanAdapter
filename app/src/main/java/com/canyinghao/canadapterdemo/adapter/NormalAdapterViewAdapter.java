package com.canyinghao.canadapterdemo.adapter;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.canyinghao.canadapter.CanAdapter;
import com.canyinghao.canadapter.CanHolderHelper;
import com.canyinghao.canadapterdemo.R;
import com.canyinghao.canadapterdemo.mode.NormalModel;


/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/21 上午12:39
 * 描述:
 */
public class NormalAdapterViewAdapter extends CanAdapter<NormalModel> {
    private boolean mIsIgnoreChange = true;

    public NormalAdapterViewAdapter(Context context) {
        super(context, R.layout.item_normal);
    }

    @Override
    protected void setItemListener(CanHolderHelper viewHolderHelper) {
        viewHolderHelper.setItemChildClickListener(R.id.tv_item_normal_delete);
        viewHolderHelper.setItemChildLongClickListener(R.id.tv_item_normal_delete);
        viewHolderHelper.setItemChildCheckedChangeListener(R.id.cb_item_normal_status);
    }

    @Override
    public void setView(CanHolderHelper viewHolderHelper, int position, NormalModel model) {
        Glide.with(mContext).load(model.avatorPath).placeholder(R.mipmap.holder).error(R.mipmap.holder).into(viewHolderHelper.getImageView(R.id.iv_item_normal_avator));
        viewHolderHelper.setText(R.id.tv_item_normal_title, model.title).setText(R.id.tv_item_normal_detail, model.detail);

        // 在设置值的过程中忽略选中状态变化
        mIsIgnoreChange = true;
        viewHolderHelper.setChecked(R.id.cb_item_normal_status, model.selected);
        mIsIgnoreChange = false;
    }

    public boolean isIgnoreChange() {
        return mIsIgnoreChange;
    }
}