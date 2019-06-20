package com.example.wechattab;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.example.wechattab.fragment.SplashFragment;
import com.example.wechattab.view.transformer.RotateTransformer;
import com.example.wechattab.view.transformer.ScaleTransformer;

public class BannerActivity extends AppCompatActivity {

    private ViewPager mViewPagerMain;

    private int[] mResIds = new int[]{
            0xfff00000,
            0xff0f0000,
            0xff00f000,
            0xff000f00,
            0xff0000f0,
            0xff00000f,
            0xffff0000,
            0xff00ff00,
            0xff0000ff
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        mViewPagerMain = findViewById(R.id.vp_main);
        mViewPagerMain.setOffscreenPageLimit(3);
        mViewPagerMain.setPageMargin(40);
        mViewPagerMain.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mResIds.length;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                Context context;
                View view = new View(container.getContext());
                view.setBackgroundColor(mResIds[position]);
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }
        });
        mViewPagerMain.setPageTransformer(true, new RotateTransformer());
    }
}
