package com.jamiexu.utils.android.dex.item;

public class TypeIDItem {
    private final int stringIndex;
    private final String description;

    public TypeIDItem(int stringIndex, String description) {
        this.stringIndex = stringIndex;
        this.description = description;
    }

    public int getStringIndex() {
        return stringIndex;
    }

    public String getDescription() {
        return description;
    }
}
