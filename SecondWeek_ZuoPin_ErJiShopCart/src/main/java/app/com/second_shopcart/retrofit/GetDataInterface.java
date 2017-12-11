package app.com.second_shopcart.retrofit;

import app.com.second_shopcart.bean.ShopBean;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 网络接口数据的请求类
 */
public interface GetDataInterface {
    /*
     * 使用Observable被观察者模式下的请求数据 http://120.27.23.105/product/getCarts?uid=100
     */
    @GET("/product/getCarts")
    Observable<ShopBean> getData(@Query("uid") int uid);
}
