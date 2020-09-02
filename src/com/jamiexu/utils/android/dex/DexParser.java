package com.jamiexu.utils.android.dex;

import com.jamiexu.utils.android.dex.base.BaseDexParse;
import com.jamiexu.utils.android.dex.throwable.DexStringParseException;
import com.jamiexu.utils.file.FileUtils;

public class DexParser{
    public DexHeader dexHeader;
    public DexString dexString;
    public DexType dexType;
    public DexProto dexProto;
    public DexField dexField;
    public DexMethod dexMethod;
    public DexClass dexClass;

    public byte[] dexBytes;

    public DexParser(String path) {
        this.dexBytes = FileUtils.readFile(path);
    }


    public DexParser parse() throws DexStringParseException {
        if (dexBytes == null) {
            throw new DexStringParseException("read dex file faile...");
        }
        try {
            this.dexHeader = new DexHeader(dexBytes);
            dexHeader.parse();
            this.dexString = new DexString(this);
            dexString.parse();
            this.dexType = new DexType(this);
            dexType.parse();
            this.dexProto = new DexProto(this);
            dexProto.parse();
            this.dexField = new DexField(this);
            this.dexField.parse();
            this.dexMethod = new DexMethod(this);
            this.dexMethod.parse();
            this.dexClass = new DexClass(this);
            System.out.println(dexClass.parse());

        } catch (DexStringParseException e) {
            e.printStackTrace();
        }

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

    public DexProto getDexProto() {
        return dexProto;
    }

    public DexField getDexField() {
        return dexField;
    }

    public DexMethod getDexMethod() {
        return dexMethod;
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
