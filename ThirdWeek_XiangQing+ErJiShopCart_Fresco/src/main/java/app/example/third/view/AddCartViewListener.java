package app.example.third.view;

import app.example.third.bean.AddCartBean;

/**
 * 加入购物车 view层
 */
public interface AddCartViewListener {
    public void success(AddCartBean addCartBean);
    public void failure(Exception e);
}
