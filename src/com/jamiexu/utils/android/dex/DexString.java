package com.jamiexu.utils.android.dex;

import com.jamiexu.utils.convert.ByteUtils;
import com.jamiexu.utils.convert.ConvertUtils;
import com.jamiexu.utils.encryption.EncryptionUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DexString {
    private byte[] dexBytes;
    private final int size;
    private final int offset;
    private final ArrayList<StringItme> stringItmes;

    public DexString(byte[] dexBytes, int offset, int size) {
        this.dexBytes = dexBytes;
        this.offset = offset;
        this.size = size;
        this.stringItmes = new ArrayList<>();
        parse();
    }

    public String[] getStrings() {
        String[] strings = new String[this.stringItmes.size()];
        for (int i = 0; i < this.stringItmes.size(); i++) {
            strings[i] = new String(this.stringItmes.get(i).getData());
        }
        return strings;
    }


    public void encrypt(byte[] bytes) {
        for (StringItme stringItme : this.stringItmes) {
            byte[] enBytes = EncryptionUtils.enDes(stringItme.getData(),bytes,1);
            stringItme.setData(enBytes);
        }
    }


    public void commit() {
        StringItme firstItem = this.stringItmes.get(0);
        StringItme lastItem = this.stringItmes.get(this.stringItmes.size() - 1);
        byte[] headerBytes = ByteUtils.copyBytes(this.dexBytes, 0, firstItem.getOffset() - firstItem.getLeb128Size());
        byte[] endBytes = ByteUtils.copyBytes(this.dexBytes, lastItem.getOffset() + lastItem.getSize(),
                this.dexBytes.length - (lastItem.getOffset() + lastItem.getSize()));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int offset = headerBytes.length;
        for (StringItme s : this.stringItmes
        ) {
            try {
                byteArrayOutputStream.write(s.getLeb128());
                byteArrayOutputStream.write(s.getData());
                int len = s.getLeb128Size() + s.getSize();
                byte[] index = ConvertUtils.int2Bytes(offset);
                System.arraycopy(index, 0, headerBytes, s.getIndex(), index.length);
                offset += len;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        byte[] newStringBytes = byteArrayOutputStream.toByteArray();
        byte[] newDexBytes = new byte[headerBytes.length + newStringBytes.length + endBytes.length];
        System.arraycopy(headerBytes, 0, newDexBytes, 0, headerBytes.length);
        System.arraycopy(newStringBytes, 0, newDexBytes, headerBytes.length, newStringBytes.length);
        System.arraycopy(endBytes, 0, newDexBytes, headerBytes.length + newStringBytes.length, endBytes.length);
        this.dexBytes = newDexBytes;
    }

    public void writeDex(String path) {
        DexUtils.writeDex(path, this.dexBytes);
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

    private void parse() {
        for (int i = 0; i < this.size; i++) {
            int index = this.offset + i * 4;
            int offset = ConvertUtils.bytes2Int(ByteUtils.copyBytes(this.dexBytes, index, 4));
            getString(index, offset);
        }
    }


    private void getString(int index, int offset) {
        byte[] s = readUnsignedLeb128(offset);
        offset += s.length;
        byte[] bytes = DexUtils.getStringBytes(this.dexBytes, offset);
        this.stringItmes.add(new StringItme(index, offset, s, bytes));
    }


    static class StringItme {
        private final int index;
        private int offset;
        private int size;
        private int leb128Size;
        private final byte[] leb128;
        private byte[] data;

        public StringItme(int index, int offset, byte[] leb128, byte[] data) {
            this.index = index;
            this.offset = offset;
            this.leb128 = leb128;
            this.leb128Size = leb128.length;
            this.size = data.length + 1;
            this.data = new byte[this.size];
            System.arraycopy(data, 0, this.data, 0, data.length);
        }

        public int getLeb128Size() {
            return leb128Size;
        }

        public void setLeb128Size(int leb128Size) {
            this.leb128Size = leb128Size;
        }

        public byte[] getLeb128() {
            return leb128;
        }

        public int getIndex() {
            return index;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public int getSize() {
            return size;
        }

        public byte[] getData() {
            return data;
        }

        public void setData(byte[] data) {
            this.data = data;
            this.size = this.data.length;
        }
    }
}
