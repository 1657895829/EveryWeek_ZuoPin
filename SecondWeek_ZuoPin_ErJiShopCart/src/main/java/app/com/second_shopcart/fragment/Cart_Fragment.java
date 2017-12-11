package app.com.second_shopcart.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import app.com.second_shopcart.R;
import app.com.second_shopcart.ShopAdapter;
import app.com.second_shopcart.bean.ShopBean;
import app.com.second_shopcart.presenter.MainPresenter;
import app.com.second_shopcart.view.MainViewListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 购物车Fragment
 */
public class Cart_Fragment extends Fragment implements MainViewListener {
    @BindView(R.id.third_recyclerview)
    RecyclerView thirdRecyclerview;
    @BindView(R.id.third_allselect)
    CheckBox checkBoxAll;
    @BindView(R.id.third_totalprice)
    TextView thirdTotalprice;
    Unbinder unbinder;
    private MainPresenter presenter;
    private ShopAdapter adapter;
    private List<ShopBean.DataBean> data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cart_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new MainPresenter(this);
        presenter.getData(100);

        adapter = new ShopAdapter(getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        thirdRecyclerview.setLayoutManager(manager);
        thirdRecyclerview.setAdapter(adapter);

        adapter.setListener(new ShopAdapter.UpdateUiListener() {
            @Override
            public void setTotal(String total, String num, boolean allCheck) {
                checkBoxAll.setChecked(allCheck);
                thirdTotalprice.setText("￥ " + total);
            }
        });
        adapter.setListener(new ShopAdapter.DeleteUiListener() {
            @Override
            public void delete(int position, View view, String num, boolean allCheck) {
                data.get(position).getList().remove(position);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void success(ShopBean bean) {
        data = bean.getData();
        adapter.add(bean);
    }

    @Override
    public void failure(Exception e) {
        System.out.println("错误：" + e);
    }


    @OnClick({R.id.third_allselect, R.id.third_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.third_allselect:
                adapter.selectAll(checkBoxAll.isChecked());
                break;
            case R.id.third_submit:
                Toast.makeText(getActivity(),"您需支付金额："+thirdTotalprice.getText().toString()+" 元",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
