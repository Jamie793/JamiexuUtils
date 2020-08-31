package com.jamiexu.utils.android.dex.item;

public class ProtoIDItem {
    private int shortyIdx;
    private int returnTypeIdx;
    private int parametersOff;
    private TypeItem typeItem;

    public ProtoIDItem(int shortyIdx, int returnTypeIdx, int parametersOff, TypeItem typeItem) {
        this.shortyIdx = shortyIdx;
        this.returnTypeIdx = returnTypeIdx;
        this.parametersOff = parametersOff;
        this.typeItem = typeItem;
    }


    public int getShortyIdx() {
        return shortyIdx;
    }

    public void setShortyIdx(int shortyIdx) {
        this.shortyIdx = shortyIdx;
    }

    public int getReturnTypeIdx() {
        return returnTypeIdx;
    }

    public void setReturnTypeIdx(int returnTypeIdx) {
        this.returnTypeIdx = returnTypeIdx;
    }

    public int getParametersOff() {
        return parametersOff;
    }

    public void setParametersOff(int parametersOff) {
        this.parametersOff = parametersOff;
    }

    public TypeItem getTypeItem() {
        return typeItem;
    }

    public void setTypeItem(TypeItem typeItem) {
        this.typeItem = typeItem;
    }
}
