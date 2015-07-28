package com.support.android.designlibdemo;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by admin on 26.07.2015.
 */
public class MyCoordinatorLayout extends CoordinatorLayout {
    private static final String TAG = "MyCoordinatorLayout";
    private MainActivity listener;

    public MyCoordinatorLayout(Context context) {
        super(context);
    }

    public MyCoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        if (listener != null){
            listener.checkActionBarState();
        }
        super.onNestedPreScroll(target, dx, dy, consumed);
        Log.d(TAG, "onNestedPreScroll() called with " + "], dx = [" + dx + "], dy = [" + dy + "], consumed = [" + consumed + "]");
        isNestedScrollCalled = false;
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        Log.d(TAG, "onNestedScroll() called with " + "], dxConsumed = [" + dxConsumed + "], dyConsumed = [" + dyConsumed + "], dxUnconsumed = [" + dxUnconsumed + "], dyUnconsumed = [" + dyUnconsumed + "]");
        isNestedScrollCalled = true;
    }

    boolean isNestedScrollCalled = false;

    @Override
    public void onStopNestedScroll(View target) {
        super.onStopNestedScroll(target);
        Log.d(TAG, "onStopNestedScroll() called with isNestedScrollCalled: " + isNestedScrollCalled + " ;  target = [" + target + "]");
        if (!isNestedScrollCalled){
            Log.e(TAG, "onStopNestedScroll() " + getParent());
            if (listener != null){
                listener.checkActionBarPosition();
            }
        }
    }

    public void setListener(MainActivity listener) {
        this.listener = listener;
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        Log.e(TAG, "onNestedFling() called with " + "target = [" + target + "], velocityX = [" + velocityX + "], velocityY = [" + velocityY + "], consumed = [" + consumed + "]");
        return super.onNestedFling(target, velocityX, velocityY, consumed);
    }

}
