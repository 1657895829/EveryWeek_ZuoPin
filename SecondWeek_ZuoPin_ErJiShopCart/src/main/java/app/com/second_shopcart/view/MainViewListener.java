package app.com.second_shopcart.view;

import app.com.second_shopcart.bean.ShopBean;

public interface MainViewListener {
    public void success(ShopBean bean);
    public void failure(Exception e);

}
