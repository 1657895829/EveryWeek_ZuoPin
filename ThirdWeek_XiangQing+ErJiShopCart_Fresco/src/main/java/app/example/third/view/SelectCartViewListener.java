package app.example.third.view;

import app.example.third.bean.SelectCartBean;
/**
 * 查询购物车 view层
 */
public interface SelectCartViewListener {
    public void success(SelectCartBean selectCartBean);
    public void failure();

}
