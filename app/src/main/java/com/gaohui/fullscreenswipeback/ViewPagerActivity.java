package com.gaohui.fullscreenswipeback;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class ViewPagerActivity extends SwipeBackActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

    private String[] strArray = new String[]{"关注", "推荐", "视频", "直播", "图片", "段子", "精华", "热门"};

    private List<String> stringList = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        findViews();

        initData();

        IndexPagerAdapter indexPagerAdapter = new IndexPagerAdapter(getSupportFragmentManager(),stringList,fragmentList);
        viewPager.setAdapter(indexPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        getSwipeBackLayout().setSupportFullScreenBack(true);
        viewPager.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View view) {
                getSwipeBackLayout().setTargetHorizontalScrollView(viewPager);
            }

            @Override
            public void onViewDetachedFromWindow(View view) {
            }
        });
    }

    private void initData() {
        stringList.addAll(Arrays.asList(strArray));
        for (int i=0;i<stringList.size();i++) {
            Fragment fragment = new BlankFragment();
            fragmentList.add(fragment);
        }
    }

    private void findViews() {
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
    }

    class IndexPagerAdapter extends FragmentPagerAdapter {
        private List<String> titleList;

        public IndexPagerAdapter(FragmentManager fm, List<String> titleList, List<Fragment> fragmentList) {
            super(fm);
            this.titleList = titleList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return titleList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }
}
