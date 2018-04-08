package com.demo.gia.entities;

/**
 * 商品对应的实体类
 *
 * @author [作者]
 * @version [版本号，2018-04-08]
 */
public class Goods {
    /**
     * 商品名称
     */
    private String mName;
    /**
     * 商品价格
     */
    private float mPrice;
    /**
     * 是否选中
     */
    private boolean mIsChecked;

    public Goods(String name, float price, boolean isChecked) {
        mName = name;
        mPrice = price;
        mIsChecked = isChecked;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public float getPrice() {
        return mPrice;
    }

    public void setPrice(float price) {
        mPrice = price;
    }

    public boolean isChecked() {
        return mIsChecked;
    }

    public void setChecked(boolean checked) {
        mIsChecked = checked;
    }
}
