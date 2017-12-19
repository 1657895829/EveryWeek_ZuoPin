package app.example.third.model;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import app.example.third.bean.DeleteBean;
import app.example.third.retrofit.GetDataInterface;
import app.example.third.retrofit_rxjava_okhttp.LoggingInterceptor;
import app.example.third.retrofit_rxjava_okhttp.RetrofitUnitl;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 删除购物车model层
 */

public class DeleteCartModel {

    //1. 使用 Retrofit请求数据
    public void delete(String pid, final DeleteCartModelCallBack deleteCartModelCallBack) {
        //https://www.zhaoapi.cn/product/deleteCart?uid=1650&pid=58
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.zhaoapi.cn")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetDataInterface service = retrofit.create(GetDataInterface.class);

        Map<String,String> map = new HashMap<>();
        map.put("source","android");
        map.put("uid","1650");
        map.put("pid",pid);

        service.deleteCartCall(map).enqueue(new Callback<DeleteBean>() {
            @Override
            public void onResponse(Call<DeleteBean> call, Response<DeleteBean> response) {
                DeleteBean deleteBean = response.body();
                deleteCartModelCallBack.success(deleteBean);
            }

            @Override
            public void onFailure(Call<DeleteBean> call, Throwable t) {
                deleteCartModelCallBack.failure();
            }
        });
    }


    /**
     * 2. 使用 Retrofit结合RxJava，okhttp封装类的单例模式 请求数据
     */
    public void deleteData(String pid, final DeleteCartModelCallBack callBack){
        //使用okhttp请求,添加拦截器时把下面代码解释
        OkHttpClient ok = new OkHttpClient.Builder()
                .connectTimeout(20000, TimeUnit.SECONDS)
                .writeTimeout(20000, TimeUnit.SECONDS)
                .readTimeout(20000, TimeUnit.SECONDS)
                .addInterceptor(new LoggingInterceptor())
                .build();

        Map<String,String> map = new HashMap<>();
        map.put("source","android");
        map.put("uid","1650");
        map.put("pid",pid);

        RetrofitUnitl.getInstance("https://www.zhaoapi.cn",ok)
                .setCreate(GetDataInterface.class)
                .deleteCart(map)
                .subscribeOn(Schedulers.io())  //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread()) //最后在主线程中执行

                //进行事件的订阅，使用Consumer实现
                .subscribe(new Consumer<DeleteBean>() {
                    @Override
                    public void accept(DeleteBean deleteBean) throws Exception {
                        callBack.success(deleteBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBack.failure();
                    }
                });

    }


}
