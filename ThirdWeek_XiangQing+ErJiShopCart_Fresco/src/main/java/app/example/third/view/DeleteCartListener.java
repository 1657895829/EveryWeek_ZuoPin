package app.example.third.view;


import app.example.third.bean.DeleteBean;
/**
 * 删除购物车 view层
 */
public interface DeleteCartListener {
    public void success(DeleteBean deleteBean);
    public void failure();
}
