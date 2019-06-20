package com.example.wechattab;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.example.wechattab.fragment.SplashFragment;
import com.example.wechattab.fragment.TabFragment;
import com.example.wechattab.utils.LogUtil;
import com.example.wechattab.view.TabView;
import com.example.wechattab.view.transformer.ScaleTransformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private ViewPager mViewPagerMain;

    private int[] mResIds = new int[]{
        R.drawable.guide_image1,
        R.drawable.guide_image2,
        R.drawable.guide_image3,
        R.drawable.guide_image1
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mViewPagerMain = findViewById(R.id.vp_main);

        mViewPagerMain.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return SplashFragment.newInstance(mResIds[i]);
            }

            @Override
            public int getCount() {
                return mResIds.length;
            }
        });
        mViewPagerMain.setPageTransformer(true, new ScaleTransformer());
    }
}
