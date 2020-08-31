package com.jamiexu.utils.android.dex;

import com.jamiexu.utils.android.dex.base.BaseDexParse;
import com.jamiexu.utils.android.dex.item.StringDataItem;
import com.jamiexu.utils.android.dex.item.TypeIDItem;
import com.jamiexu.utils.android.dex.throwable.DexStringParseException;
import com.jamiexu.utils.convert.ByteUtils;
import com.jamiexu.utils.convert.ConvertUtils;

import java.util.HashMap;

public class DexField extends BaseDexParse<DexField> {
    private byte[] dexBytes;
    private int indexOffset;
    private int indexSize;
    private HashMap<Integer, StringDataItem> stringDataItemHashMap;
    private HashMap<Integer, TypeIDItem> typeIDItemHashMap;

    public DexField(DexParser dexParser) {
        this.dexBytes = dexParser.dexBytes;
        this.indexOffset = dexParser.getDexHeader().field_ids_off;
        this.indexSize = dexParser.getDexHeader().field_ids_size;
        this.stringDataItemHashMap = dexParser.getDexString().getStringDataItems();
        this.typeIDItemHashMap = dexParser.getDexType().getTypeIDItems();
    }

    @Override
    public DexField parse() throws DexStringParseException {
        if (this.dexBytes == null)
            throw new DexStringParseException("dexBytes is null...");
        else if (this.indexSize < 0) {
            throw new DexStringParseException("offset exception...");
        }


        for (int i = 0; i < this.indexSize; i++) {
            int index = this.indexOffset + i * 8;
            byte[] indexBytes = ByteUtils.copyBytes(this.dexBytes, index, 8);
            short classIdx = ConvertUtils.bytes2Short(ByteUtils.copyBytes(indexBytes, 0, 2));
            short typeIdx = ConvertUtils.bytes2Short(ByteUtils.copyBytes(indexBytes, 2, 2));
            int nameIdx = ConvertUtils.bytes2Int(ByteUtils.copyBytes(indexBytes, 4, 4));
            int classIdx2 = this.typeIDItemHashMap.get(Integer.parseInt(classIdx+"")).getStringIndex();
            int typeIdx2 = this.typeIDItemHashMap.get(Integer.parseInt(typeIdx+"")).getStringIndex();
//            System.out.println("Class type:" + this.stringDataItemHashMap.get(classIdx2).getData());
//            System.out.println("Field type:" + this.stringDataItemHashMap.get(typeIdx2).getData());
//            System.out.println("Field name:" + this.stringDataItemHashMap.get(nameIdx).getData());
//            System.out.println();
        }

        return this;
    }
}
