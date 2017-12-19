package app.example.third;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import app.example.third.adapter.SelectCartAdapter;
import app.example.third.bean.SelectCartBean;
import app.example.third.presenter.SelectCartPresenter;
import app.example.third.view.SelectCartViewListener;
import butterknife.ButterKnife;
import butterknife.OnClick;

//购物车结算页面
public class ShopActivity extends AppCompatActivity implements SelectCartViewListener {
    private RecyclerView recyclerView;
    private TextView total_price;
    private TextView total_num;
    private CheckBox quanxuan;
    private TextView pay;
    private SelectCartPresenter selectCartPresenter;
    private SelectCartAdapter selectCartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        ButterKnife.bind(this);
        //https://www.zhaoapi.cn/product/getCarts?source=android&uid=1650&token=2FC3EF31EA25696D2715A971ADE38DE1
        recyclerView = (RecyclerView) findViewById(R.id.recycler_View);
        total_price = (TextView) findViewById(R.id.total_price);
        total_num = (TextView) findViewById(R.id.total_num);
        quanxuan = (CheckBox) findViewById(R.id.quanxuan);
        pay = (TextView) findViewById(R.id.pay);

        //1为不选中
        quanxuan.setTag(1);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        selectCartAdapter = new SelectCartAdapter(this);
        selectCartPresenter = new SelectCartPresenter(this);

        //调用presenter里面的请求数据的方法
        selectCartPresenter.getData();

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(selectCartAdapter);
        selectCartAdapter.notifyDataSetChanged();

        //调用recyAdapter里面的接口,设置 全选按钮 总价 总数量
        selectCartAdapter.setUpdateListener(new SelectCartAdapter.UpdateListener() {
            @Override
            public void setTotal(String total, String num, boolean allCheck) {
                //设置ui的改变
                pay.setText("去支付(" + num + ")");//总数量
                total_price.setText("" + total);//总价
                if (allCheck) {
                    quanxuan.setTag(2);
                    quanxuan.setBackgroundResource(R.drawable.shopcart_selected);
                } else {
                    quanxuan.setTag(1);
                    quanxuan.setBackgroundResource(R.drawable.shopcart_unselected);
                }
                quanxuan.setChecked(allCheck);
            }
        });

        //这里只做ui更改, 点击全选按钮,,调到adapter里面操作
        quanxuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用adapter里面的方法 ,,把当前quanxuan状态传递过去

                int tag = (int) quanxuan.getTag();
                if (tag == 1) {
                    quanxuan.setTag(2);
                    quanxuan.setBackgroundResource(R.drawable.shopcart_selected);
                } else {
                    quanxuan.setTag(1);
                    quanxuan.setBackgroundResource(R.drawable.shopcart_unselected);
                }

                selectCartAdapter.quanXuan(quanxuan.isChecked());
            }
        });
    }


    @Override
    public void success(SelectCartBean selectCartBean) {

        //将数据传给适配器
        if (selectCartBean != null) {
            selectCartAdapter.add(selectCartBean);
            selectCartAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void failure() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        selectCartPresenter.detach();
    }

    @OnClick(R.id.back_btn)
    public void onViewClicked() {
        //返回至加入购物车页面
        finish();
    }

}
