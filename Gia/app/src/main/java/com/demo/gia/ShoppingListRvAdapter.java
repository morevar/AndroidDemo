package com.demo.gia;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.gia.entities.Goods;
import com.demo.gia.util.ItemSlideHelper;

import java.util.List;

import ch.ielse.view.SwitchView;

/**
 * 购物车列表对应的Adapter
 *
 * @author [作者]
 * @version [版本号，2018-04-08]
 */
public class ShoppingListRvAdapter extends RecyclerView.Adapter<ShoppingListRvAdapter.ViewHolder> implements ItemSlideHelper.Callback {

    private List<Goods> mData;
    private IEvent mEvent;
    private RecyclerView mRecyclerView;

    ShoppingListRvAdapter(List<Goods> data, IEvent event) {
        mData = data;
        mEvent = event;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_shopping_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder vh, int position) {
        Goods item = mData.get(position);

        vh.sw.toggleSwitch(item.isChecked());
        vh.tvName.setText(item.getName());
        vh.tvPrice.setText(String.valueOf(item.getPrice()));
        vh.tvPrice.append("元");

        setItemChecked(item, vh.llMain);

        vh.sw.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                vh.sw.toggleSwitch(true);
                int position = vh.getAdapterPosition();
                onSwitchCheckedChanged(position, view, true);
            }

            @Override
            public void toggleToOff(SwitchView view) {
                vh.sw.toggleSwitch(false);
                int position = vh.getAdapterPosition();
                onSwitchCheckedChanged(position, view, false);
            }
        });

        vh.llDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEvent != null) {
                    mEvent.deleteItem(vh.getAdapterPosition(), mData.get(vh.getAdapterPosition()));
                }
            }
        });
    }

    private void onSwitchCheckedChanged(int position, View view, boolean isChecked) {
        Goods item = mData.get(position);
        item.setChecked(isChecked);

        if (mEvent != null) {
            mEvent.onItemChecked(position, item);
        }
        setItemChecked(item, (View) view.getParent());
    }

    // 设置Item的背景颜色
    private void setItemChecked(Goods item, View view) {
        if (item.isChecked()) {
            view.setBackgroundResource(R.color.lt_gray);
        } else {
            view.setBackgroundResource(R.color.lt_gray_2);
        }
    }


    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    List<Goods> getItems() {
        return mData;
    }

    /**
     * 将商品移除出购物车
     * @param pos 商品Item在Adapter中的位置
     */
    void removeItem(int pos) {
        mData.remove(pos);
        notifyItemRemoved(pos);
    }


    //region - 左滑操作相关(ItemSlideHelper) -

    /**
     * 此方法用来计算水平方向移动的距离
     */
    @Override
    public int getHorizontalRange(RecyclerView.ViewHolder holder) {
        int range = 0;
        if (holder.itemView instanceof LinearLayout) {
            ViewGroup viewGroup = (ViewGroup) holder.itemView;
            //viewGroup包含2个控件，即消息主item、删除，返回为标记已读宽度+删除宽度
            View view1 = viewGroup.getChildAt(1);
            if (view1 != null) {
                range = view1.getLayoutParams().width;
            }
        }
        return range;
    }

    @Override
    public RecyclerView.ViewHolder getChildViewHolder(View childView) {
        return mRecyclerView.getChildViewHolder(childView);
    }

    @Override
    public View findTargetView(float x, float y) {
        return mRecyclerView.findChildViewUnder(x, y);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
        mRecyclerView.addOnItemTouchListener(new ItemSlideHelper(mRecyclerView.getContext(), this));
    }

    //endregion

    static class ViewHolder extends RecyclerView.ViewHolder {
        SwitchView sw;
        TextView tvName, tvPrice;
        View llMain, llDel;

        ViewHolder(View itemView) {
            super(itemView);
            sw = itemView.findViewById(R.id.sw_select);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            llMain = itemView.findViewById(R.id.ll_ri_main);
            llDel = itemView.findViewById(R.id.ll_ri_del);
        }
    }

    interface IEvent {
        /**
         * 选中/取消选中某商品时的回调方法
         * @param position adapter的位置
         * @param item 点击的商品
         */
        void onItemChecked(int position, Goods item);

        void deleteItem(int position, Goods item);
    }
}
