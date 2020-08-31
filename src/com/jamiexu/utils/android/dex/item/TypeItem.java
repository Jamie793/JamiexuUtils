package com.jamiexu.utils.android.dex.item;

import java.util.ArrayList;

public class TypeItem {
    private int size;
    private ArrayList<Short> arrayList;

    public TypeItem(int size, ArrayList<Short> arrayList) {
        this.size = size;
        this.arrayList = arrayList;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public ArrayList<Short> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Short> arrayList) {
        this.arrayList = arrayList;
    }
}
