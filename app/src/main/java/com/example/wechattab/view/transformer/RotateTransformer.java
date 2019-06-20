package com.example.wechattab.view.transformer;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

public class RotateTransformer implements ViewPager.PageTransformer {
    private static final int MAX_ROTATE = 15;
    @Override
    public void transformPage(@NonNull View page, float position) {
        if(position < -1){
            page.setRotation(-MAX_ROTATE);
            page.setPivotY(page.getHeight());
            page.setPivotX(page.getWidth());
        } else if(position<= 1){
/*        page: 1 -> 2
                1 : pos [0 -> -1]
                    旋转中心 [0.5W -> W]的变化基于position去实现。


                2 : pos [1 -> 0]
                2 : 旋转中心 [0W -> 0.5W]这个变化基于position
                2 : Rotation  [max -> 0]


          page: 1 <- 2
            1 ：pos [-1 -> 0]
            1 : siz [0.75->1]大小变化基于pos
                1+pos*(1-MIN_SCALE)

            2 : pos [0 -> 1]
            2 : siz [1 -> 0.75]
                1-pos*(1-MIN_SCALE)
            */
            if(position < 0){//作用于左边页面pos [0 -> -1] rot[0.5W -> W]
                page.setPivotX(0.5F * page.getWidth() + 0.5F * page.getWidth() * (-position));
                page.setPivotY(page.getHeight());
                page.setRotation(MAX_ROTATE * position);
            } else {//作用于右边页面
                page.setPivotX(0.5F * page.getWidth() - 0.5F * page.getWidth() * (position));
                page.setPivotY(page.getHeight());
                page.setRotation(MAX_ROTATE * position);
            }
        } else {
            page.setRotation(MAX_ROTATE);
            page.setPivotY(page.getHeight());
            page.setPivotX(0);
        }
    }
}
