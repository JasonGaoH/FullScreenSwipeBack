package com.gaohui.fullscreenswipeback;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class RecyclerViewActivity extends SwipeBackActivity {

    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        findViews();

        initRecyclerView();

        getSwipeBackLayout().setSupportFullScreenBack(true);
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        ArrayList<Object> list = new ArrayList<Object>();
        list.add(1);
        list.add("text1");
        list.add("text2");
        list.add("text3");
        list.add("text4");
        list.add("text5");
        list.add("text6");
        list.add("text7");
        list.add("text8");
        list.add("text9");
        list.add("text10");
        list.add("text11");
        MultiTypeAdapter multiTypeAdapter = new MultiTypeAdapter(list);
        mRecyclerView.setAdapter(multiTypeAdapter);
        multiTypeAdapter.notifyDataSetChanged();
    }

    private void findViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }
}
