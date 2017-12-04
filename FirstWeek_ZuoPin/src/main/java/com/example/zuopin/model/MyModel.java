package com.example.zuopin.model;

import com.example.zuopin.app.MyApplication;
import com.example.zuopin.bean.Bean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * model接口实现类
 */
public class MyModel {
    /**
     * get请求
     * @param callBack
     */
    public void getData(final ModelCallBack callBack){
        //设置接口请求的key值
        Call<Bean> call = MyApplication.request.get("homepage");

        //发起异步请求
        call.enqueue(new Callback<Bean>() {
            @Override
            public void onResponse(Call<Bean> call, Response<Bean> response) {
                //获取响应的数据
                Bean bean = response.body();

                //请求成功时返回数据
                callBack.onSuccess(bean);
            }

            @Override
            public void onFailure(Call<Bean> call, Throwable t) {
                //请求失败时返回数据
                callBack.onFailure(new Exception(""));
            }
        });
    }
}
