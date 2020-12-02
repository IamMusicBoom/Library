package com.wma.library.base;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

/**
 * create by wma
 * on 2020/11/18 0018
 */
public class BasePagerTransformer implements ViewPager.PageTransformer {
    final String TAG = BasePagerTransformer.class.getSimpleName();
    private static final float MIN_SCALE = 0.75f;

    @Override
    public void transformPage(@NonNull View view, float position) {
//        if (position <= 0f) {
//            page.setTranslationX(0f);
//            page.setScaleX(1f);
//            page.setScaleY(1f);
//        } else if (position <= 1f) {
//            final float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
//            page.setAlpha(1 - position);
//            page.setPivotY(0.5f * page.getHeight());
//            page.setTranslationX(page.getWidth() * -position);
//            page.setScaleX(scaleFactor);
//            page.setScaleY(scaleFactor);
//        }
//        page.setAlpha(1 - Math.abs(position));
//        page.setScaleX(1 - Math.abs(position));
//        page.setScaleY(1 - Math.abs(position));

        int pageWidth = view.getWidth();

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0);

        } else if (position <= 0) { // [-1,0]
            // Use the default slide transition when moving to the left page
            view.setAlpha(1);
            view.setTranslationX(0);
            view.setScaleX(1);
            view.setScaleY(1);

        } else if (position <= 1) { // (0,1]
            // Fade the page out.
            view.setAlpha(1 - position);

            // Counteract the default slide transition
            view.setTranslationX(pageWidth * -position);

            // Scale the page down (between MIN_SCALE and 1)
            float scaleFactor = MIN_SCALE
                    + (1 - MIN_SCALE) * (1 - Math.abs(position));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0);
        }
    }
}
