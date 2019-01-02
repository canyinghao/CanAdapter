package com.canyinghao.canadapterdemo.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import com.canyinghao.canadapter.CanErvAdapter;
import com.canyinghao.canadapter.CanHolderHelper;
import com.canyinghao.canadapterdemo.R;
import com.canyinghao.canadapterdemo.model.MainBean;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by canyinghao on 16/1/21.
 */
public class ERVGridFragment extends Fragment {

    RecyclerView recyclerView;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        recyclerView = new RecyclerView(getContext());

        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(mLayoutManager);


//        ItemDragHelperCallback callback = new ItemDragHelperCallback();
//        final ItemTouchHelper helper = new ItemTouchHelper(callback);
//        helper.attachToRecyclerView(recyclerView);

        final CanErvAdapter<MainBean,MainBean> adapter = new CanErvAdapter<MainBean,MainBean>(recyclerView, R.layout.item_group,R.layout.item_image) {



            Runnable notify =  new Runnable() {
                @Override
                public void run() {
                    notifyDataSetChanged();
                }
            };


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

                helper.setText(R.id.tv_group, model.title);
               final View arrow =  helper.getView(R.id.iv_arrow);

                if (model.isExp){

                    ViewHelper.setRotation(arrow,180);
                }else{

                    ViewHelper.setRotation(arrow,0);
                }



                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        model.isExp =   toggle(helper,groupPosition);


                    }
                });


            }

            @Override
            protected void onSingleExpand(CanHolderHelper groupHelper, int group) {
                super.onSingleExpand(groupHelper,group);
                expandView(groupHelper.getView(R.id.iv_arrow));
                getGroupItem(group).isExp = true;

                recyclerView.removeCallbacks(notify);
                recyclerView.postDelayed(notify,1000);

                Log.e("TAG","onExpand");
            }


            @Override
            protected void onSingleCollapse(CanHolderHelper groupHelper, int group) {
                super.onSingleCollapse(groupHelper, group);

                collapseView(groupHelper.getView(R.id.iv_arrow));
                getGroupItem(group).isExp = false;


                Log.e("TAG","Collapse"+group);
            }
        };

        recyclerView.setAdapter(adapter);

        adapter.setSingleExpand(false);

//        adapter.setCannotCollapse(true);
        List<MainBean> gList = new ArrayList<>();
        List<List<MainBean>>  cList = new ArrayList<>();

        for (int i = 0; i < 50; i++) {

            gList.add(new MainBean("2016.05.0"+(i+1)));


            List<MainBean> childeList =new ArrayList<>();


            for (int j = 0; j < 8; j++) {
                childeList.add(new MainBean("title"+j));
            }

            cList.add(childeList);
        }





        adapter.setList(gList,cList);


        return recyclerView;
    }




    public void expandView(final View view) {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {


                ViewHelper.setRotation(view,180 * (float) (animation.getAnimatedValue()));


                view.postInvalidate();
            }
        });
        animator.start();

    }

    public void collapseView(final View view) {
        ValueAnimator animator = ValueAnimator.ofFloat(1, 0);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {



                ViewHelper.setRotation(view,180 * (float) (animation.getAnimatedValue()));

                view.postInvalidate();
            }
        });
        animator.start();

    }
}
