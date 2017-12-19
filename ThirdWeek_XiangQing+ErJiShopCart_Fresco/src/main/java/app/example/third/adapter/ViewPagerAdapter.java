package app.example.third.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * 详情页面商品图片适配器
 */
public class ViewPagerAdapter extends PagerAdapter{
    List<String> list ;
    Context context;
    public ViewPagerAdapter(Context context) {
        this.context = context;
    }

    //添加数据的方法
    public void addData(List<String> listImage) {
        if(list ==null){
            list = new ArrayList<>();
        }
        list.addAll(listImage);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list == null ? 0:list.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //添加视图
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        ImageLoader.getInstance().displayImage(list.get(position),imageView);
        container.addView(imageView);

        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
