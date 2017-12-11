package app.com.second_shopcart.presenter;

import app.com.second_shopcart.bean.ShopBean;
import app.com.second_shopcart.model.MainModel;
import app.com.second_shopcart.model.MainModelCallBack;
import app.com.second_shopcart.view.MainViewListener;

// Presenter层，进行view层与model数据的交互
public class MainPresenter {
    private MainViewListener listener;
    private MainModel mainModel;
    public MainPresenter(MainViewListener listener){
        this.listener = listener ;
        this.mainModel = new MainModel();
    }

    /**
     * 简单使用Retrofit结合RxJava的get请求数据, 使用@Query注解传递参数
     */
    public void getData(int uid){
        mainModel.getData(uid, new MainModelCallBack() {
            @Override
            public void success(ShopBean bean) {
                //数据交互时，为防止内存泄露，设置view层数据为空
                if (listener != null){
                    listener.success(bean);
                }
            }

            @Override
            public void failure(Exception e) {
                //数据交互时，为防止内存泄露，设置view层数据为空
                if (listener != null){
                    listener.failure(e);
                }
            }
        });
    }


    /**
     * 防止内存泄漏
     */
    public void detach(){
        listener = null;
    }

}
