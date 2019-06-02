package com.gaohui.fullscreenswipeback;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;



public class HorizontalRecyclerView extends RecyclerView {
    private float lastX = 0f;
    private float lastY = 0f;
    private static final float mRatio = 1.25f;
    private static final long QUICK_SLIDE = 600;

    private boolean flag  = true;

    private long lastTime = 0,currentTime = 0;

    private boolean isSupportFullScreenBack = false;

    private boolean isNeedFixQuickSlide = false;

    public HorizontalRecyclerView(Context context) {
        super(context);
    }

    public HorizontalRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 是否支持全屏侧滑
     * @param supportFullScreenBack
     */
    public void setSupportFullScreenBack(boolean supportFullScreenBack) {
        isSupportFullScreenBack = supportFullScreenBack;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        if(isSupportFullScreenBack) {
            return fullScreenBackInterceptTouchEvent(e);
        } else {
            return super.onInterceptTouchEvent(e);
        }

    }

    /**
     * 支持全屏侧滑情况下事件拦截
     * @param e
     * @return
     */
    private boolean fullScreenBackInterceptTouchEvent(MotionEvent e) {
        float x = e.getRawX();
        float y = e.getRawY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                requestInterceptTouchEvent(x,y,true);
                getParent().requestDisallowInterceptTouchEvent(true);
//                //这里设置false是防止在ACTION_DOWN里面直接让子View获取事件，因为这里是否需要消费事件是根据手指滑动方向来确定的
//                //因为这里是用flag && super.onInterceptTouchEvent(e)两个变量来确定是否需要拦截事件
                flag = false;
                lastX = x;
                lastY = y;
                collectTouchEventInterval();
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = x - lastX;
                LinearLayoutManager manager = (LinearLayoutManager) getLayoutManager();
                int firstCompletelyVisibleItemPosition = manager.findFirstCompletelyVisibleItemPosition();
                int lastCompletelyVisibleItemPosition = manager.findLastCompletelyVisibleItemPosition();
                int itemCount = manager.getItemCount();
                if(firstCompletelyVisibleItemPosition == 0  && dx >0) {
                    //当前在第一张图片且向左滑动，不拦截
//                    if(isNeedFixQuickSlide) {
//                        //左滑finish页面的时候，如果两次事件小于600ms，需要拦截滑动
//                        requestInterceptTouchEvent(x,y,true);
//                        isNeedFixQuickSlide = false;
//                    } else {
                        requestInterceptTouchEvent(x,y,false);
//                    }
                    break;
                } else if(firstCompletelyVisibleItemPosition == 0  && dx <0) {
                    //当前在第一张图片且向右滑动，拦截
                    requestInterceptTouchEvent(x,y,true);
                    break;
                } else if(lastCompletelyVisibleItemPosition == (itemCount - 1) && dx <0) {
                    //当前在最后一张图片且向右滑动，不拦截
                    requestInterceptTouchEvent(x,y,false);
                    break;
                } else  if(lastCompletelyVisibleItemPosition == (itemCount - 1) && dx >0) {
                    //当前在最后一张图片且向右滑动，不拦截
                    requestInterceptTouchEvent(x,y,true);
                    break;
                }
                float dealtX = Math.abs(x - lastX);
                float dealtY = Math.abs(y - lastY);
                if (dealtX > 1f || dealtY > 1f) {
                    //这里是之前做的优化，为了让图片横滑起来更容易
                    if (dealtY < dealtX * mRatio) {
                        requestInterceptTouchEvent(x,y,true);
                    } else {
                        requestInterceptTouchEvent(x,y,true);
                    }
                }
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        //这里必须要保证super.onInterceptTouchEvent(e)在flag为false的时候能够调用一次，否则RecyclerView将不会响应onTouchEvent
        return super.onInterceptTouchEvent(e) && flag;
    }


    private void collectTouchEventInterval() {
        if(lastTime == 0) {
            lastTime = System.currentTimeMillis();
        } else {
            currentTime = System.currentTimeMillis();
            if(currentTime - lastTime < QUICK_SLIDE) {
                isNeedFixQuickSlide = true;
            }
            lastTime = 0;
        }
    }

    private void requestInterceptTouchEvent(float x,float y,boolean isIntercept) {
        getParent().requestDisallowInterceptTouchEvent(isIntercept);
        flag = isIntercept;
        lastX = x;
        lastY = y;
    }


}

