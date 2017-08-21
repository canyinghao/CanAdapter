package com.canyinghao.canadapter.much;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jianyang on 2017/8/21.
 */

public class MuchBean<T extends MuchItemBean> implements Serializable {


    public MuchItemBean header;
    public MuchItemBean footer;

    public List<T> list;


}
