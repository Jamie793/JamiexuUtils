package com.jamiexu.utils.android.dex;

import com.jamiexu.utils.android.dex.base.BaseDexParse;
import com.jamiexu.utils.android.dex.item.MethodItem;
import com.jamiexu.utils.android.dex.item.ProtoIDItem;
import com.jamiexu.utils.android.dex.throwable.DexStringParseException;
import com.jamiexu.utils.convert.ByteUtils;
import com.jamiexu.utils.convert.ConvertUtils;

import java.util.HashMap;

public class DexMethod extends BaseDexParse<DexMethod> {

    public DexMethod(DexParser dexParser) {
        super(dexParser);
        this.dexBytes = dexParser.dexBytes;
        this.indexOffset = dexParser.getDexHeader().method_ids_off;
        this.indexSize = dexParser.getDexHeader().method_ids_size;
        this.stringDataItemHashMap = dexParser.getDexString().getStringDataItems();
        this.typeIDItemHashMap = dexParser.getDexType().getTypeIDItems();
        this.protoIDItemHashMap = dexParser.getDexProto().getProtoIDItemHashMap();
        this.methodItemHashMap = new HashMap<>();
    }

    @Override
    public DexMethod parse() throws DexStringParseException {
        super.parse();
        for (int i = 0; i < this.indexSize; i++) {
            int index = this.indexOffset + i * MethodItem.getSize();
            byte[] indexBytes = ByteUtils.copyBytes(this.dexBytes, index, MethodItem.getSize());
            short classIdx = ConvertUtils.bytes2Short(ByteUtils.copyBytes(indexBytes, 0, 2));
            short protoIdx = ConvertUtils.bytes2Short(ByteUtils.copyBytes(indexBytes, 2, 2));
            int nameIdx = ConvertUtils.bytes2Int(ByteUtils.copyBytes(indexBytes, 4, 4));
            int classIdx2 = this.typeIDItemHashMap.get(Integer.parseInt(classIdx + "")).getStringIndex();
            ProtoIDItem proto = this.protoIDItemHashMap.get(Integer.parseInt(protoIdx + ""));
            this.methodItemHashMap.put(i, new MethodItem(classIdx2, Integer.parseInt(nameIdx + ""), proto));

        }

        return this;
    }
}
