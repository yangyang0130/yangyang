package com.yangyang.rkq.View;

import android.content.Context;

import android.util.AttributeSet;

import androidx.core.widget.NestedScrollView;


public class AnimationNestedScrollView extends NestedScrollView {

    private OnAnimationScrollChangeListener listener;

    public AnimationNestedScrollView(Context context) {
        super(context);
    }

    public AnimationNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimationNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnAnimationScrollListener(OnAnimationScrollChangeListener listener) {
        this.listener = listener;
    }

    public interface OnAnimationScrollChangeListener {
        void onScrollChanged(float dy);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (listener != null) {
            listener.onScrollChanged(getScrollY() * 0.65f);//x0.65 使位移效果更加平滑 解决手指按住停留时抖动问题
        }
    }
}
