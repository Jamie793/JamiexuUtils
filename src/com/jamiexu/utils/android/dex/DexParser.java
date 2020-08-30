package com.jamiexu.utils.android.dex;

import com.jamiexu.utils.android.dex.base.BaseDexParse;
import com.jamiexu.utils.android.dex.throwable.DexStringParseException;
import com.jamiexu.utils.file.FileUtils;

public class DexParser extends BaseDexParse<DexParser> {
    public DexHeader dexHeader;
    public DexString dexString;
    public DexType dexType;

    public byte[] dexBytes;

    public DexParser(String path) {
        this.dexBytes = FileUtils.readFile(path);
    }


    public DexParser parse() throws DexStringParseException {
        if (dexBytes == null) {
            throw new DexStringParseException("read dex file faile...");
        }
        this.dexHeader = new DexHeader(dexBytes).parse();
        this.dexString = new DexString(dexBytes, dexHeader.string_ids_off, dexHeader.string_ids_size);
        try {
            dexString.parse();
        } catch (DexStringParseException e) {
            e.printStackTrace();
        }
        this.dexType = (DexType) new DexType(this.dexBytes, dexHeader.type_ids_off, dexHeader.type_ids_size, dexString.getStringDataItems()).parse();
        return this;
    }

    public DexHeader getDexHeader() {
        return dexHeader;
    }

    public DexString getDexString() {
        return dexString;
    }

    public DexType getDexType() {
        return dexType;
    }

    public boolean verifyCheckSum() {
        return DexUtils.verifyCheckSum(this.dexBytes);
    }

    public boolean verifySignature() {
        return DexUtils.verifySignature(this.dexBytes);
    }

    public void calcCheckSum() {
        DexUtils.calcCheckSum(this.dexBytes);
    }

    public void calcSignature() {
        DexUtils.calcSignature(this.dexBytes);
    }

    public void writeDex(String path) {
        DexUtils.writeDex(path, this.dexBytes);
    }


}
