package app.example.third.presenter;

import app.example.third.bean.SelectCartBean;
import app.example.third.model.SelectCartModel;
import app.example.third.model.SelectCartModelCallBack;
import app.example.third.view.SelectCartViewListener;

/**
 * 查询购物车p层
 */
public class SelectCartPresenter {
    SelectCartModel selectCartModel = new SelectCartModel();

    SelectCartViewListener cartViewListener;
    public SelectCartPresenter(SelectCartViewListener cartViewListener) {
        this.cartViewListener = cartViewListener;
    }
    //调用model 层的请求数据
    public void getData(){
        selectCartModel.getNetData(new SelectCartModelCallBack() {
            @Override
            public void success(SelectCartBean selectCartBean) {
                if(cartViewListener!=null) {
                    cartViewListener.success(selectCartBean);
                }
            }

            @Override
            public void failure() {
                if(cartViewListener!=null) {
                    cartViewListener.failure();
                }
            }
        });
    }

    /**
     * 防止内存泄露
     * */
    public void detach(){
        cartViewListener = null;
    }

}
