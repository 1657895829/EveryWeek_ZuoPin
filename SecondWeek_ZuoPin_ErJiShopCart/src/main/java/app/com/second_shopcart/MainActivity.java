package app.com.second_shopcart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.hjm.bottomtabbar.BottomTabBar;
import app.com.second_shopcart.fragment.Cart_Fragment;
import app.com.second_shopcart.fragment.FenLei_Fragment;
import app.com.second_shopcart.fragment.Mine_Fragment;
import app.com.second_shopcart.fragment.ShouYe_Fragment;

//Fragment页面设置页
public class MainActivity extends AppCompatActivity {
    private BottomTabBar bottomTabBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomTabBar = findViewById(R.id.bottom_tab_bar);

        //初始化Fragment
        bottomTabBar.init(getSupportFragmentManager())
                .setImgSize(50, 50)      //图片大小
                .setFontSize(12)                       //字体大小
                .setTabPadding(4, 6, 10)//选项卡的间距
                .setChangeColor(Color.RED, Color.BLUE)     //选项卡的选择颜色
                .addTabItem("首页", R.drawable.sy_n, ShouYe_Fragment.class)
                .addTabItem("分类", R.drawable.fl_n, FenLei_Fragment.class)
                .addTabItem("购物车", R.drawable.gouwuche1, Cart_Fragment.class)
                .addTabItem("我的", R.drawable.wd_n, Mine_Fragment.class)
                .isShowDivider(true)    //是否包含分割线
                .setOnTabChangeListener(new BottomTabBar.OnTabChangeListener() {
                    @Override
                    public void onTabChange(int position, String name) {
                        Log.i("TGA", "位置：" + position + "      选项卡：" + name);
                    }
                });
    }

}

