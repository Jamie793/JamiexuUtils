package com.jamiexu.utils.android.dex;

import com.jamiexu.utils.android.dex.base.BaseDexParse;
import com.jamiexu.utils.android.dex.item.StringDataItem;
import com.jamiexu.utils.android.dex.item.TypeIDItem;
import com.jamiexu.utils.convert.ByteUtils;
import com.jamiexu.utils.convert.ConvertUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DexType extends BaseDexParse<DexType> {
    private final byte[] dexBytes;
    private final int indexOffset;
    private final int indexSize;
    private final ArrayList<TypeIDItem> typeIDItems;
    private final HashMap<Integer, StringDataItem> stringDataItemHashMap;

    public DexType(byte[] dexBytes, int indexOffset, int indexSize, HashMap<Integer, StringDataItem> stringDataItemHashMap) {
        this.dexBytes = dexBytes;
        this.indexOffset = indexOffset;
        this.indexSize = indexSize;
        this.typeIDItems = new ArrayList<>();
        this.stringDataItemHashMap = stringDataItemHashMap;
    }

    public DexType parse() {
        int is = 0;
        for (int i = 0; i < this.indexSize; i++) {
            byte[] indexBytes = ByteUtils.copyBytes(this.dexBytes, this.indexOffset + i * 4, 4);
            int offset = ConvertUtils.bytes2Int(indexBytes);
            indexBytes = ByteUtils.copyBytes(this.dexBytes, offset, 4);
            int index = ConvertUtils.bytes2Int(indexBytes);
            StringDataItem stringDataItem = this.stringDataItemHashMap.get(index);
//            if (stringDataItem != null) {
                System.out.println(stringDataItem);
//            }
//            this.typeIDItems.add(new TypeIDItem(index, stringDataItem.getData()));
        }
        return this;
    }


    private byte[] readUnsignedLeb128(int offset) {
        List<Byte> byteAryList = new ArrayList<>();
        byte bytes = ByteUtils.copyBytes(this.dexBytes, offset, 1)[0];
        byte highBit = (byte) (bytes & 0x80);
        byteAryList.add(bytes);
        offset++;
        while (highBit != 0) {
            bytes = ByteUtils.copyBytes(this.dexBytes, offset, 1)[0];
            highBit = (byte) (bytes & 0x80);
            offset++;
            byteAryList.add(bytes);
        }
        byte[] byteAry = new byte[byteAryList.size()];
        for (int j = 0; j < byteAryList.size(); j++) {
            byteAry[j] = byteAryList.get(j);
        }
        return byteAry;
    }


}
