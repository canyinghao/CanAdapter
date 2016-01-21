package com.canyinghao.canadapterdemo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangjian on 16/1/21.
 */
public class MainBean {


    public String title ;
    public String content ;


    public static List<MainBean> getList(){

        List<MainBean>  list = new ArrayList<>();


        for (int i = 0; i < 50; i++) {

            MainBean  bean =   new MainBean();

            bean.title = "this is a canadapter title "+i;
            bean.content = "this is a canadapter content "+i;

            list.add(bean);

        }

        return  list;

    }
}
