package com.jjc.demo;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Administrator on 2015/3/28.
 */
public class PosterAdapter extends PagerAdapter {

    private List<Integer> posterList;

    private LayoutInflater inflater;

    public PosterAdapter(Context context, List<Integer> posterList) {
        this.posterList = posterList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return posterList.isEmpty() ? 0 : posterList.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = container.findViewById(position);
        ViewHolder holder;

        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.item_poster, null);
            //为View设置标识符，可通过findViewById方法获取
            view.setId(position);
            holder.icon = (ImageView) view.findViewById(R.id.img_pic);
            container.addView(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.icon.setImageResource(posterList.get(position));
        return view;
    }

    static class ViewHolder {
        ImageView icon;
    }
}
