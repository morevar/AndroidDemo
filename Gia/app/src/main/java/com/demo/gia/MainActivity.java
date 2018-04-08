package com.demo.gia;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.demo.gia.entities.Goods;
import com.demo.gia.util.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ch.ielse.view.SwitchView;

public class MainActivity extends AppCompatActivity {

    TextView mTvSummary; // 购物车商品价格合计
    EmptyRecyclerView mRvShoppingList; // 购物车商品列表
    ShoppingListRvAdapter adapter; // 购物车商品列表-Adapter
    SwitchView mSwSelectAll; // 是否全选购物车商品

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideActionBar();
        setContentView(R.layout.act_main);

        mRvShoppingList = findViewById(R.id.rv_shopping_list);
        mTvSummary = findViewById(R.id.tv_summary);
        mSwSelectAll = findViewById(R.id.sw_select_all);

        initView();
    }

    private void initView() {
        // 1. 购物车列表
        // 布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvShoppingList.setLayoutManager(manager);
        // EmptyView
        View emptyView = LayoutInflater.from(this).inflate(R.layout.rv_empty_view_shopping_list, mRvShoppingList, false);
        mRvShoppingList.setEmptyView(emptyView);
        // Adapter
        List<Goods> data = getData();
        adapter = new ShoppingListRvAdapter(data, new ShoppingListRvAdapter.IEvent() {
            @Override
            public void onItemChecked(int position, Goods item) {
                List<Goods> items = adapter.getItems();
                calcSummary(items);
            }

            @Override
            public void deleteItem(int position, Goods item) {
                adapter.removeItem(position);
                calcSummary(adapter.getItems());
            }
        });
        mRvShoppingList.setAdapter(adapter);

        // 2. 全选/取消全选
        mSwSelectAll.setOnStateChangedListener(getListener());

        // 3. 计算选中商品的总价
        calcSummary(data);
    }

    private SwitchView.OnStateChangedListener getListener() {
        return new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                if (view.isEnabled()) {
                    view.toggleSwitch(true);
                    onSwitchCheckedChanged(view, true);
                } else {
                    view.toggleSwitch(false);
                }
            }

            @Override
            public void toggleToOff(SwitchView view) {
                if (view.isEnabled()) {
                    view.toggleSwitch(false);
                    onSwitchCheckedChanged(view, false);
                }
            }
        };
    }

    private void onSwitchCheckedChanged(View view, boolean isChecked) {
        List<Goods> items = adapter.getItems();
        if (items != null && items.size() > 0 && view.isEnabled()) {
            float summary = 0f;
            if (isChecked) {
                for (Goods i : items) {
                    i.setChecked(true);
                    summary += i.getPrice();
                }
            } else {
                for (Goods i : items) {
                    i.setChecked(false);
                }
            }

            adapter.notifyDataSetChanged();
            setGoodsSummary(summary);
        }
    }

    /**
     * 计算选中商品的总价，并设置全选/取消全选开关的状态
     * @param items 商品集合
     */
    private void calcSummary(List<Goods> items) {
        float summary = 0f;

        mSwSelectAll.setEnabled(false);
        if (items != null && items.size() > 0) {
            boolean flag = true;
            for (Goods i : items) {
                if (i.isChecked()) {
                    summary += i.getPrice();
                } else {
                    flag = false;
                }
            }

            mSwSelectAll.toggleSwitch(flag);
            mSwSelectAll.setEnabled(true);
        } else {
            mSwSelectAll.toggleSwitch(false);
            mSwSelectAll.setEnabled(false);
        }

        setGoodsSummary(summary);
    }

    private void setGoodsSummary(float summary) {
        if (summary < 0) {
            summary = 0;
        }
        mTvSummary.setText(String.format(Locale.CHINA, "%.1f", summary));
        mTvSummary.append("元");
    }

    /**
     * 获取购物车中的商品数据集合
     */
    private List<Goods> getData() {
        List<Goods> data = new ArrayList<>();
        data.add(new Goods("数据结构", 30.6f, false));
        data.add(new Goods("C++程序基础", 35.0f, true));
        data.add(new Goods("数据结构", 30.6f, false));
        data.add(new Goods("高等数学", 35.0f, true));
        data.add(new Goods("大学语文", 30.6f, false));
        data.add(new Goods("大学英语教程", 35.0f, true));
        data.add(new Goods("数据库技术", 30.6f, false));
        data.add(new Goods("设计模式", 35.0f, true));
        return data;
    }

    /**
     * 隐藏ActionBar
     */
    public void hideActionBar() {
        // 隐藏 AppCompatActivity 中的 ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }
}
