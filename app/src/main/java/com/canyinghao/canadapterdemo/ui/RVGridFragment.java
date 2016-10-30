package com.canyinghao.canadapterdemo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.canyinghao.canadapter.CanHolderHelper;
import com.canyinghao.canadapter.CanOnItemListener;
import com.canyinghao.canadapter.CanRVAdapter;
import com.canyinghao.canadapterdemo.App;
import com.canyinghao.canadapterdemo.R;
import com.canyinghao.canadapterdemo.model.MainBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by canyinghao on 16/1/21.
 */
public class RVGridFragment extends Fragment {

    RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        recyclerView = new RecyclerView(getContext());



        final CanRVAdapter adapter = new CanRVAdapter<MainBean>(recyclerView, R.layout.item_main) {


            @Override
            protected void setView(CanHolderHelper helper, int position, MainBean model) {
                helper.setText(R.id.tv_title, model.title);
                helper.setText(R.id.tv_content, model.content);

            }

            @Override
            protected void setItemListener(CanHolderHelper helper) {

                helper.setItemChildClickListener(R.id.tv_title);
                helper.setItemChildClickListener(R.id.tv_content);

            }
        };


        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 4);

        recyclerView.setLayoutManager(mLayoutManager);


        recyclerView.setAdapter(adapter);


        adapter.setOnItemListener(new CanOnItemListener() {

            public void onItemChildClick(View view, int position) {

                MainBean bean = (MainBean) adapter.getItem(position);
                switch (view.getId()) {


                    case R.id.tv_title:

                        App.getInstance().show(bean.title);
                        break;

                    case R.id.tv_content:
                        App.getInstance().show(bean.content);
                        break;
                }


            }

        });


        List<MainBean> list = new ArrayList<>();

        for (int j = 0; j < 100; j++) {
            list.add(new MainBean("title"+j));
        }

        adapter.setList(list);


        return recyclerView;
    }


}
