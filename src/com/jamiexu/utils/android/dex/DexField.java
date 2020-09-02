package com.jamiexu.utils.android.dex;

import com.jamiexu.utils.android.dex.base.BaseDexParse;
import com.jamiexu.utils.android.dex.item.FieldItem;
import com.jamiexu.utils.android.dex.throwable.DexStringParseException;
import com.jamiexu.utils.convert.ByteUtils;
import com.jamiexu.utils.convert.ConvertUtils;

import java.util.HashMap;

public class DexField extends BaseDexParse<DexField> {

    public DexField(DexParser dexParser) {
        super(dexParser);
        this.dexBytes = dexParser.dexBytes;
        this.indexOffset = dexParser.getDexHeader().field_ids_off;
        this.indexSize = dexParser.getDexHeader().field_ids_size;
        this.stringDataItemHashMap = dexParser.getDexString().getStringDataItems();
        this.typeIDItemHashMap = dexParser.getDexType().getTypeIDItems();
        this.fieldItemHashMap = new HashMap<>();
    }

    @Override
    public DexField parse() throws DexStringParseException {
        super.parse();
        for (int i = 0; i < this.indexSize; i++) {
            int index = this.indexOffset + i * FieldItem.getSize();
            byte[] indexBytes = ByteUtils.copyBytes(this.dexBytes, index, FieldItem.getSize());
            short classIdx = ConvertUtils.bytes2Short(ByteUtils.copyBytes(indexBytes, 0, 2));
            short typeIdx = ConvertUtils.bytes2Short(ByteUtils.copyBytes(indexBytes, 2, 2));
            int nameIdx = ConvertUtils.bytes2Int(ByteUtils.copyBytes(indexBytes, 4, 4));
            int classIdx2 = this.typeIDItemHashMap.get(Integer.parseInt(classIdx + "")).getStringIndex();
            int typeIdx2 = this.typeIDItemHashMap.get(Integer.parseInt(typeIdx + "")).getStringIndex();
            this.fieldItemHashMap.put(i, new FieldItem(classIdx2, nameIdx, typeIdx2));
//            System.out.println("Class type:" + this.stringDataItemHashMap.get(classIdx2).getData());
//            System.out.println("Field type:" + this.stringDataItemHashMap.get(typeIdx2).getData());
//            System.out.println("Field name:" + this.stringDataItemHashMap.get(nameIdx).getData());
//            System.out.println();

        }

        return this;
    }
}
