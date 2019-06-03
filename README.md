# FullScreenSwipeBack
FullScreenSwipeBack是基于  [SwipeBackLayout](https://github.com/ikew0ng/SwipeBackLayout)的二次修改，非常感谢作者能开源这么优秀的库。

### 修改主要是为了支持全屏侧滑功能

![image](https://raw.githubusercontent.com/JasonGaoH/FullScreenSwipeBack/master/images/full_screen_back.gif)

### 兼容ViewPager

![image](https://raw.githubusercontent.com/JasonGaoH/FullScreenSwipeBack/master/images/full_screen_back_viewpager.gif)

### 兼容横滑RecyclerView

![image](https://raw.githubusercontent.com/JasonGaoH/FullScreenSwipeBack/master/images/full_screen_back_viewpager.gif)

具体使用：

```
//通过使用setSupportFullScreenBack来打开全屏侧滑手势
//目前只支持全屏左滑手势
getSwipeBackLayout().setSupportFullScreenBack(true);
```

ViewPager兼容横滑，这里采用"外部拦截法"

```
//传递ViewPager自身引用来让SwipeBackLayout来知道是否需要拦截全屏侧滑事件
viewPager.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View view) {
                getSwipeBackLayout().setTargetHorizontalScrollView(viewPager);
            }

            @Override
            public void onViewDetachedFromWindow(View view) {
            }
        });
```

RecyclerView兼容横滑逻辑，采用"内部拦截法"

主要采用requestDisallowInterceptTouchEvent获取父View的事件。
```
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
                getParent().requestDisallowInterceptTouchEvent(true);
                //这里设置false是防止在ACTION_DOWN里面直接让子View获取事件，因为这里是否需要消费事件是根据手指滑动方向来确定的
                //因为这里是用flag && super.onInterceptTouchEvent(e)两个变量来确定是否需要拦截事件
                flag = false;
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = x - lastX;
                LinearLayoutManager manager = (LinearLayoutManager) getLayoutManager();
                int firstCompletelyVisibleItemPosition = manager.findFirstCompletelyVisibleItemPosition();
                int lastCompletelyVisibleItemPosition = manager.findLastCompletelyVisibleItemPosition();
                int itemCount = manager.getItemCount();
                if(firstCompletelyVisibleItemPosition == 0  && dx >0) {
                    requestInterceptTouchEvent(x,y,false);
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

```

### 注意
> 使用该自定义RecyclerView需要给RecyclerView的子View添加onTouch的监听事件，并且返回true。


```
imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;

                }
            });
```


### TODO 

整理出一篇关于全屏侧滑的文章，介绍关于这个的实现以及遇到的问题。

欢迎拍砖，有问题随时交流，感谢！！！


关于
--

博客：[https://blog.csdn.net/H_Gao](https://blog.csdn.net/H_Gao)

邮箱：532294580@qq.com

License
--
Copyright 2018 JasonGaoH

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions



