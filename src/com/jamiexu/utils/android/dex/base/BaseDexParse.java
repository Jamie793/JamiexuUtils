package com.jamiexu.utils.android.dex.base;

import com.jamiexu.utils.android.dex.DexParser;
import com.jamiexu.utils.android.dex.item.*;
import com.jamiexu.utils.android.dex.throwable.DexStringParseException;

import java.util.HashMap;

public abstract class BaseDexParse<T> {
    protected DexParser dexParser;
    protected byte[] dexBytes;
    protected int indexOffset;
    protected int indexSize;
    protected HashMap<Integer, StringDataItem> stringDataItemHashMap;
    protected HashMap<Integer, TypeIDItem> typeIDItemHashMap;
    protected HashMap<Integer, ProtoIDItem> protoIDItemHashMap;
    protected HashMap<Integer, FieldItem> fieldItemHashMap;
    protected HashMap<Integer, MethodItem> methodItemHashMap;


    public BaseDexParse(DexParser dexParser) {
        this.dexParser = dexParser;
        this.dexBytes = dexParser.dexBytes;
    }


    protected T parse() throws DexStringParseException {
        if (this.dexBytes == null)
            throw new DexStringParseException("dexBytes is null...");
        else if (this.indexSize < 0) {
            throw new DexStringParseException("offset exception...");
        }
        return null;
    }
}
