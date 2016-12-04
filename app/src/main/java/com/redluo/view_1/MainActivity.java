package com.redluo.view_1;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.redluo.view_1.view.ClipViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ClipViewPager viewPager;
    private int imageIds[] = {R.mipmap.ig1, R.mipmap.ig2, R.mipmap.ig3, R.mipmap.ig5, R.mipmap.ig4};
    private List<View> viewList = new ArrayList<>();


    public static final float MAX_SCALE = 1.2f;
    public static final float MIN_SCALE = 0.6f;
    private RelativeLayout viewPagerContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        viewPager = (ClipViewPager) findViewById(R.id.view_pager);
        viewPagerContainer = (RelativeLayout) findViewById(R.id.activity_main);
        for (int i = 0; i < imageIds.length; i++) {
            View view = View.inflate(this, R.layout.pager_item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
            imageView.setImageResource(imageIds[i]);
            viewList.add(view);

        }

        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return viewList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(viewList.get(position));
                return viewList.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {

                container.removeView(viewList.get(position));
            }

        });

        viewPager.setOffscreenPageLimit(viewList.size());
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (viewPagerContainer != null) {
                    viewPagerContainer.invalidate();
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {

                if (position < -1) {
                    position = -1;
                } else if (position > 1) {
                    position = 1;
                }

                float tempScale = position < 0 ? 1 + position : 1 - position;

                float slope = (MAX_SCALE - MIN_SCALE) / 1;
                //一个公式
                float scaleValue = MIN_SCALE + tempScale * slope;
                page.setScaleX(scaleValue);
                page.setScaleY(scaleValue);
//                if (position <= 1 && position > 0) { //right scrolling
//                    Log.e("tag", position + "=========");
//                    page.setPivotX(0);
////                    page.setRotationY(90f*position);
//                    page.setScaleX(scaleFactor);
//                    page.setScaleY(scaleFactor);
//
//
//                } else if (position == 0) {
//
//                } else if (position < 0 && position > -1) {
//                    Log.e("tag", position + "<<<<<<<<<");
//                    page.setPivotX(width);
//                    page.setScaleX(scaleFactor);
//                    page.setScaleY(scaleFactor);
//
////                    page.setRotationY(90f * position);
//                }else if (position<-1){
//                    page.setAlpha(0);
//                }else if (position>1){
//                    page.setAlpha(0);
//                }


            }
        });

        viewPagerContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return viewPager.dispatchTouchEvent(event);
            }
        });
    }


}
