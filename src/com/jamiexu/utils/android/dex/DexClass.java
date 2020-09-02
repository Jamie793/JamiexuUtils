package com.jamiexu.utils.android.dex;

import com.jamiexu.utils.android.dex.base.BaseDexParse;
import com.jamiexu.utils.android.dex.item.ClassDefItem;
import com.jamiexu.utils.android.dex.throwable.DexStringParseException;
import com.jamiexu.utils.convert.ByteUtils;
import com.jamiexu.utils.convert.ConvertUtils;

public class DexClass extends BaseDexParse<DexClass> {

    public DexClass(DexParser dexParser) {
        super(dexParser);
        this.indexOffset = dexParser.dexHeader.class_defs_off;
        this.indexSize = dexParser.dexHeader.class_defs_size;
    }

    @Override
    public DexClass parse() throws DexStringParseException {
        super.parse();
        for (int i = 0; i < this.indexSize; i++) {
            byte[] bytes = ByteUtils.copyBytes(this.dexBytes, this.indexOffset + i * ClassDefItem.getSize(),
                    ClassDefItem.getSize());

            int classIdx = ConvertUtils.bytes2Int(ByteUtils.copyBytes(bytes, 0, 4));
            int accessFlags = ConvertUtils.bytes2Int(ByteUtils.copyBytes(bytes, 4, 4));
            int superClassIdx = ConvertUtils.bytes2Int(ByteUtils.copyBytes(bytes, 8, 4));
            int interfaceOff = ConvertUtils.bytes2Int(ByteUtils.copyBytes(bytes, 12, 4));
            int sourceFileIdx = ConvertUtils.bytes2Int(ByteUtils.copyBytes(bytes, 16, 4));
            int annotationsOff = ConvertUtils.bytes2Int(ByteUtils.copyBytes(bytes, 20, 4));
            int classDataOff = ConvertUtils.bytes2Int(ByteUtils.copyBytes(bytes, 24, 4));
            int staticvaluesOff = ConvertUtils.bytes2Int(ByteUtils.copyBytes(bytes, 28, 4));

            System.out.println(classIdx);
            System.out.println(accessFlags);
            System.out.println(superClassIdx);
            System.out.println(interfaceOff);
            System.out.println(sourceFileIdx);
            System.out.println(annotationsOff);
            System.out.println(classDataOff);
            System.out.println(staticvaluesOff);
            System.out.println();

        }
        return this;
    }
}
