package app.example.third;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import app.example.third.adapter.ViewPagerAdapter;
import app.example.third.bean.AddCartBean;
import app.example.third.presenter.AddCartPresenter;
import app.example.third.view.AddCartViewListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//详情页面 添加购物车 https://www.zhaoapi.cn/product/addCart
public class MainActivity extends AppCompatActivity implements AddCartViewListener {
    @BindView(R.id.custom_xq_viewpager)
    ViewPager customXqViewpager;
    @BindView(R.id.custom_xq_title)
    TextView customXqTitle;
    @BindView(R.id.custom_xq_bargin_price)
    TextView customXqBarginPrice;
    @BindView(R.id.shop_title)
    TextView shop_title;
    private AddCartPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //原价设置删除线
        shop_title.setText( "我是商家1");
        customXqTitle.setText("小米(MI)Air 13.3英寸全金属轻薄笔记本(i5-7200U 8G 256G PCle SSD MX150 2G独显 FHD 指纹识别 Win10）银");
        customXqBarginPrice.setText("¥11800.0");

        //图片的集合
        List<String> listImage = new ArrayList<>();
        listImage.add("https://m.360buyimg.com//n0//jfs//t6700//155//2098998076//156185//6cf95035//595dd5a5Nc3a7dab5.jpg!q70.jpg");
        listImage.add("https://m.360buyimg.com//n0//jfs//t6130//97//1370670410//180682//1109582a//593276b1Nd81fe723.jpg!q70.jpg");
        listImage.add("https://m.360buyimg.com//n0//jfs//t5698//110//2617517836//202970//c9388feb//593276b7Nbd94ef1f.jpg");

        //设置适配器
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPagerAdapter.addData(listImage);
        customXqViewpager.setAdapter(viewPagerAdapter);

        //实例化p层
        presenter = new AddCartPresenter(this);
    }

    @Override
    public void success(AddCartBean addCartBean) {
        Toast.makeText(this, "结果：" + addCartBean.getMsg(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void failure(Exception e) {
        Toast.makeText(this, "错误:" + e, Toast.LENGTH_LONG).show();
    }


    @OnClick(R.id.jiagou_btn)
    public void onViewClicked() {
        presenter.getData("57");

        //加购成功，跳转至购物车页面
        Intent intent = new Intent(MainActivity.this, ShopActivity.class);
        startActivity(intent);
    }

    //view与presenter层解绑，防止内存泄漏
    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }

}
