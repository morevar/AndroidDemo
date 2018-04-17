package com.demo.hellorecyclerview.test2;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.demo.hellorecyclerview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现 EmptyView / ErrorRetry / Loading 效果
 * <br/>
 * 参考：https://github.com/PhanVanLinh/AndroidRecyclerViewWithLoadingEmptyAndRetry
 *
 * @author [作者]
 * @version [版本号，2018-04-17]
 */
public class ActTest2 extends AppCompatActivity implements BaseRecyclerViewAdapter.ItemClickListener, View.OnClickListener {

    private RecyclerViewEmptyRetryGroup mRecyclerViewEmptyRetryGroup;
    private MyRecyclerViewAdapter mAdapter;
    private List<String> mData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test2);

        mRecyclerViewEmptyRetryGroup = findViewById(R.id.recyclerViewEmptyRetryGroup);
        mRecyclerViewEmptyRetryGroup.setOnRetryClick(new RecyclerViewEmptyRetryGroup.OnRetryClick() {
            @Override
            public void onRetry() {
                loadDataSuccess();
            }
        });

        RecyclerView rv = mRecyclerViewEmptyRetryGroup.getRecyclerView();
        // LayoutManager
        LinearLayoutManager lm = new LinearLayoutManager(rv.getContext());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(lm);
        // Adapter
        mData = new ArrayList<>();
        mAdapter = new MyRecyclerViewAdapter(this, this);
        rv.setAdapter(mAdapter);

        findViewById(R.id.btn_empty).setOnClickListener(this);
        findViewById(R.id.btn_err).setOnClickListener(this);
        findViewById(R.id.btn_loading).setOnClickListener(this);
    }


    private void loadDataEmpty() {
        mAdapter.clear();
        mRecyclerViewEmptyRetryGroup.loading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRecyclerViewEmptyRetryGroup.empty();
                mAdapter.notifyDataSetChanged();
            }
        }, 500);
    }

    private void loadDataFailed() {
        mAdapter.clear();
        mRecyclerViewEmptyRetryGroup.loading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRecyclerViewEmptyRetryGroup.retry();
                mAdapter.notifyDataSetChanged();
            }
        }, 500);
    }

    private void loadDataSuccess() {
        mData.clear();

        mRecyclerViewEmptyRetryGroup.loading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mData.add("b");
                mData.add("b");
                mData.add("b");
                mData.add("b");
                mData.add("b");
                mData.add("b");
                mData.add("b");
                mData.add("b");
                mData.add("b");
                mData.add("b");
                mData.add("b");

                mAdapter.set(mData);
                mRecyclerViewEmptyRetryGroup.success();
                mAdapter.notifyDataSetChanged();
            }
        }, 500);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_empty:
                loadDataEmpty();
                break;
            case R.id.btn_err:
                loadDataFailed();
                break;
            case R.id.btn_loading:
                loadDataSuccess();
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {

    }

}
