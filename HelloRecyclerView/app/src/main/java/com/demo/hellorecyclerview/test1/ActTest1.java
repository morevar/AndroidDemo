package com.demo.hellorecyclerview.test1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.demo.hellorecyclerview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author [作者]
 * @version [版本号，2018-04-17]
 */
public class ActTest1 extends AppCompatActivity implements View.OnClickListener {

    private RvAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test1);

        RecyclerView rv = findViewById(R.id.rv_1);
        LinearLayoutManager lm = new LinearLayoutManager(rv.getContext());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(lm);
        rv.setAdapter(getAdapter());

        findViewById(R.id.btn_loading).setOnClickListener(this);
        findViewById(R.id.btn_empty).setOnClickListener(this);
        findViewById(R.id.btn_err).setOnClickListener(this);
    }

    private RecyclerView.Adapter getAdapter() {
        if (mAdapter == null) {
            List<String> data = new ArrayList<>();
            mAdapter = new RvAdapter(data, new RvAdapter.IEvent() {
                @Override
                public void onRetry() {
                    List<String> data = new ArrayList<>();
                    data.add("1");
                    data.add("2");
                    data.add("3");
                    data.add("4");
                    data.add("5");
                    data.add("6");
                    mAdapter.setData(data);
                }
            });
        }
        return mAdapter;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_loading:
                mAdapter.setLoading(true);
                break;
            case R.id.btn_empty:
                mAdapter.setEmpty(true);
                break;
            case R.id.btn_err:
                mAdapter.setError(true);
                break;
        }
    }

}
