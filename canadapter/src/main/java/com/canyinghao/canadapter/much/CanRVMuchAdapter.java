package com.canyinghao.canadapter.much;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.canyinghao.canadapter.CanHolderHelper;
import com.canyinghao.canadapter.CanRViewHolder;
import com.canyinghao.canadapter.CanSpanSize;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Copyright 2016 canyinghao
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public abstract class CanRVMuchAdapter<T extends MuchItemBean> extends RecyclerView.Adapter<CanRViewHolder> implements CanSpanSize {


    protected Context mContext;

    protected List<MuchBean<T>> mChildList;


    protected SparseArray<ErvType> ervTypes;


    protected RecyclerView mRecyclerView;


    public CanRVMuchAdapter(RecyclerView mRecyclerView) {
        super();
        this.mContext = mRecyclerView.getContext();
        this.mRecyclerView = mRecyclerView;
        this.mChildList = new ArrayList<>();
        this.ervTypes = new SparseArray<>();

    }

    public void resetStatus() {

        ervTypes.clear();

    }


    /**
     * child的实际个数
     *
     * @param group int
     * @return int
     */
    public int getChildItemCount(int group) {

        if (mChildList.size() <= group) {
            return 0;
        }

        MuchBean muchBean = mChildList.get(group);

        if(muchBean==null){
            return 0;
        }

        int count = 0;

        count += muchBean.header != null ? 1 : 0;
        count += muchBean.footer != null ? 1 : 0;
        count += muchBean.list != null ? muchBean.list.size() : 0;

        return count;
    }


    /**
     * group的个数
     *
     * @return int
     */
    public int getGroupItemCount() {
        if (mChildList.isEmpty()) {
            return 0;
        }

        return mChildList.size();


    }


    @Override
    public int getItemCount() {

        int count = 0;


        int groupCount = getGroupItemCount();


        if (groupCount == 0) {


            return 0;


        } else {

            for (int i = 0; i < groupCount; i++) {

                count += getChildItemCount(i);
            }

        }


        return count;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public MuchBean<T> getGroupItem(int position) {
        return mChildList.get(position);
    }

    public T getChildItem(int group, int position) {
        MuchBean<T> muchBean = getGroupItem(group);
        if (muchBean != null && muchBean.list != null) {

            return muchBean.list.get(position);
        }
        return null;
    }




    public void setList(List<MuchBean<T>> childData) {


        mChildList.clear();

        if (childData != null && !childData.isEmpty()) {
            mChildList.addAll(childData);
        }
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        ErvType ervType = ervTypes.get(position);

        if (ervType == null) {
            ervType = getItemErvType(position);
            ervTypes.put(position, ervType);
        }
        return ervType.type;
    }


    @Override
    public CanRViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        return onCreateChildViewHolder(parent, viewType);

    }


    protected CanRViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {

        try{
            View itemView = LayoutInflater.from(mContext).inflate(viewType, parent, false);
            return new CanRViewHolder(mRecyclerView, itemView).setViewType(viewType);
        }catch (Throwable e){

        }
        return null;



    }

    @Override
    public void onBindViewHolder(CanRViewHolder viewHolder, int position) {
        if(viewHolder==null){
            return;
        }
        try{
            CanHolderHelper mHolderHelper = viewHolder.getCanHolderHelper();
            mHolderHelper.setPosition(position);

            int viewType = viewHolder.getViewType();
            ErvType ervType = ervTypes.get(position);

            if (ervType == null) {
                ervType = getItemErvType(position);
                ervTypes.put(position, ervType);
            }

            setView(mHolderHelper, ervType.group, ervType.position, viewType, ervType.itemBean);
        }catch (Throwable e){
            e.printStackTrace();
        }


    }


    protected abstract void setView(CanHolderHelper helper, int group, int position, int viewType, MuchItemBean bean);


    /**
     * 计算正确的group和position
     *
     * @param i position
     * @return 一个计算好的group和position
     */
    public ErvType getItemErvType(int i) {

        for (int group = 0; group < getGroupItemCount(); group++) {


            if (i >= 0) {

                MuchBean<T> muchBean = getGroupItem(group);
                if (muchBean.header != null) {
                    if (i == 0) {

                        return new ErvType(muchBean.header.layoutId, group, i, muchBean.header).setHeaderOrFooter();

                    } else {
                        i -= 1;
                    }

                }


                if (i < muchBean.list.size()) {

                    MuchItemBean itemBean = muchBean.list.get(i);

                    return new ErvType(itemBean.layoutId, group, i, itemBean);
                } else {

                    i -= muchBean.list.size();
                }

                if (muchBean.footer != null) {
                    if (i == 0) {

                        return new ErvType(muchBean.footer.layoutId, group, i, muchBean.footer).setHeaderOrFooter();

                    } else {
                        i -= 1;
                    }

                }


            }


        }


        return new ErvType(0, 0, i, null);
    }

    @Override
    public boolean isHeaderPosition(int position) {
        return false;
    }

    @Override
    public boolean isFooterPosition(int position) {
        return false;
    }

    @Override
    public boolean isGroupPosition(int position) {

        ErvType ervType = ervTypes.get(position);

        if (ervType == null) {
            ervType = getItemErvType(position);
            ervTypes.put(position, ervType);
        }

        return ervType.isHeaderOrFooter;
    }


    public int getSpanIndex(int position, int spanCount) {

        ErvType ervType = ervTypes.get(position);

        if (ervType == null) {
            ervType = getItemErvType(position);
            ervTypes.put(position, ervType);
        }

        if (ervType.isHeaderOrFooter) {

            return 0;
        }

        int group = ervType.group;
        int tempPosition = ervType.position;

        MuchBean<T> bean = getGroupItem(group);


//        上一次计算到的index
        int count = 0;
        //上一次还剩余多少空间
        int lastRemain = 0;


        for (int i = 0; i <= tempPosition; i++) {
            int spanSize = bean.list.get(i).spanSize;
            if (i == 0) {

                lastRemain = spanCount - spanSize;

            } else {

                if (lastRemain <= 0) {


                    if (spanSize < spanCount) {
                        count = 0;
                        lastRemain = spanCount - spanSize;


                    } else {
                        count = 0;
                        lastRemain = 0;


                    }


                } else {

                    if (spanSize <= lastRemain) {

                        count += 1;


                        lastRemain = lastRemain - spanSize;


                    } else {

                        count = 0;
                        lastRemain = 0;


                    }


                }


            }


        }


        return count;

    }


    @Override
    public int getSpanSize(int position) {

        ErvType ervType = ervTypes.get(position);

        if (ervType == null) {
            ervType = getItemErvType(position);
            ervTypes.put(position, ervType);
        }
        if(ervType.itemBean==null){
            return 1;
        }
        return ervType.itemBean.spanSize;
    }


    /**
     * 获取数据
     *
     * @return List
     */
    public List<MuchBean<T>> getList() {
        return mChildList;
    }

    /**
     * 添加到头部
     *
     * @param datas List
     */
    public void addNewList(List<MuchBean<T>> datas) {
        if (datas != null && !datas.isEmpty()) {
            mChildList.addAll(0, datas);
            notifyDataSetChanged();
        }
    }

    /**
     * 添加到末尾
     *
     * @param datas List
     */
    public void addMoreList(List<MuchBean<T>> datas) {
        if (datas != null && !datas.isEmpty()) {
            mChildList.addAll(datas);
            notifyDataSetChanged();
        }
    }


    /**
     * 清空
     */
    public void clear() {
        mChildList.clear();
        notifyDataSetChanged();
    }


    /**
     * 删除指定索引数据条目
     *
     * @param position position
     */
    public void removeItem(int position) {
        if (isSafePosition(position)) {
            mChildList.remove(position);
            notifyItemRangeRemoved(position, 1);
        }

    }

    /**
     * 删除指定数据条目
     *
     * @param model T
     */
    public void removeItem(MuchBean<T> model) {

        if (mChildList != null && mChildList.contains(model)) {
            int index = mChildList.indexOf(model);
            if (isSafePosition(index)) {
                removeItem(index);
            }
        }


    }

    /**
     * 在指定位置添加数据条目
     *
     * @param position int
     * @param model    T
     */
    public void addItem(int position, MuchBean<T> model) {

        if (position >= 0 && position <= mChildList.size()) {
            mChildList.add(position, model);
            notifyItemInserted(position);
        }


    }

    /**
     * 在集合头部添加数据条目
     *
     * @param model T
     */
    public void addFirstItem(MuchBean<T> model) {
        addItem(0, model);
    }

    /**
     * 在集合末尾添加数据条目
     *
     * @param model T
     */
    public void addLastItem(MuchBean<T> model) {
        addItem(mChildList.size(), model);
    }

    /**
     * 替换指定索引的数据条目
     *
     * @param location int
     * @param newModel T
     */
    public void setItem(int location, MuchBean<T> newModel) {

        if (isSafePosition(location)) {
            mChildList.set(location, newModel);

            notifyItemChanged(location);
        }

    }

    /**
     * 替换指定数据条目
     *
     * @param oldModel T
     * @param newModel T
     */
    public void setItem(MuchBean<T> oldModel, MuchBean<T> newModel) {
        setItem(mChildList.indexOf(oldModel), newModel);
    }

    /**
     * 交换两个数据条目的位置
     *
     * @param fromPosition int
     * @param toPosition   int
     */
    public void moveItem(int fromPosition, int toPosition) {

        if (isSafePosition(fromPosition) && isSafePosition(toPosition)) {
            Collections.swap(mChildList, fromPosition, toPosition);
            notifyItemMoved(fromPosition, toPosition);
        }

    }

    private boolean isSafePosition(int position) {


        return mChildList != null && position >= 0 && position < mChildList.size();

    }



    public static class ErvType {

        public int type;
        public int group;
        public int position;
        public MuchItemBean itemBean;
        public boolean isHeaderOrFooter;


        public ErvType(int type, int group, int position, MuchItemBean itemBean) {
            this.type = type;
            this.group = group;
            this.position = position;
            this.itemBean = itemBean;
        }

        public ErvType setHeaderOrFooter() {
            isHeaderOrFooter = true;
            return this;
        }
    }

}
