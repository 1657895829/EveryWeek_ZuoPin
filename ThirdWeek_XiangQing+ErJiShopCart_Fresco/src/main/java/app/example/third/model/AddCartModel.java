package app.example.third.model;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import app.example.third.bean.AddCartBean;
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
 * 加入购物车的model层
 */
public class AddCartModel {
    /**
     * 1. 使用 Retrofit请求数据
     * https://www.zhaoapi.cn/product/addCart
     * "uid": 1650
     * "token": "2FC3EF31EA25696D2715A971ADE38DE1"
     * "pid":57
     * @param pid
     * @param callBack
     */
    public void getData(String pid, final AddCartModelCallBack callBack){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.zhaoapi.cn")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetDataInterface request = retrofit.create(GetDataInterface.class);
        Map<String,String> map = new HashMap<>();
        map.put("source","android");
        map.put("uid","1650");
        map.put("token","2FC3EF31EA25696D2715A971ADE38DE1");
        map.put("pid",pid);
        request.addCartCall(map).enqueue(new Callback<AddCartBean>() {
            @Override
            public void onResponse(Call<AddCartBean> call, Response<AddCartBean> response) {
                AddCartBean addCartBean = response.body();
                callBack.success(addCartBean);
            }

            @Override
            public void onFailure(Call<AddCartBean> call, Throwable t) {
                callBack.failure(new Exception());
            }
        });
    }

    /**
     * 2. 使用 Retrofit结合RxJava，okhttp封装类的单例模式 请求数据
     * https://www.zhaoapi.cn/product/addCart
     * "uid": 1650
     * "token": "2FC3EF31EA25696D2715A971ADE38DE1"
     * "pid":57
     * @param pid
     * @param callBack
     */
    public void getNetData(String pid, final AddCartModelCallBack callBack){
        //使用okhttp请求,添加拦截器时把下面代码解释
        OkHttpClient ok = new OkHttpClient.Builder()
                .connectTimeout(20000, TimeUnit.SECONDS)
                .writeTimeout(20000,TimeUnit.SECONDS)
                .readTimeout(20000,TimeUnit.SECONDS)
                .addInterceptor(new LoggingInterceptor())
                .build();

        Map<String,String> map = new HashMap<>();
        map.put("source","android");
        map.put("uid","1650");
        map.put("token","2FC3EF31EA25696D2715A971ADE38DE1");
        map.put("pid",pid);

        RetrofitUnitl.getInstance("https://www.zhaoapi.cn",ok)
                .setCreate(GetDataInterface.class)
                .addCart(map)
                .subscribeOn(Schedulers.io())  //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread()) //最后在主线程中执行

        //进行事件的订阅，使用Consumer实现
        .subscribe(new Consumer<AddCartBean>() {
            @Override
            public void accept(AddCartBean addCartBean) throws Exception {
                callBack.success(addCartBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callBack.failure((Exception) throwable);
            }
        });

    }


}
