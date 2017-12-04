package com.example.zuopin.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.zuopin.R;
import com.example.zuopin.adapter.MyAdapter;
import com.example.zuopin.app.ImageLoaderUtil;
import com.example.zuopin.bean.Bean;
import com.example.zuopin.presenter.MyPresenter;
import com.example.zuopin.view.MyView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
/**
 * 首页Fragment
 */
public class ShouYe_Fragment extends Fragment implements MyView {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;
    private MyAdapter adapter;
    private MyPresenter presenter;
    private List<String> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shouye_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //创建Presenter层实例,与view层交互
        presenter = new MyPresenter(this);

        //get请求方式
        presenter.get();

        //设置布局管理器以及布局适配器
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        adapter = new MyAdapter(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onSuccess(Bean bean) {
        //获取图片数据源
        List<Bean.DataBean.DefaultGoodsListBean> goodsList = bean.getData().getDefaultGoodsList();

        //添加到新的集合中
        for (int i = 0; i < goodsList.size(); i++) {
            list.add(goodsList.get(i).getGoods_img());
        }

        //设置图片自动轮播
        banner.isAutoPlay(true);
        //将图片集合放入banner中，加载图片
        banner.setImages(list);
        //设置图片每 3 秒切换
        banner.setDelayTime(3000);
        //加载图片
        banner.setImageLoader(new ImageLoaderUtil());

        /**
         * 设置轮播图小圆点样式，不设置下面代码默认小圆点在底部居中
         * 下面代码都是小圆点在底部居中显示
         */
        //banner.setBannerStyle(Banner.ACCESSIBILITY_LIVE_REGION_ASSERTIVE);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);

        //开始执行banner轮播
        banner.start();

        //请求成功时添加数据,多条目展示
        adapter.addData(bean);
    }

    @Override
    public void onFailure(Exception e) {
        System.out.println("数据出错：" + e);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //实现presenter内部的防止内存溢出方法
    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destory();
    }
}
