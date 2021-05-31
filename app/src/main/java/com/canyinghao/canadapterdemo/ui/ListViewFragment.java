package com.canyinghao.canadapterdemo.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.canyinghao.canadapter.CanHolderHelper;
import com.canyinghao.canadapter.CanSpanSizeLookup;
import com.canyinghao.canadapter.much.CanRVMuchAdapter;
import com.canyinghao.canadapter.much.MuchBean;
import com.canyinghao.canadapter.much.MuchItemBean;
import com.canyinghao.canadapterdemo.R;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by canyinghao on 16/1/21.
 */
public class ListViewFragment extends Fragment {
    RecyclerView listView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        listView = new RecyclerView(getContext());



        final CanRVMuchAdapter adapter = new CanRVMuchAdapter(listView) {


            @Override
            protected void setView(CanHolderHelper helper, int group, int position, int viewType, MuchItemBean bean) {

            }

            @Override
            public boolean isHeaderPosition(int position) {
                return false;
            }

            @Override
            public boolean isFooterPosition(int position) {
                return false;
            }




        };


        listView.setHasFixedSize(true);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 6);

        CanSpanSizeLookup headerSpanSizeLookup = new CanSpanSizeLookup(adapter, mLayoutManager.getSpanCount());
        mLayoutManager.setSpanSizeLookup(headerSpanSizeLookup);
        listView.setLayoutManager(mLayoutManager);


        listView.setAdapter(adapter);


        List<MuchBean>  allList = new ArrayList<>();

        for(int i=0;i<5;i++){

            MuchBean muchBean =  new MuchBean();

            MuchItemBean header = new MuchItemBean();

            header.spanSize =  6;
            header.layoutId = R.layout.item_much_header;


            MuchItemBean footer = new MuchItemBean();

            footer.spanSize =  6;
            footer.layoutId = R.layout.item_much_footer;


            List<MuchItemBean>  list = new ArrayList<>();

            switch (i){

                case 0:
                    for(int j=0;j<5;j++){

                        MuchItemBean item = new MuchItemBean();

                        item.spanSize =  1;
                        item.layoutId = R.layout.item_much_child_1;

                        list.add(item);

                    }

                    break;


                case 1:

                    for(int j=0;j<4;j++){

                        MuchItemBean item = new MuchItemBean();

                        item.spanSize =  3;
                        item.layoutId = R.layout.item_much_child_2;

                        list.add(item);

                    }

                    break;



                case 2:


                    MuchItemBean item1 = new MuchItemBean();

                    item1.spanSize =  6;
                    item1.layoutId = R.layout.item_much_child_1;

                    list.add(item1);


                    MuchItemBean item2 = new MuchItemBean();

                    item2.spanSize =  2;
                    item2.layoutId = R.layout.item_much_child_3;

                    list.add(item2);

                    MuchItemBean item3 = new MuchItemBean();

                    item3.spanSize =  6;
                    item3.layoutId = R.layout.item_much_child_3;

                    list.add(item3);

                    break;


                default:
                    for(int j=0;j<4;j++){

                        MuchItemBean item = new MuchItemBean();

                        item.spanSize =  2;
                        item.layoutId = R.layout.item_much_child_3;

                        list.add(item);

                    }

                    break;



            }


            muchBean.header = header;
            muchBean.footer = footer;
            muchBean.list = list;

            allList.add(muchBean);

        }

        adapter.setList(allList);


        return listView;
    }


}
