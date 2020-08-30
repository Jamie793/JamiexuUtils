package com.jamiexu.utils.android.dex.item;

public class StringDataItem {

    private String data;
    private final int size;

    public StringDataItem(String data, int size) {
        this.data = data;
        this.size = size;
    }


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getSize() {
        return size;
    }

}
