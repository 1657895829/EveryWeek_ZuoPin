package app.example.third.retrofit;

import java.util.Map;
import app.example.third.bean.AddCartBean;
import app.example.third.bean.DeleteBean;
import app.example.third.bean.SelectCartBean;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * 网络接口数据的请求类
 */
public interface GetDataInterface {
//第一种：只使用 Retrofit请求数据

     /*
     * 加入购物车
     *  https://www.zhaoapi.cn/product/getCarts?source=android&uid=1650&token=2FC3EF31EA25696D2715A971ADE38DE1
     *  uid": 1650
     *  "token": "2FC3EF31EA25696D2715A971ADE38DE1"
     */
    @GET("product/addCart")
    Call<AddCartBean> addCartCall(@QueryMap Map<String, String> map);

    //https://www.zhaoapi.cn/product/getCarts?source=android&uid=1650&token=2FC3EF31EA25696D2715A971ADE38DE1
    //uid": 1650,
    // "token": "2FC3EF31EA25696D2715A971ADE38DE1",
    @GET("product/getCarts")
    Call<SelectCartBean> selectCartCall(@QueryMap Map<String, String> map);

    //删除
    //https://www.zhaoapi.cn/product/deleteCart?uid=1650&pid=58
    @GET("/product/deleteCart")
    Call<DeleteBean> deleteCartCall(@QueryMap Map<String, String> map);


//第二种：使用 Retrofit结合RxJava，okhttp封装类的单例模式 请求数据

    /*
    * 加入购物车
    *  https://www.zhaoapi.cn/product/getCarts?source=android&uid=1650&token=2FC3EF31EA25696D2715A971ADE38DE1
    *  uid": 1650
    *  "token": "2FC3EF31EA25696D2715A971ADE38DE1"
    */
    @GET("product/addCart")
    Observable<AddCartBean> addCart(@QueryMap Map<String, String> map);

    //https://www.zhaoapi.cn/product/getCarts?source=android&uid=1650&token=2FC3EF31EA25696D2715A971ADE38DE1
    //uid": 1650,
    // "token": "2FC3EF31EA25696D2715A971ADE38DE1",
    @GET("product/getCarts")
    Observable<SelectCartBean> selectCart(@QueryMap Map<String, String> map);

    //删除
    //https://www.zhaoapi.cn/product/deleteCart?uid=1650&pid=58
    @GET("/product/deleteCart")
    Observable<DeleteBean> deleteCart(@QueryMap Map<String, String> map);

}
