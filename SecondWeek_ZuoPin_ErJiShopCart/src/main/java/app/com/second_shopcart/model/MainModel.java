package app.com.second_shopcart.model;

import java.util.concurrent.TimeUnit;
import app.com.second_shopcart.bean.ShopBean;
import app.com.second_shopcart.retrofit.GetDataInterface;
import app.com.second_shopcart.util_retrofit_rxjava_okhttp.LoggingInterceptor;
import app.com.second_shopcart.util_retrofit_rxjava_okhttp.RetrofitUnitl;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;

//model接口实现类
public class MainModel {

    /**
     * 使用Retrofit结合RxJava+Observable的get请求数据，使用@Query注解传递参数
     * @param callBack
     */
    public void getData(int uid,final MainModelCallBack callBack) {
        //使用okhttp请求,添加拦截器时把下面代码解释
        OkHttpClient ok = new OkHttpClient.Builder()
                .connectTimeout(20000, TimeUnit.SECONDS)
                .writeTimeout(20000,TimeUnit.SECONDS)
                .readTimeout(20000,TimeUnit.SECONDS)
                .addInterceptor(new LoggingInterceptor())
                .build();

        //使用Retrofit结合RxJava，okhttp封装类的单例模式
        RetrofitUnitl.getInstance("http://120.27.23.105",ok)
                .setCreate(GetDataInterface.class)
                .getData(97)
                .subscribeOn(Schedulers.io())               //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())  //最后在主线程中执行

                //进行事件的订阅，使用Consumer实现
                .subscribe(new Consumer<ShopBean>() {
                    @Override
                    public void accept(ShopBean bean) throws Exception {
                        //请求成功时返回数据
                        callBack.success(bean);
                        System.out.println("数据返回："+bean.toString());
                    }
                });
    }

}
