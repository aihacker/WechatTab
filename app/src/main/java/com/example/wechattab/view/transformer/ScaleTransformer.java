package com.example.wechattab.view.transformer;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.wechattab.utils.LogUtil;

/**
 * 参考：https://developer.android.google.cn/training/animation/screen-slide
 *      https://www.imooc.com/video/19290
 */
public class ScaleTransformer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.75F;
    private static final float MIN_ALPHA = 0.1F;
    @Override
    public void transformPage(@NonNull View page, float position) {
        LogUtil.d(page.getTag() + ", position = " + position);
        if(position < -1){
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
            page.setAlpha(MIN_ALPHA);
        } else if(position<= 1){
/*        page: 1 -> 2
                1 : pos [0 -> -1]
                    siz [1 -> 0.75]的变化基于position去实现。
                    α  [1 -> 0.5]
                    1+pos*(1-MIN_SCALE)

                2 : pos [1 -> 0]
                2 : siz [0.75 -> 1]这个变化基于position
                2 : α  [0.5 -> 1]
                    1-pos*(1-MIN_SCALE)

          page: 1 <- 2
            1 ：pos [-1 -> 0]
            1 : siz [0.75->1]大小变化基于pos
                1+pos*(1-MIN_SCALE)

            2 : pos [0 -> 1]
            2 : siz [1 -> 0.75]
                1-pos*(1-MIN_SCALE)
            */
            if(position < 0){//作用于左边页面
                float scaleA = 1 + position * (1 - MIN_SCALE);
                float alphaA = 1 + position * (1 - MIN_ALPHA);
                page.setScaleX(scaleA);
                page.setScaleY(scaleA);
                page.setAlpha(alphaA);
            } else {//作用于右边页面
                float scaleB = 1 - position * (1 - MIN_SCALE);
                float alphaB = 1 - position * (1 - MIN_ALPHA);
                page.setScaleX(scaleB);
                page.setScaleY(scaleB);
                page.setAlpha(alphaB);
            }
        } else {
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
            page.setAlpha(MIN_ALPHA);
        }
    }
}
