package com.canyinghao.canadapterdemo.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;

import com.canyinghao.canadapter.CanOnItemListener;
import com.canyinghao.canadapterdemo.App;
import com.canyinghao.canadapterdemo.R;
import com.canyinghao.canadapterdemo.adapter.NormalAdapterViewAdapter;
import com.canyinghao.canadapterdemo.engine.ApiEngine;
import com.canyinghao.canadapterdemo.mode.NormalModel;

import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/6/28 下午12:34
 * 描述:
 */
public class ListViewDemoFragment extends BaseFragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private static final String TAG = ListViewDemoFragment.class.getSimpleName();
    private ListView mDataLv;
    private NormalAdapterViewAdapter mAdapter;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_listview);
        mDataLv = getViewById(R.id.lv_listview_data);
    }

    @Override
    protected void setListener() {
        mDataLv.setOnItemClickListener(this);
        mDataLv.setOnItemLongClickListener(this);

        mAdapter = new NormalAdapterViewAdapter(mActivity);
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
            public void onItemChildCheckedChanged(CompoundButton childView, int position, boolean isChecked) {
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
        mDataLv.setAdapter(mAdapter);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showSnackbar("点击了条目 " + mAdapter.getItem(position).title);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        showSnackbar("长按了" + mAdapter.getItem(position).title);
        return true;
    }


}