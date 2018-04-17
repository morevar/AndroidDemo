package com.demo.hellorecyclerview.test2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.hellorecyclerview.R;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author [作者]
 * @version [版本号，2018-04-17]
 */
public class MyRecyclerViewAdapter extends BaseRecyclerViewAdapter<String> {

    public MyRecyclerViewAdapter(Context context, BaseRecyclerViewAdapter.ItemClickListener itemClickListener) {
        super(context, itemClickListener);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).myTextView.setText(mDataList.get(position));
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView myTextView;

        ItemViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.tv_1);
        }
    }
}