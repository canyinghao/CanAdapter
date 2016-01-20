package com.canyinghao.canadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by yangjian on 15/12/16.
 */
public abstract class CanRVAdapter<T> extends RecyclerView.Adapter<CanRViewHolder> {

    protected  int mItemLayoutId;
    protected Context mContext;
    protected List<T> mList;
    protected CanOnItemListener mOnItemListener;

    protected RecyclerView mRecyclerView;
//    item项等分个数
    protected  int ratio ;

    public CanRVAdapter(RecyclerView mRecyclerView){

        this.mContext = mRecyclerView.getContext();
        this.mRecyclerView = mRecyclerView;
        this.mList = new ArrayList<>();
    }


    public CanRVAdapter(RecyclerView mRecyclerView, int itemLayoutId) {
        this(mRecyclerView);
        this.mItemLayoutId = itemLayoutId;

    }

    public CanRVAdapter(RecyclerView mRecyclerView, int itemLayoutId, List<T> mList) {
        this(mRecyclerView,itemLayoutId);
        if (mList != null && !mList.isEmpty()) {
            this.mList.addAll(mList);
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    /**
     * 获取数据
     *
     * @return
     */
    public List<T> getList() {
        return mList;
    }

    /**
     * 添加到头部
     *
     * @param datas
     */
    public void addNewList(List<T> datas) {
        if (datas != null && !datas.isEmpty()) {
            mList.addAll(0, datas);
            notifyDataSetChanged();
        }
    }

    /**
     * 添加到末尾
     *
     * @param datas
     */
    public void addMoreList(List<T> datas) {
        if (datas != null && !datas.isEmpty()) {
            mList.addAll(datas);
            notifyDataSetChanged();
        }
    }


    /**
     * 设置数据
     *
     * @param datas
     */
    public void setList(List<T> datas) {

        mList.clear();

        if (datas != null && !datas.isEmpty()) {
            mList.addAll(datas);
        }
        notifyDataSetChanged();
    }

    /**
     * 清空
     */
    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }


    /**
     * 删除指定索引数据条目
     *
     * @param position
     */
    public void removeItem(int position) {
        mList.remove(position);
        notifyDataSetChanged();
    }

    /**
     * 删除指定数据条目
     *
     * @param model
     */
    public void removeItem(T model) {
        mList.remove(model);
        notifyDataSetChanged();
    }

    /**
     * 在指定位置添加数据条目
     *
     * @param position
     * @param model
     */
    public void addItem(int position, T model) {
        mList.add(position, model);
        notifyDataSetChanged();
    }

    /**
     * 在集合头部添加数据条目
     *
     * @param model
     */
    public void addFirstItem(T model) {
        addItem(0, model);
    }

    /**
     * 在集合末尾添加数据条目
     *
     * @param model
     */
    public void addLastItem(T model) {
        addItem(mList.size(), model);
    }

    /**
     * 替换指定索引的数据条目
     *
     * @param location
     * @param newModel
     */
    public void setItem(int location, T newModel) {
        mList.set(location, newModel);
        notifyDataSetChanged();
    }

    /**
     * 替换指定数据条目
     *
     * @param oldModel
     * @param newModel
     */
    public void setItem(T oldModel, T newModel) {
        setItem(mList.indexOf(oldModel), newModel);
    }

    /**
     * 交换两个数据条目的位置
     *
     * @param fromPosition
     * @param toPosition
     */
    public void moveItem(int fromPosition, int toPosition) {
        Collections.swap(mList, fromPosition, toPosition);
        notifyDataSetChanged();
    }



    @Override
    public CanRViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        CanRViewHolder holder =    new CanRViewHolder(mRecyclerView, LayoutInflater.from(mContext).inflate(mItemLayoutId, parent, false),ratio);


        return  holder;
    }

    @Override
    public void onBindViewHolder(CanRViewHolder viewHolder, int position) {
        viewHolder.setOnItemListener(mOnItemListener);
        CanHolderHelper mHolderHelper =  viewHolder.getCanHolderHelper();

        mHolderHelper.setPosition(position);
        mHolderHelper.setOnItemListener(mOnItemListener);
        setItemListener(mHolderHelper);
        setView(mHolderHelper, position, getItem(position));
    }

    /**
     * 设置item中的子控件点击事件监听器
     *
     * @param onItemListener
     */
    public void setOnItemListener(CanOnItemListener onItemListener) {
        mOnItemListener = onItemListener;
    }


    protected abstract void setView(CanHolderHelper viewHelper, int position, T model);

    protected abstract void setItemListener(CanHolderHelper viewHelper);



}

class CanRViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

    protected Context mContext;
    protected CanHolderHelper mHolderHelper;
    protected RecyclerView mRecyclerView;
    protected  CanOnItemListener mOnItemListener;


    public CanRViewHolder(RecyclerView recyclerView, View itemView,int ratio) {
        super(itemView);

        if(ratio>0){

            ViewGroup.LayoutParams lp = itemView.getLayoutParams() == null ? new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT) : itemView.getLayoutParams();
            if (recyclerView.getLayoutManager().canScrollHorizontally()) {
                lp.width =  (recyclerView.getWidth()/ratio - recyclerView.getPaddingLeft() - recyclerView.getPaddingRight());
            } else {
                lp.height = recyclerView.getHeight()/ratio - recyclerView.getPaddingTop() - recyclerView.getPaddingBottom();
            }

            itemView.setLayoutParams(lp);
        }






        this.mRecyclerView = recyclerView;
        this. mContext = mRecyclerView.getContext();
        this.mHolderHelper =   CanHolderHelper.holderHelperByRecyclerView(itemView);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public CanHolderHelper getCanHolderHelper(){
        return  mHolderHelper;
    }

    public void setOnItemListener(CanOnItemListener onItemListener){

        this.mOnItemListener = onItemListener;

    }

    @Override
    public void onClick(View view) {

        if (mOnItemListener!=null){
            mOnItemListener.onRVItemClick(mRecyclerView,view,getAdapterPosition());
        }

    }

    @Override
    public boolean onLongClick(View view) {

        if (mOnItemListener!=null){
            return  mOnItemListener.onRVItemLongClick(mRecyclerView, view, getAdapterPosition());
        }
        return false;
    }
}
