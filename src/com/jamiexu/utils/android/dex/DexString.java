package com.jamiexu.utils.android.dex;

import com.jamiexu.utils.convert.ByteUtils;
import com.jamiexu.utils.convert.ConvertUtils;

import java.util.ArrayList;
import java.util.List;

public class DexString {
    public byte[] dexBytes;
    public int size;
    public int offset;
    public ArrayList<String> arrayList;

    public DexString(byte[] dexBytes, int offset, int size) {
        this.dexBytes = dexBytes;
        this.offset = offset;
        this.size = size;
        this.arrayList = new ArrayList<>();
        parse();
    }

    private void parse() {
        System.out.println(getStrings());
//        System.out.println(this.dexBytes.length);

    }


    private byte[] readUnsignedLeb128(int offset) {
        List<Byte> byteAryList = new ArrayList<>();
        byte bytes = ByteUtils.copyBytes(this.dexBytes, offset, 1)[0];
        byte highBit = (byte) (bytes & 0x80);
        byteAryList.add(bytes);
        offset++;
        while (highBit != 0) {
            bytes = ByteUtils.copyBytes(this.dexBytes, offset, 1)[0];
            highBit = (byte) (bytes & 0x80);
            offset++;
            byteAryList.add(bytes);
        }
        byte[] byteAry = new byte[byteAryList.size()];
        for (int j = 0; j < byteAryList.size(); j++) {
            byteAry[j] = byteAryList.get(j);
        }
        return byteAry;
    }


    public static int decodeUleb128(byte[] byteAry) {
        int index = 0, cur;
        int result = byteAry[index];
        index++;

        if (byteAry.length == 1) {
            return result;
        }

        if (byteAry.length == 2) {
            cur = byteAry[index];
            index++;
            result = (result & 0x7f) | ((cur & 0x7f) << 7);
            return result;
        }

        if (byteAry.length == 3) {
            cur = byteAry[index];
            index++;
            result |= (cur & 0x7f) << 14;
            return result;
        }

        if (byteAry.length == 4) {
            cur = byteAry[index];
            index++;
            result |= (cur & 0x7f) << 21;
            return result;
        }

        if (byteAry.length == 5) {
            cur = byteAry[index];
            index++;
            result |= cur << 28;
            return result;
        }

        return result;

    }


    public ArrayList<String> getStrings() {
        ArrayList<String> strignIndex = new ArrayList<>();
        for (int i = 0; i < this.size; i++) {
            int index = ConvertUtils.bytes2Int(ByteUtils.copyBytes(this.dexBytes, this.offset + i * 4, 4));
            byte[] s = readUnsignedLeb128(index);
            strignIndex.add(getString(index + s.length, decodeUleb128(s)));
        }
        return strignIndex;
    }

    private String getString(int offset, int len) {
        return new String(this.dexBytes, offset + len, len);
    }


}
