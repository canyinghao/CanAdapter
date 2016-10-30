package com.canyinghao.canadapterdemo.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.canyinghao.canadapter.CanHolderHelper;
import com.canyinghao.canadapter.CanRVHFAdapter;
import com.canyinghao.canadapter.CanSpanSizeLookup;
import com.canyinghao.canadapterdemo.R;
import com.canyinghao.canadapterdemo.model.MainBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by canyinghao on 16/1/21.
 */
public class RVHFGridFragment extends Fragment {

    RecyclerView recyclerView;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        recyclerView = new RecyclerView(getContext());
        recyclerView.setHasFixedSize(true);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 4);




        final CanRVHFAdapter<MainBean,MainBean,MainBean,MainBean> adapter = new CanRVHFAdapter<MainBean,MainBean,MainBean,MainBean>(recyclerView,
                R.layout.item_image,R.layout.item_group2,R.layout.item_header,R.layout.item_footer) {




            @Override
            protected void setChildView(CanHolderHelper helper, int groupPosition, int position, final MainBean model) {
                helper.setText(R.id.tv_title, model.title);


                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(mContext,model.title,Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            protected void setGroupView(final CanHolderHelper helper, final int groupPosition, int position, final MainBean model) {









            }

            @Override
            protected void setHeaderView(CanHolderHelper helper, int position, MainBean bean) {

            }

            @Override
            protected void setFooterView(CanHolderHelper helper, int position, MainBean bean) {

            }


        };



        recyclerView.setHasFixedSize(true);

        CanSpanSizeLookup headerSpanSizeLookup = new CanSpanSizeLookup(adapter, mLayoutManager.getSpanCount());
        mLayoutManager.setSpanSizeLookup(headerSpanSizeLookup);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setFooter(new MainBean());
        adapter.setHeader(new MainBean());




        List<MainBean> gList = new ArrayList<>();
        List<List<MainBean>>  cList = new ArrayList<>();

        for (int i = 0; i < 2; i++) {

            gList.add(new MainBean("2016.05.0"+(i+1)));

            List<MainBean> childeList =new ArrayList<>();


            for (int j = 0; j < 2000; j++) {
                childeList.add(new MainBean("title"+j));
            }

            cList.add(childeList);
        }





        adapter.setList(gList,cList);


        return recyclerView;
    }



}
