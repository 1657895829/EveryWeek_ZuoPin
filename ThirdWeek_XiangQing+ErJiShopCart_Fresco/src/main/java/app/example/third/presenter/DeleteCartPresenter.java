package app.example.third.presenter;

import app.example.third.bean.DeleteBean;
import app.example.third.model.DeleteCartModel;
import app.example.third.model.DeleteCartModelCallBack;
import app.example.third.view.DeleteCartListener;

/**
 * 删除购物车p层
 */
public class DeleteCartPresenter {

    DeleteCartModel deleteCartModel = new DeleteCartModel();
    DeleteCartListener deleteCartListener;
    public DeleteCartPresenter(DeleteCartListener deleteCartListener) {
        this.deleteCartListener = deleteCartListener;
    }

    //删除购物车方法
    public void delete(String pid) {
        deleteCartModel.deleteData(pid, new DeleteCartModelCallBack() {
            @Override
            public void success(DeleteBean deleteBean) {
                deleteCartListener.success(deleteBean);
            }

            @Override
            public void failure() {
                deleteCartListener.failure();
            }
        });
    }

    /**
     * 防止内存泄漏
     */
    public void detach(){
        deleteCartListener = null;
    }

}
