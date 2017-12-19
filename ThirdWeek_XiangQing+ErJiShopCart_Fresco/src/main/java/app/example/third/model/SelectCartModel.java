package app.example.third.model;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import app.example.third.bean.SelectCartBean;
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
 * 查询购物车model层
 */
public class SelectCartModel {

    //1. 使用 Retrofit请求数据
    public void getData(final SelectCartModelCallBack selectCartModelCallBack) {
        //https://www.zhaoapi.cn/product/getCarts?source=android&uid=1650&token=2FC3EF31EA25696D2715A971ADE38DE1
        // https://www.zhaoapi.cn/product/addCart
        //"uid": 1650,
        // "token": "2FC3EF31EA25696D2715A971ADE38DE1",
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.zhaoapi.cn")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetDataInterface service = retrofit.create(GetDataInterface.class);

        Map<String,String> map = new HashMap<>();
        map.put("source","android");
        map.put("uid","1650");
        map.put("token","2FC3EF31EA25696D2715A971ADE38DE1");

        service.selectCartCall(map).enqueue(new Callback<SelectCartBean>() {
            @Override
            public void onResponse(Call<SelectCartBean> call, Response<SelectCartBean> response) {
                SelectCartBean selectCartBean = response.body();
                selectCartModelCallBack.success(selectCartBean);
            }

            @Override
            public void onFailure(Call<SelectCartBean> call, Throwable t) {
                selectCartModelCallBack.failure();
            }
        });
    }

    /**
     * 2. 使用 Retrofit结合RxJava，okhttp封装类的单例模式 请求数据
     */
    public void getNetData(final SelectCartModelCallBack callBack){
        //使用okhttp请求,添加拦截器时把下面代码解释
        OkHttpClient ok = new OkHttpClient.Builder()
                .connectTimeout(20000, TimeUnit.SECONDS)
                .writeTimeout(20000,TimeUnit.SECONDS)
                .readTimeout(20000, TimeUnit.SECONDS)
                .addInterceptor(new LoggingInterceptor())
                .build();

        Map<String,String> map = new HashMap<>();
        map.put("source","android");
        map.put("uid","1650");
        map.put("token","2FC3EF31EA25696D2715A971ADE38DE1");

        RetrofitUnitl.getInstance("https://www.zhaoapi.cn",ok)
                .setCreate(GetDataInterface.class)
                .selectCart(map)
                .subscribeOn(Schedulers.io())  //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread()) //最后在主线程中执行

                //进行事件的订阅，使用Consumer实现
                .subscribe(new Consumer<SelectCartBean>() {
                    @Override
                    public void accept(SelectCartBean selectCartBean) throws Exception {
                        callBack.success(selectCartBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBack.failure();
                    }
                });

    }

}
