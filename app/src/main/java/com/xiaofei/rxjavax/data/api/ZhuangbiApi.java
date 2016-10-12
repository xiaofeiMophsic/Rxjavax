package com.xiaofei.rxjavax.data.api;

import com.xiaofei.rxjavax.model.ZhuangbiImage;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 作者：xiaofei
 * 日期：2016/10/12
 * 邮箱：paozi.xiaofei.123@gmail.com
 */

public interface ZhuangbiApi {
    @GET("search")
    Observable<List<ZhuangbiImage>> getZhuangbiImage(@Query("q") String query);
}
