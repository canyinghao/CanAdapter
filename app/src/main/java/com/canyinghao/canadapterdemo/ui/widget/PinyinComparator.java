package com.canyinghao.canadapterdemo.ui.widget;

import com.canyinghao.canadapterdemo.mode.IndexModel;

import java.util.Comparator;



public class PinyinComparator implements Comparator<IndexModel> {

    public int compare(IndexModel o1, IndexModel o2) {
        if (o1.topc.equals("@") || o2.topc.equals("#")) {
            return -1;
        } else if (o1.topc.equals("#") || o2.topc.equals("@")) {
            return 1;
        } else {
            return o1.topc.compareTo(o2.topc);
        }
    }

}