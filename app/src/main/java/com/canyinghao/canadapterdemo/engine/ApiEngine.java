package com.canyinghao.canadapterdemo.engine;

import com.canyinghao.canadapterdemo.mode.NormalModel;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/9/17 上午10:19
 * 描述:
 */
public interface ApiEngine {
    @GET("normalModels.json")
    Call<List<NormalModel>> getNormalModels();
}