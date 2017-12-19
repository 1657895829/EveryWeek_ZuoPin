package app.example.third.presenter;

import app.example.third.bean.AddCartBean;
import app.example.third.model.AddCartModel;
import app.example.third.model.AddCartModelCallBack;
import app.example.third.view.AddCartViewListener;

/**
 * 加入购物车 p 层
 */
public class AddCartPresenter {
    private AddCartViewListener viewListener;
    private AddCartModel model;

    public AddCartPresenter(AddCartViewListener viewListener) {
        this.viewListener = viewListener;
        this.model = new AddCartModel();
    }

    public void getData(String pid){
        model.getNetData(pid, new AddCartModelCallBack() {
            @Override
            public void success(AddCartBean addCartBean) {
                viewListener.success(addCartBean);
            }

            @Override
            public void failure(Exception e) {
                viewListener.failure(e);
            }
        });
    }

    /**
     * 防止内存泄漏
     */
    public void detach(){
        viewListener = null;
    }

}
