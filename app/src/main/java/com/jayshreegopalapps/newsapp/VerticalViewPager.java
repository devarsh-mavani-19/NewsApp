package com.jayshreegopalapps.newsapp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class VerticalViewPager extends ViewPager {
    public VerticalViewPager(@NonNull Context context) {
        super(context);
        init();
    }

    private void init() {
        setPageTransformer(true, new ViewPagerTransform());
        setOverScrollMode(OVER_SCROLL_NEVER);

    }

    public VerticalViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private MotionEvent SwapXY(MotionEvent event) {
        float x = getWidth();
        float y = getHeight();

        float newX = (event.getY() / y) * y;
        float newY = (event.getY() / x) * x;

        event.setLocation(newX, newY);
        return event;

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = super.onInterceptTouchEvent(SwapXY(ev));
        SwapXY(ev);
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(SwapXY(ev));
    }


    private class ViewPagerTransform implements PageTransformer {
        public static final float MIN_SCALE = 0.65f;

        @Override
        public void transformPage(@NonNull View page, float position) {
            if(position < -1) {
                page.setAlpha(0);
            } else if(position <= 0) {
                page.setAlpha(1);
                page.setTranslationX(page.getWidth() * -position);
                page.setTranslationY(page.getHeight() * position);
                page.setScaleX(1);
                page.setScaleY(1);
            } else if(position <= 1) {
                page.setAlpha(1 - position);
                page.setTranslationX(page.getWidth() * -position);
                page.setTranslationY(0);
                float scaleFactor = MIN_SCALE + (1-MIN_SCALE);
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
            } else if(position > 1) {
                page.setAlpha(0);
            }
        }
    }
}

