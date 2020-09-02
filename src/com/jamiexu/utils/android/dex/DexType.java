package com.jamiexu.utils.android.dex;

import com.jamiexu.utils.android.dex.base.BaseDexParse;
import com.jamiexu.utils.android.dex.item.StringDataItem;
import com.jamiexu.utils.android.dex.item.TypeIDItem;
import com.jamiexu.utils.android.dex.item.TypeItem;
import com.jamiexu.utils.android.dex.throwable.DexStringParseException;
import com.jamiexu.utils.convert.ByteUtils;
import com.jamiexu.utils.convert.ConvertUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DexType extends BaseDexParse<DexType> {

    public DexType(DexParser dexParser) {
        super(dexParser);
        this.dexBytes = dexParser.dexBytes;
        this.indexOffset = dexParser.getDexHeader().type_ids_off;
        this.indexSize = dexParser.getDexHeader().type_ids_size;
        this.stringDataItemHashMap = dexParser.getDexString().getStringDataItems();
        this.typeIDItemHashMap = new HashMap<>();
    }

    @Override
    public DexType parse() throws DexStringParseException {
        super.parse();
        for (int i = 0; i < this.indexSize; i++) {
            byte[] indexBytes = ByteUtils.copyBytes(this.dexBytes, this.indexOffset + i * TypeIDItem.getSize(),
                    TypeIDItem.getSize());
            int offset = ConvertUtils.bytes2Int(indexBytes);
            StringDataItem stringDataItem = this.stringDataItemHashMap.get(offset);
            this.typeIDItemHashMap.put(i, new TypeIDItem(offset, stringDataItem.getData()));
        }
        return this;
    }

    public HashMap<Integer, TypeIDItem> getTypeIDItems() {
        return this.typeIDItemHashMap;
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
