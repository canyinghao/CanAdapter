package com.canyinghao.canadapterdemo.engine;

import com.canyinghao.canadapterdemo.mode.ChatModel;
import com.canyinghao.canadapterdemo.mode.IndexModel;
import com.canyinghao.canadapterdemo.ui.widget.CharacterParser;
import com.canyinghao.canadapterdemo.ui.widget.PinyinComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/26 上午1:03
 * 描述:
 */
public class DataEngine {

    public static List<IndexModel> loadIndexModelDatas() {
        List<IndexModel> datas = new ArrayList<>();
        datas.add(new IndexModel("安阳"));
        datas.add(new IndexModel("鞍山"));
        datas.add(new IndexModel("保定"));
        datas.add(new IndexModel("包头"));
        datas.add(new IndexModel("北京"));
        datas.add(new IndexModel("河北"));
        datas.add(new IndexModel("北海"));
        datas.add(new IndexModel("伊春"));
        datas.add(new IndexModel("宝鸡"));
        datas.add(new IndexModel("本兮"));
        datas.add(new IndexModel("滨州"));
        datas.add(new IndexModel("常州"));
        datas.add(new IndexModel("常德"));
        datas.add(new IndexModel("常熟"));
        datas.add(new IndexModel("成都"));
        datas.add(new IndexModel("承德"));
        datas.add(new IndexModel("沧州"));
        datas.add(new IndexModel("重庆"));
        datas.add(new IndexModel("东莞"));
        datas.add(new IndexModel("扬州"));
        datas.add(new IndexModel("大庆"));
        datas.add(new IndexModel("佛山"));
        datas.add(new IndexModel("广州"));
        datas.add(new IndexModel("合肥"));
        datas.add(new IndexModel("海口"));
        datas.add(new IndexModel("济南"));
        datas.add(new IndexModel("兰州"));
        datas.add(new IndexModel("南京"));
        datas.add(new IndexModel("泉州"));
        datas.add(new IndexModel("荣成"));
        datas.add(new IndexModel("三亚"));
        datas.add(new IndexModel("上海"));
        datas.add(new IndexModel("汕头"));
        datas.add(new IndexModel("天津"));
        datas.add(new IndexModel("武汉"));
        datas.add(new IndexModel("厦门"));
        datas.add(new IndexModel("宜宾"));
        datas.add(new IndexModel("张家界"));
        datas.add(new IndexModel("自贡"));


        PinyinComparator mPinyinComparator = new PinyinComparator();
        CharacterParser mCharacterParser = CharacterParser.getInstance();
        for (IndexModel indexModel : datas) {
            indexModel.topc = mCharacterParser.getSelling(indexModel.name).substring(0, 1).toUpperCase();
            if (indexModel.name.equals("重庆")) {
                indexModel.topc = "C";
            }
        }
        Collections.sort(datas, mPinyinComparator);
        return datas;
    }

    public static List<ChatModel> loadChatModelDatas() {
        List<ChatModel> datas = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            if (i % 3 == 0) {
                datas.add(new ChatModel("消息" + i, ChatModel.UserType.To));
            } else {
                datas.add(new ChatModel("消息" + i, ChatModel.UserType.From));
            }
        }
        return datas;
    }

}