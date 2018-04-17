package com.demo.hellorecyclerview.test1;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.demo.hellorecyclerview.R;

import java.util.List;

/**
 * 包含 Loading / EmptyView / ErrorAndRetry 三种情况的处理
 * <br/>
 * (1) 需要定义相关的 RvItem 布局（如果布局中有事件需要处理，还需要定义相应的ViewHolder）
 *
 * @author [作者]
 * @version [版本号，2018-04-17]
 */
public class RvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> mData;
    private boolean mIsError = false;
    private boolean mIsEmpty = false;
    private boolean mIsLoading = false;
    private static final int TYPE_LOADING = 1;
    private static final int TYPE_ERROR = 2;
    private static final int TYPE_EMPTY = 3;
    private static final int TYPE_NORMAL = 4;

    public RvAdapter(List<String> data, IEvent event) {
        mData = data;
        mEvent = event;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 前提：定义相关的布局文件、ViewHolder(仅需定义有事件需要处理的ViewHolder)
        View view;
        switch (viewType) {
            case TYPE_LOADING:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_1_loading, parent, false);
                return new BaseHolder(view);
            case TYPE_ERROR:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_2_err, parent, false);
                return new ErrorHolder(view);
            case TYPE_EMPTY:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_3_empty, parent, false);
                return new BaseHolder(view);
            case TYPE_NORMAL:
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_4_data, parent, false);
                return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // 处理相关ViewHolder中的业务逻辑
        int viewType = getItemViewType(position);
        switch (viewType) {
            case TYPE_ERROR: {
                if (holder instanceof ErrorHolder) {
                    ErrorHolder vh = (ErrorHolder) holder;
                    vh.btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO Retry.
                            if (mEvent != null) {
                                mEvent.onRetry();
                            }
                        }
                    });
                }
            }
            case TYPE_NORMAL: {
                if (holder instanceof ItemViewHolder) {
                    ItemViewHolder vh = (ItemViewHolder) holder;
                    if (mData != null && mData.size() > position) {
                        vh.tv.setText(mData.get(position));
                    }
                }
                break;
            }
            case TYPE_EMPTY:
            case TYPE_LOADING:
            default:
        }
    }

    @Override
    public int getItemCount() {
        // 注意：此处mData为空时返回1
        return mData != null && mData.size() > 0 ? mData.size() : 1;
    }

    @Override
    public int getItemViewType(int position) {
        // 定义相关ItemViewType
        if (mIsLoading) return TYPE_LOADING;
        if (mIsError) return TYPE_ERROR;
        if (mIsEmpty) return TYPE_EMPTY;
        return TYPE_NORMAL;
    }

    //region - ViewHolder -
    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        ItemViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_1);
        }
    }

    static class ErrorHolder extends RecyclerView.ViewHolder {

        Button btn;

        ErrorHolder(View itemView) {
            super(itemView);
            btn = itemView.findViewById(R.id.btn_1);
        }
    }

    static class BaseHolder extends RecyclerView.ViewHolder {

        BaseHolder(View itemView) {
            super(itemView);
        }
    }
    //endregion

    //region - 操作Adapter的公开方法 -
    public void setData(List<String> data) {
        mData = data;
        refreshStatus(false, false, false);
    }

    public void setError(boolean error) {
        mData = null;
        refreshStatus(error, false, false);
    }

    public void setEmpty(boolean empty) {
        mData = null;
        refreshStatus(false, empty, false);
    }

    public void setLoading(boolean loading) {
        mData = null;
        refreshStatus(false, false, loading);
    }
    //endregion

    //region - 辅助 -
    private void refreshStatus(boolean err, boolean empty, boolean loading) {
        mIsEmpty = empty;
        mIsError = err;
        mIsLoading = loading;
        notifyDataSetChanged();
    }

    private IEvent mEvent;

    public interface IEvent {
        void onRetry();
    }
    //endregion

}
