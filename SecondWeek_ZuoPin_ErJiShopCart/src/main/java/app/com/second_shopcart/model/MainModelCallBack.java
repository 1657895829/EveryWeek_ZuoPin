package app.com.second_shopcart.model;

import app.com.second_shopcart.bean.ShopBean;

public interface MainModelCallBack {


    public void success(ShopBean bean);
    public void failure(Exception e);

}
