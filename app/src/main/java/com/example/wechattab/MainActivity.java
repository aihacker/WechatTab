package com.example.wechattab;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.wechattab.fragment.TabFragment;
import com.example.wechattab.utils.LogUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {//https://www.jianshu.com/p/e5abbda4a71c
//https://blog.csdn.net/xiaolaohuqwer/article/details/75670294
    private ViewPager mViewPagerMain;
    private List<String> mTabFragmentTitleList = new ArrayList<>(Arrays.asList("微信", "通讯录", "发现","我"));

    private Button mBtnWeChat;
    private Button mBtnContact;
    private Button mBtnFound;
    private Button mBtnSelf;

    private List<Button> mTabList = new ArrayList<>();
    private SparseArray<TabFragment> mFragments = new SparseArray<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initViewPagerAdapter();

    }

    private void initViewPagerAdapter() {
        mViewPagerMain.setOffscreenPageLimit(mTabFragmentTitleList.size());
        mViewPagerMain.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {//相当于CreateFragment
                LogUtil.d("FragmentPagerAdapter getItem position = " + position);
                TabFragment tabFragment = TabFragment.newInstance(mTabFragmentTitleList.get(position));
                if(position ==0){
                    tabFragment.setOnTitleClickListener(new TabFragment.OnTitleClickListener() {
                        @Override
                        public void onClick(String title) {
                            changeWeChatTab(title);
                        }
                    });
                }
                return tabFragment;
            }

            @Override
            public int getCount() {
                return mTabFragmentTitleList.size();
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                TabFragment fragment = (TabFragment) super.instantiateItem(container, position);
                mFragments.put(position, fragment);
                return fragment;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                mFragments.remove(position);
                super.destroyItem(container, position, object);
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return getItem(position).getClass().getSimpleName();
            }
        });

        mViewPagerMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LogUtil.d("onPageScrolled position = " + position + " positionOffset = "+ positionOffset);
//                      position
//              左-->右   0->1,   left pos, right pos + 1, positionOffset 0~1
                //left process: 1~0(1-positionOffset);  right process:0~1(positionOffset)
//              右-->左   1->0, left pos, right pos + 1, positionOffset 1~0
                //left process: 0~1(1-positionOffset);  right process:1~0(positionOffset)
                if(positionOffset>0) {
                    Button btn_Left = mTabList.get(position);
                    Button btn_Right = mTabList.get(position + 1);

                    btn_Left.setText(1 - positionOffset + "");
                    btn_Right.setText(positionOffset + "");
                }
            }

            @Override
            /**
             * @param state The new scroll state.
             * @see ViewPager#SCROLL_STATE_IDLE 0       完全停下来的状态
             * @see ViewPager#SCROLL_STATE_DRAGGING 1   拉着的状态
             * @see ViewPager#SCROLL_STATE_SETTLING 2   手放开状态
             */
            public void onPageSelected(int position) {
                LogUtil.d("onPageSelected position = " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initViews() {
        mViewPagerMain = findViewById(R.id.vp_main);
        mBtnWeChat = findViewById(R.id.btn_wechat);
        mBtnContact = findViewById(R.id.btn_contact);
        mBtnFound = findViewById(R.id.btn_found);
        mBtnSelf = findViewById(R.id.btn_self);

        mTabList.add(mBtnWeChat);
        mTabList.add(mBtnContact);
        mTabList.add(mBtnFound);
        mTabList.add(mBtnSelf);

        mBtnWeChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如何获取Fragment
                TabFragment tabFragment = mFragments.get(0);
                if(tabFragment != null){
                    tabFragment.changeTitle("Activity调用Fragment方法");//2.通信1：activity --> fragment的方法
                }
            }
        });
    }

    public void changeWeChatTab(String title){//2.通信2：fragment --> activity的方法
        mBtnWeChat.setText(title);
    }
    /**
     * 区别：
     * FragmentPagerAdapter：
     *      onDestoryView
     *      onCreateView
     *      Tab滑动过程中，一般只保留左右最近的一个，多余的会被销毁掉，但Fragment没有被销毁
     *      适用范围：适用于页面比较少的情况，如微信
     * FragmentStatePagerAdapter:
     *      onDestoryView
     *      onDestory
     *      onCreate
     *      onCreateView
     *
     *      Tab滑动过程中，一般只保留左右最近的一个，多余的会被销毁掉，Fragment一并被销毁。
     *      适用范围：适用于页面比较多的情况，如照片预览有上千个Tab，适用FragmentStatePagerAdapter
     *
     */
}
