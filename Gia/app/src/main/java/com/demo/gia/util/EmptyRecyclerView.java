package com.demo.gia.util;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 具有EmptyView的RecyclerView
 *
 * @author [作者]
 * @version [版本号，2018-04-08]
 */
public class EmptyRecyclerView extends RecyclerView {

    private View mEmptyView;

    private AdapterDataObserver mObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            Adapter adapter = getAdapter();
            if(adapter.getItemCount() == 0){
                if (mEmptyView != null) {
                    mEmptyView.setVisibility(VISIBLE);
                }
                EmptyRecyclerView.this.setVisibility(GONE);
            } else{
                if (mEmptyView != null) {
                    mEmptyView.setVisibility(GONE);
                }
                EmptyRecyclerView.this.setVisibility(VISIBLE);
            }
        }

        public void onItemRangeChanged(int positionStart, int itemCount) {onChanged();}
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {onChanged();}
        public void onItemRangeRemoved(int positionStart, int itemCount) {onChanged();}
        public void onItemRangeInserted(int positionStart, int itemCount) {onChanged();}
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {onChanged();}
    };

    public EmptyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setEmptyView(View view){
        this.mEmptyView = view;
        //((ViewGroup)this.getRootView()).addView(mEmptyView); //加入主界面布局
        ((ViewGroup)this.getParent()).addView(mEmptyView); //加入父布局
    }

    public void setAdapter(RecyclerView.Adapter adapter){
        super.setAdapter(adapter);
        adapter.registerAdapterDataObserver(mObserver);
        mObserver.onChanged();
    }
}
