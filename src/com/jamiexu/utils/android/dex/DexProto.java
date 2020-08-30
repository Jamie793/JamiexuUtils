package com.jamiexu.utils.android.dex;

import com.jamiexu.utils.android.dex.base.BaseDexParse;
import com.jamiexu.utils.android.dex.throwable.DexStringParseException;

public class DexProto extends BaseDexParse<DexProto> {

    private byte[] dexBytes;
    private int indexOffset;
    private int indexSize;

    public DexProto(byte[] dexBytes, int indexOffset, int indexSize) {
        this.dexBytes = dexBytes;
        this.indexOffset = indexOffset;
        this.indexSize = indexSize;
    }

    @Override
    public DexProto parse() throws DexStringParseException {
        if (this.dexBytes == null)
            throw new DexStringParseException("dexBytes is null...");
        else if (this.indexSize < 0) {
            throw new DexStringParseException("offset exception...");
        }
        return this;
    }
}
