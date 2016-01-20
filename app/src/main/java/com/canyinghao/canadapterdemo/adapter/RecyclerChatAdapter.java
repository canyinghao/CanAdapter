package com.canyinghao.canadapterdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.canyinghao.canadapter.CanHolderHelper;
import com.canyinghao.canadapter.CanRVAdapter;
import com.canyinghao.canadapterdemo.R;
import com.canyinghao.canadapterdemo.mode.ChatModel;


/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 16:31
 * 描述:
 */
public class RecyclerChatAdapter extends CanRVAdapter<ChatModel> {
    public RecyclerChatAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_chat);
    }

    @Override
    public void setItemListener(CanHolderHelper viewHolderHelper) {
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