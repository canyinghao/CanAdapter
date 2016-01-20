package com.canyinghao.canadapterdemo.adapter;

import android.content.Context;
import android.view.View;

import com.canyinghao.canadapter.CanAdapter;
import com.canyinghao.canadapter.CanHolderHelper;
import com.canyinghao.canadapterdemo.R;
import com.canyinghao.canadapterdemo.mode.ChatModel;


/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/21 上午12:39
 * 描述:
 */
public class ListChatAdapter extends CanAdapter<ChatModel> {

    public ListChatAdapter(Context context) {
        super(context, R.layout.item_chat);
    }

    @Override
    protected void setItemListener(CanHolderHelper viewHolderHelper) {
    }

    @Override
    public void setView(CanHolderHelper viewHolderHelper, int position, ChatModel model) {
        if (model.mUserType == ChatModel.UserType.From) {
            viewHolderHelper.setVisibility(R.id.rl_item_chat_to, View.GONE);
            viewHolderHelper.setVisibility(R.id.rl_item_chat_from, View.VISIBLE);
            String htmlMsg = String.format(mContext.getString(R.string.color_msg_from), model.mMsg);
            viewHolderHelper.setHtml(R.id.tv_item_chat_from_msg, htmlMsg);
        } else {
            viewHolderHelper.setVisibility(R.id.rl_item_chat_from, View.GONE);
            viewHolderHelper.setVisibility(R.id.rl_item_chat_to, View.VISIBLE);
            String htmlMsg = String.format(mContext.getString(R.string.color_msg_to), model.mMsg);
            viewHolderHelper.setHtml(R.id.tv_item_chat_to_msg, htmlMsg);
        }
    }

}