package com.jamiexu.utils.android.dex;

import com.jamiexu.utils.convert.ByteUtils;
import com.jamiexu.utils.convert.ConvertUtils;

import java.util.ArrayList;

public class DexType {
    private final byte[] dexBytes;
    private final int offset;
    private final int size;
    private final ArrayList<TypeItem> typeItems;

    public DexType(byte[] dexBytes, int offset, int size) {
        this.dexBytes = dexBytes;
        this.offset = offset;
        this.size = size;
        this.typeItems = new ArrayList<>();
        parse();
//        System.out.println(this.offset);
    }

    public void parse() {
        for (int i = 0; i < this.size; i++) {
            int index = this.offset + i * 4;
            int offset = ConvertUtils.bytes2Int(ByteUtils.copyBytes(this.dexBytes, index, 4));
            this.typeItems.add(new TypeItem(index, offset));
        }
    }

    public ArrayList<TypeItem> getTypeItems() {
        return typeItems;
    }


    static class TypeItem {
        private int index;
        private int offset;


        public TypeItem(int index, int offset) {
            this.index = index;
            this.offset = offset;
        }


        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

    }

}
