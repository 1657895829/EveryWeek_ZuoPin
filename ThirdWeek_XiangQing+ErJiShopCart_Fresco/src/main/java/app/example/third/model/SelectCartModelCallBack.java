package app.example.third.model;

import app.example.third.bean.SelectCartBean;

//查询购物车model层接口类
public interface SelectCartModelCallBack {

    public void success(SelectCartBean selectCartBean);
    public void failure();

}
