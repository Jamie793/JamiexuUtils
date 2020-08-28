package com.jamiexu.utils.android.dex;

import com.jamiexu.utils.convert.ByteUtils;
import com.jamiexu.utils.convert.ConvertUtils;
import com.jamiexu.utils.encryption.EncryptionUtils;
import com.jamiexu.utils.file.FileUtils;

public class DexParser {
    public DexHeader dexHeader;
    public DexString dexString;

    public byte[] dexBytes;

    public DexParser parse(String dexPath) {
        byte[] dexBytes = FileUtils.readFile(dexPath);
        if (dexBytes == null) {
            System.out.println("read dex file faile...");
            return null;
        }
        this.dexBytes = dexBytes;
        this.dexHeader = new DexHeader(dexBytes);
        this.dexString = new DexString(dexBytes, dexHeader.string_ids_off,dexHeader.string_ids_size);

        return this;
    }

    public DexHeader getDexHeader() {
        return dexHeader;
    }

    public boolean verifyCheckSum() {
        byte[] bytes = ConvertUtils.longToBytes(EncryptionUtils.getAlder32(ByteUtils.copyBytes(this.dexBytes, 12, this.dexBytes.length - 12)));
        bytes = ByteUtils.copyBytes(bytes, 4, 4);
        String checksumHex = ConvertUtils.bytesToHex(bytes).toUpperCase();
        String checksumHex2 = ConvertUtils.bytes2Hex(this.getDexHeader().checksum).toUpperCase();
        return checksumHex2.equals(checksumHex);
    }

    public boolean verifySignature() {
        byte[] bytes = ByteUtils.copyBytes(this.dexBytes, 32, this.dexBytes.length - 32);
        bytes = EncryptionUtils.getSha1(bytes);
        String hex = ConvertUtils.bytesToHex(bytes).toUpperCase();
        String hex2 = ConvertUtils.bytesToHex(this.getDexHeader().signature).toUpperCase();
        return hex.equals(hex2);
    }
}
