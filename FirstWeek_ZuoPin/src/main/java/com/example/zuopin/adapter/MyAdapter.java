package com.example.zuopin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.zuopin.R;
import com.example.zuopin.bean.Bean;
import com.facebook.drawee.view.SimpleDraweeView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * //RecyclerView展示数据适配器
 */
public class MyAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Bean.DataBean.SubjectsBean> list;

    public MyAdapter(Context context) {
        this.context = context;
    }

    //声明数据来源，添加数据
    public void addData(Bean bean) {
        if (this.list == null) {
            this.list = new ArrayList<>();
        }
        this.list.addAll(bean.getData().getSubjects());
        notifyDataSetChanged();
    }

    //创建条目布局
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view01 = LayoutInflater.from(context).inflate(R.layout.adapter_01, parent, false);
            return new MyViewHolder01(view01);
        } else {
            View view02 = LayoutInflater.from(context).inflate(R.layout.adapter_02, parent, false);
            return new MyViewHolder02(view02);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //判断加载布局的类型
        if (holder instanceof MyViewHolder01) {
            MyViewHolder01 holder01 = (MyViewHolder01) holder;
            holder01.simpleDraweeView.setImageURI(list.get(position).getDescImage());
        } else if (holder instanceof MyViewHolder02) {
            MyViewHolder02 holder02 = (MyViewHolder02) holder;
            holder02.draweeView.setImageURI(list.get(position).getDescImage());
            holder02.title.setText(list.get(position).getTitle());
        }
    }

    @Override
    public int getItemViewType(int position) {
        //判断布局类型，加载不同数据
        if (position % 2 == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class MyViewHolder01 extends RecyclerView.ViewHolder {
        @BindView(R.id.simpleDraweeView)
        SimpleDraweeView simpleDraweeView;
        public MyViewHolder01(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    static class MyViewHolder02 extends RecyclerView.ViewHolder {
        @BindView(R.id.draweeView)
        SimpleDraweeView draweeView;
        @BindView(R.id.title)
        TextView title;

        public MyViewHolder02(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
