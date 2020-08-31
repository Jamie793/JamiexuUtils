package com.jamiexu.utils.android.dex;

import com.jamiexu.utils.android.dex.base.BaseDexParse;
import com.jamiexu.utils.android.dex.item.StringDataItem;
import com.jamiexu.utils.android.dex.throwable.DexStringParseException;
import com.jamiexu.utils.convert.ByteUtils;
import com.jamiexu.utils.convert.ConvertUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DexString extends BaseDexParse<DexString> {

    private final byte[] dexBytes;
    private final int indexOffset;
    private final int indexSize;
    private final HashMap<Integer, StringDataItem> stringDataItems;

    public DexString(DexParser dexParser) {
        this.dexBytes = dexParser.dexBytes;
        this.indexOffset = dexParser.getDexHeader().string_ids_off;
        this.indexSize = dexParser.getDexHeader().string_ids_size;
        this.stringDataItems = new HashMap<>();
    }

    public DexString parse() throws DexStringParseException {
        if (this.dexBytes == null)
            throw new DexStringParseException("dexBytes is null...");
        else if (this.indexSize < 0) {
            throw new DexStringParseException("offset exception...");
        }

        parseStringDataItem();
        return this;
    }

    public String[] getStringDatas() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (Map.Entry<Integer, StringDataItem> stringDataItemEntry : this.stringDataItems.entrySet()) {
            arrayList.add(stringDataItemEntry.getValue().getData());
        }
        return arrayList.toArray(new String[0]);
    }

    public HashMap<Integer, StringDataItem> getStringDataItems() {
        return this.stringDataItems;
    }

    private void parseStringDataItem() {
        for (int i = 0; i < this.indexSize; i++) {
            byte[] indexBytes = ByteUtils.copyBytes(this.dexBytes, this.indexOffset + i * 4, 4);
            int strOffset = ConvertUtils.bytes2Int(indexBytes);

            byte[] uleb128 = readUnsignedLeb128(strOffset);
            int size = decodeUleb128(uleb128);

            byte[] strBytes = ByteUtils.copyBytes(this.dexBytes, strOffset + uleb128.length, size);
            while (strBytes.length != 0 && strBytes[strBytes.length - 1] != 0) {
                strBytes = ByteUtils.copyBytes(this.dexBytes, strOffset + uleb128.length, size++);
            }

            String str = new String(strBytes, 0, strBytes.length, StandardCharsets.UTF_8);
            this.stringDataItems.put(i, new StringDataItem(str, str.length()));
        }
    }

    private static int decodeUleb128(byte[] byteAry) {
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


}