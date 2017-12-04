package com.example.zuopin.presenter;

import com.example.zuopin.bean.Bean;
import com.example.zuopin.model.ModelCallBack;
import com.example.zuopin.model.MyModel;
import com.example.zuopin.view.MyView;

/**
 * Presenter层，进行view层与model数据的交互
 */
public class MyPresenter {
    private MyView myView;
    private MyModel myModel;

    public MyPresenter(MyView myView) {
        this.myView = myView;
        this.myModel = new MyModel();
    }

    /**
     * get请求数据交互
     */
    public void get(){
        myModel.getData(new ModelCallBack() {
            @Override
            public void onSuccess(Bean bean) {
                //数据交互时，为防止内存泄露，设置view层数据为空
                if (myView != null){
                    myView.onSuccess(bean);
                }
            }

            @Override
            public void onFailure(Exception e) {
                //数据交互时，为防止内存泄露，设置view层数据为空
                if (myView != null){
                    myView.onFailure(e);
                }
            }
        });
    }

    //Presenter与View层，取消绑定，用来防止内存溢出
    public void destory(){
        this.myView = null;
    }

}
