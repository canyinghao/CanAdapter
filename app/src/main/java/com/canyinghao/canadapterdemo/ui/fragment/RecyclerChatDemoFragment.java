package com.canyinghao.canadapterdemo.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.canyinghao.canadapterdemo.R;
import com.canyinghao.canadapterdemo.adapter.RecyclerChatAdapter;
import com.canyinghao.canadapterdemo.engine.DataEngine;
import com.canyinghao.canadapterdemo.mode.ChatModel;

import java.util.List;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 10:06
 * 描述:
 */
public class RecyclerChatDemoFragment extends BaseFragment {
    private static final String TAG = RecyclerChatDemoFragment.class.getSimpleName();
    private RecyclerChatAdapter mAdapter;
    private List<ChatModel> mDatas;
    private RecyclerView mDataRv;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_recyclerview);
        mDataRv = getViewById(R.id.rv_recyclerview_data);
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataRv.setLayoutManager(layoutManager);

        mAdapter = new RecyclerChatAdapter(mDataRv);

        mDatas = DataEngine.loadChatModelDatas();
        mAdapter.setList(mDatas);
        mDataRv.setAdapter(mAdapter);
    }

    @Override
    protected void onUserVisible() {
    }
}