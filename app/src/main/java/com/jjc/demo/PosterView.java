

package com.jjc.demo;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2015/3/28.
 */
public class PosterView extends FrameLayout {

    private Context context;
    private ViewPager viewPager;
    private LinearLayout container;

    private PosterAdapter adapter;

    private List<Integer> posterList = new ArrayList<Integer>();


    private ImageView imageView;
    // 底部小点的图片
    private ImageView[] points;

    // 记录当前选中位置
    private int currentIndex = 0;
    //    private int nextIndex;
    private int pageNum = 1;

    public PosterView(Context context) {
        this(context, null);
    }

    public PosterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.view_poster, this, true);
        initView();
    }

    private void initView() {
        container = (LinearLayout) findViewById(R.id.layout_container);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOnPageChangeListener(new viewpagerListener());

        adapter = new PosterAdapter(context, posterList);
        viewPager.setAdapter(adapter);
    }

    /**
     * 刷新海报页数据；
     *
     * @param list
     */
    public void refreshData(List<Integer> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        initPoint(list.size());

        posterList.clear();
        posterList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    private void initPoint(int num) {
        pageNum = num;
        container.removeAllViews();

        if (num == 0) {
            return;
        }

        points = new ImageView[num];
        for (int i = 0; i < num; i++) {
            imageView = new ImageView(context);
            imageView.setBackgroundResource(R.drawable.poster_point_selector);

            LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(12, 12);
            mLayoutParams.rightMargin = 8;
            mLayoutParams.leftMargin = 8;
            mLayoutParams.topMargin = 24;
            mLayoutParams.bottomMargin = 28;

            points[i] = imageView;
            // 设置位置tag，方便取出与当前位置对应
            points[i].setTag(i);
            // 默认都设为灰色
            points[i].setEnabled(false);
            // 给每个小点设置监听
            points[i].setOnClickListener(new pointListener());
            container.addView(points[i], mLayoutParams);
        }
        // 设置为白色，即选中状态
        points[currentIndex].setEnabled(true);
    }

    private class viewpagerListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            // 设置底部小点选中状态
            setCurDot(position);
        }
    }

    private class pointListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            int position = (Integer) v.getTag();
            setCurView(position);
            setCurDot(position);
        }
    }

    /**
     * 设置当前页面的位置
     */
    private void setCurView(int position) {
        if (position < 0 || position >= pageNum) {
            return;
        }
        viewPager.setCurrentItem(position);
    }

    /**
     * 设置当前的小点的位置
     */
    private void setCurDot(int positon) {
        if (positon < 0 || positon > pageNum - 1 || currentIndex == positon) {
            return;
        }
        points[positon].setEnabled(true);
        points[currentIndex].setEnabled(false);
        currentIndex = positon;
    }

}