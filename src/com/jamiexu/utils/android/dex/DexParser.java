package com.jamiexu.utils.android.dex;

import com.jamiexu.utils.file.FileUtils;

public class DexParser {
    public DexHeader dexHeader;
    public DexString dexString;
    public DexType dexType;

    public byte[] dexBytes;

    public DexParser parse(String dexPath) {
        byte[] dexBytes = FileUtils.readFile(dexPath);
        if (dexBytes == null) {
            System.out.println("read dex file faile...");
            return null;
        }
        this.dexBytes = dexBytes;
        this.dexHeader = new DexHeader(dexBytes);
        this.dexString = new DexString(dexBytes, dexHeader.string_ids_off, dexHeader.string_ids_size);
        this.dexType = new DexType(dexBytes, dexHeader.type_ids_off, dexHeader.type_ids_size);
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
