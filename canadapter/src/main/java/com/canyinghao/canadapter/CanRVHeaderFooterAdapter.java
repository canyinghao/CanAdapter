package com.canyinghao.canadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
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
public abstract class CanRVHeaderFooterAdapter<C, H, F> extends RecyclerView.Adapter<CanRViewHolder> implements CanSpanSize {


    public static final int TYPE_CHILD = 0;
    public static final int TYPE_HEADER = 1;
    public static final int TYPE_FOOTER = 2;


    protected int itemChildLayoutId;
    protected int itemHeaderLayoutId;
    protected int itemFooterLayoutId;

    protected Context mContext;

    protected List<C> mChildList;


    private F footer;
    private H header;
    protected RecyclerView mRecyclerView;


    public CanRVHeaderFooterAdapter(RecyclerView mRecyclerView) {
        super();
        this.mContext = mRecyclerView.getContext();
        this.mRecyclerView = mRecyclerView;

        this.mChildList = new ArrayList<>();


    }


    public CanRVHeaderFooterAdapter(RecyclerView mRecyclerView, int itemChildLayoutId,  int itemHeaderLayoutId, int itemFooterLayoutId) {
        this(mRecyclerView);
        this.itemChildLayoutId = itemChildLayoutId;

        this.itemHeaderLayoutId = itemHeaderLayoutId;
        this.itemFooterLayoutId = itemFooterLayoutId;

    }


    public void setFooter(F footer) {
        this.footer = footer;

    }

    public F getFooter() {
        return footer;
    }

    public void setHeader(H header) {
        this.header = header;

    }

    public H getHeader() {
        return header;
    }



    /**
     * child的实际个数
     *
     *
     * @return
     */
    public int getChildItemCount() {

        if (mChildList.size() <= 0) {
            return 0;
        }
        return mChildList.size();
    }





    @Override
    public int getItemCount() {

        int count = 0;

        int headerCount = header == null ? 0 : 1;
        int footerCount = footer == null ? 0 : 1;




        count = headerCount + footerCount + getChildItemCount();


        return count;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }




    public C getChildItem( int position) {

        position = getChildPosition(position);

        return mChildList.get(position);
    }

    public int getChildPosition( int position){
        if (header != null) {

            position -= 1;
        }
        return position;
    }




    public List<C> getChildList() {
        return mChildList;
    }


    /**
     * 设置数据
     *
     * @param childData
     */
    public void setList( List<C> childData) {


        mChildList.clear();

        if (childData != null && !childData.isEmpty()) {
            mChildList.addAll(childData);
        }

        notifyDataSetChanged();
    }



    protected CanRViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(mContext).inflate(itemChildLayoutId, parent, false);

        return new CanRViewHolder(mRecyclerView, itemView).setViewType(viewType);

    }


    protected CanRViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(mContext).inflate(itemHeaderLayoutId, parent, false);

        return new CanRViewHolder(mRecyclerView, itemView).setViewType(viewType);

    }

    protected CanRViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(mContext).inflate(itemFooterLayoutId, parent, false);

        return new CanRViewHolder(mRecyclerView, itemView).setViewType(viewType);

    }


    @Override
    public int getItemViewType(int position) {
        int viewType = TYPE_CHILD;
        if (isHeaderPosition(position)) {
            viewType = TYPE_HEADER;
        } else if (isFooterPosition(position)) {
            viewType = TYPE_FOOTER;
        } else {
            viewType = TYPE_CHILD;
        }
        return viewType;
    }

    @Override
    public CanRViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        switch (viewType) {

            case TYPE_HEADER:

                return onCreateHeaderViewHolder(parent, viewType);

            case TYPE_FOOTER:

                return onCreateFooterViewHolder(parent, viewType);


        }

        return onCreateChildViewHolder(parent, viewType);

    }

    @Override
    public void onBindViewHolder(CanRViewHolder viewHolder, int position) {

        CanHolderHelper mHolderHelper = viewHolder.getCanHolderHelper();
        mHolderHelper.setPosition(position);

        int viewType = viewHolder.getViewType();

        switch (viewType) {

            case TYPE_HEADER:

                setHeaderView(mHolderHelper, position, header);
                break;

            case TYPE_FOOTER:
                setFooterView(mHolderHelper, position, footer);

                break;


            case TYPE_CHILD:

                setChildView(mHolderHelper, getChildPosition(position),getChildItem(position));


                break;


        }


    }






    public boolean isHeaderPosition(int position) {
        return header != null && position == 0;
    }


    public boolean isFooterPosition(int position) {
        int lastPosition = getItemCount() - 1;
        return footer != null && position == lastPosition;
    }






    protected abstract void setChildView(CanHolderHelper helper, int position, C bean);



    protected abstract void setHeaderView(CanHolderHelper helper, int position, H bean);

    protected abstract void setFooterView(CanHolderHelper helper, int position, F bean);


    @Override
    public  void onViewRecycled(CanRViewHolder holder) {


        int viewType = holder.getViewType();

        switch (viewType) {

            case TYPE_HEADER:

                onHeaderViewRecycled(holder);

                break;

            case TYPE_FOOTER:
                onFooterViewRecycled(holder);

                break;

            case TYPE_CHILD:


                onChildViewRecycled(holder);

                break;


        }
    }

    protected void onHeaderViewRecycled(CanRViewHolder holder) {
    }


    protected void onFooterViewRecycled(CanRViewHolder holder) {
    }

    protected void onChildViewRecycled(CanRViewHolder holder) {
    }


    @Override
    public int getSpanIndex(int position, int spanCount) {

        if(isHeaderPosition(position)||isFooterPosition(position)){
            return 0;
        }

        return getChildPosition(position)%spanCount;
    }

    @Override
    public boolean isGroupPosition(int position) {
        return false;
    }
}
