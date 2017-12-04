package com.example.zuopin.retrofit;

import com.example.zuopin.bean.Bean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 网络接口数据的请求类
 */

public interface GetDataInterface {
    /**
     * get请求
     * @param uri
     * @return
     */
    @GET("/umIPmfS6c83237d9c70c7c9510c9b0f97171a308d13b611")
    Call<Bean> get(@Query("uri") String uri);
}
