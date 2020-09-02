package com.jamiexu.utils.android.dex.item;

public class FieldItem {
    private int classTypeIndex;
    private int FieldNameIndex;
    private int FieldTypeIndex;

    public FieldItem(int classTypeIndex, int FieldNameIndex, int FieldTypeIndex) {
        this.classTypeIndex = classTypeIndex;
        this.FieldNameIndex = FieldNameIndex;
        this.FieldTypeIndex = FieldTypeIndex;
    }

    public static int getSize(){
        return 2+2+4;
    }

    public int getClassTypeIndex() {
        return classTypeIndex;
    }

    public void setClassTypeIndex(int classTypeIndex) {
        this.classTypeIndex = classTypeIndex;
    }

    public int getFieldNameIndex() {
        return FieldNameIndex;
    }

    public void setFieldNameIndex(int fieldNameIndex) {
        FieldNameIndex = fieldNameIndex;
    }

    public int getFieldTypeIndex() {
        return FieldTypeIndex;
    }

    public void setFieldTypeIndex(int fieldTypeIndex) {
        FieldTypeIndex = fieldTypeIndex;
    }
}
