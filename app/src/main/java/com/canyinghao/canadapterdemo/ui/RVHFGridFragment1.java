package com.canyinghao.canadapterdemo.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.canyinghao.canadapter.CanHolderHelper;
import com.canyinghao.canadapter.CanRVHeaderFooterAdapter;
import com.canyinghao.canadapter.CanSpanSizeLookup;
import com.canyinghao.canadapterdemo.R;
import com.canyinghao.canadapterdemo.model.MainBean;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by canyinghao on 16/1/21.
 */
public class RVHFGridFragment1 extends Fragment {

    RecyclerView recyclerView;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        recyclerView = new RecyclerView(getContext());

        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 4);




        final CanRVHeaderFooterAdapter<MainBean,MainBean,MainBean> adapter = new CanRVHeaderFooterAdapter<MainBean,MainBean,MainBean>(recyclerView,
                R.layout.item_image,R.layout.item_header,R.layout.item_footer) {




            @Override
            protected void setChildView(CanHolderHelper helper, int position, final MainBean model) {
                helper.setText(R.id.tv_title, model.title+" "+position);


                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(mContext,model.title,Toast.LENGTH_SHORT).show();
                    }
                });
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


        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setFooter(new MainBean("footer"));
        adapter.setHeader(new MainBean("header"));




        List<MainBean> gList = new ArrayList<>();



        for (int j = 0; j < 2000; j++) {
            gList.add(new MainBean("title"+j));
        }





        adapter.setList(gList);


        return recyclerView;
    }



}
