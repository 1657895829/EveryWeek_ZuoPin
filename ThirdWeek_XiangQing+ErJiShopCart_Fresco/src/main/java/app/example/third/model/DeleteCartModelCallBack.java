package app.example.third.model;

import app.example.third.bean.DeleteBean;

//删除购物车model层接口类
public interface DeleteCartModelCallBack {
    public void success(DeleteBean deleteBean);
    public void failure();
}
