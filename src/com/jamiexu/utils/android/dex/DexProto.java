package com.jamiexu.utils.android.dex;

import com.jamiexu.utils.android.dex.base.BaseDexParse;
import com.jamiexu.utils.android.dex.item.ProtoIDItem;
import com.jamiexu.utils.android.dex.item.TypeItem;
import com.jamiexu.utils.android.dex.throwable.DexStringParseException;
import com.jamiexu.utils.convert.ByteUtils;
import com.jamiexu.utils.convert.ConvertUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class DexProto extends BaseDexParse<DexProto> {


    public DexProto(DexParser dexParser) {
        super(dexParser);
        this.indexOffset = dexParser.dexHeader.proto_ids_off;
        this.indexSize = dexParser.dexHeader.proto_ids_size;
        this.stringDataItemHashMap = dexParser.getDexString().getStringDataItems();
        this.typeIDItemHashMap = dexParser.getDexType().getTypeIDItems();
        this.protoIDItemHashMap = new HashMap<>();
    }

    public HashMap<Integer, ProtoIDItem> getProtoIDItemHashMap() {
        return protoIDItemHashMap;
    }

    @Override
    public DexProto parse() throws DexStringParseException {
        super.parse();


        for (int i = 0; i < this.indexSize; i++) {
            int index = this.indexOffset + i * ProtoIDItem.getSize();
            byte[] indexBytes = ByteUtils.copyBytes(this.dexBytes, index, ProtoIDItem.getSize());
            int shortyIdxOffst = ConvertUtils.bytes2Int(ByteUtils.copyBytes(indexBytes, 0, 4));

            int returnTypeIdxOffset = ConvertUtils.bytes2Int(ByteUtils.copyBytes(indexBytes, 4, 4));
            returnTypeIdxOffset = this.typeIDItemHashMap.get(returnTypeIdxOffset).getStringIndex();

            int ParamtersOff = ConvertUtils.bytes2Int(ByteUtils.copyBytes(indexBytes, 8, 4));


            TypeItem typeItem = null;
            if (ParamtersOff != 0) {
                byte[] paramIndexBytes = ByteUtils.copyBytes(this.dexBytes, ParamtersOff, 4);
                ArrayList<Short> arrayList = new ArrayList<>();
                int size = ConvertUtils.bytes2Int(paramIndexBytes);
                ParamtersOff += 4;
                for (int j = 0; j < size; j++) {
                    short s = ConvertUtils.bytes2Short(ByteUtils.copyBytes(this.dexBytes, ParamtersOff + j * 2, 2));
                    arrayList.add(s);
                }
                typeItem = new TypeItem(size, arrayList);

            }
            this.protoIDItemHashMap.put(i, new ProtoIDItem(shortyIdxOffst, returnTypeIdxOffset, ParamtersOff, typeItem));
        }


        return this;
    }


}
