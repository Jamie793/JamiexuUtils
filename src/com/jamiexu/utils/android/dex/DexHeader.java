package com.jamiexu.utils.android.dex;

import com.jamiexu.utils.android.dex.throwable.DexStringParseException;
import com.jamiexu.utils.convert.ByteUtils;
import com.jamiexu.utils.convert.ConvertUtils;

public class DexHeader {

    //    dexBytes
    public byte[] dexBytes;

    //    魔法值
    public byte[] magic;  //8 bytes

    //    文件校验采用alder32算法，把magic和checksum除外的数据进行算法
    public byte[] checksum;  //20 bytes

    //    文件校验采用sha-1算法，把magic、checksum和signat除外的数据进行算法
    public byte[] signature;

    //    dex大小
    public int file_size;

    //    header头大小
    public int header_size;

    //    大小端标签，一般固定为常量0x1234 5678
    public byte[] endian_tag;

    //    链数据大小
    public int link_size;

    //    链偏移值
    public int link_off;

    //    map_item的偏移地址，该item属于data区里的内值要大于等于data_off打大小
    public int map_off;

    //    字符常量池的大小
    public int string_ids_size;

    //    字符常量池的偏移
    public int string_ids_off;

    //    dex类型数据结构的大小
    public int type_ids_size;

    //    dex类型数据结构的偏移
    public int type_ids_off;

    //    数据信息数据结构大小
    public int proto_ids_size;

    //    数据信息数据结构偏移
    public int proto_ids_off;

    //    字段信息的大小
    public int field_ids_size;

    //    字段信息的偏移
    public int field_ids_off;

    //    方法信息的大小
    public int method_ids_size;

    //    方法信息的偏移
    public int method_ids_off;

    //    类信息的大小
    public int class_defs_size;

    //    类信息的偏移
    public int class_defs_off;

    //    数据区域的结构信息的大小
    public int data_size;

    //    数据区域的结构信息的偏移值
    public int data_off;

    private int currentOffset;

    public DexHeader(byte[] bytes) {
        this.dexBytes = bytes;
        this.currentOffset = 0;
    }


    public DexHeader parse() throws DexStringParseException {
        if (this.dexBytes == null)
            throw new DexStringParseException("dexBytes is null...");

        this.magic = read(8);
        this.checksum = read(4);
        this.signature = read(20);
        this.file_size = ConvertUtils.bytes2Int(read(4));
        this.header_size = ConvertUtils.bytes2Int(read(4));
        this.endian_tag = read(4);
        this.link_size = ConvertUtils.bytes2Int(read(4));
        this.link_off = ConvertUtils.bytes2Int(read(4));
        this.map_off = ConvertUtils.bytes2Int(read(4));
        this.string_ids_size = ConvertUtils.bytes2Int(read(4));
        this.string_ids_off = ConvertUtils.bytes2Int(read(4));
        this.type_ids_size = ConvertUtils.bytes2Int(read(4));
        this.type_ids_off = ConvertUtils.bytes2Int(read(4));
        this.proto_ids_size = ConvertUtils.bytes2Int(read(4));
        this.proto_ids_off = ConvertUtils.bytes2Int(read(4));
        this.field_ids_size = ConvertUtils.bytes2Int(read(4));
        this.field_ids_off = ConvertUtils.bytes2Int(read(4));
        this.method_ids_size = ConvertUtils.bytes2Int(read(4));
        this.method_ids_off = ConvertUtils.bytes2Int(read(4));
        this.class_defs_size = ConvertUtils.bytes2Int(read(4));
        this.class_defs_off = ConvertUtils.bytes2Int(read(4));
        this.data_size = ConvertUtils.bytes2Int(read(4));
        this.data_off = ConvertUtils.bytes2Int(read(4));
        return this;
    }

    private byte[] read(int len) {
        byte[] bytes = ByteUtils.copyBytes(this.dexBytes, this.currentOffset, len);
        this.currentOffset += len;
        return bytes;
    }


    @Override
    public String toString() {
        return "DexHeader\n{" +
                "\nmagic:" + ConvertUtils.bytesToHex(magic, ' ') +
                "\nchecksum:" + ConvertUtils.bytesToHex(checksum, ' ') +
                "\nsiganature:" + ConvertUtils.bytesToHex(signature, ' ') +
                "\nfile_size:" + file_size +
                "\nheader_size:" + header_size +
                "\nendian_tag:" + ConvertUtils.bytes2Hex(endian_tag, ' ') +
                "\nlink_size:" + link_size +
                "\nlink_off:" + link_off +
                "\nmap_off:" + map_off +
                "\nstring_ids_size:" + string_ids_size +
                "\nstring_ids_off:" + string_ids_off +
                "\ntype_ids_size:" + type_ids_size +
                "\ntype_ids_off:" + type_ids_off +
                "\nproto_ids_size:" + proto_ids_size +
                "\nproto_ids_off:" + proto_ids_off +
                "\nfield_ids_size:" + field_ids_size +
                "\nfield_ids_off:" + field_ids_off +
                "\nmethod_ids_size:" + method_ids_size +
                "\nmethod_ids_off:" + method_ids_off +
                "\nclass_defs_size:" + class_defs_size +
                "\nclass_defs_off:" + class_defs_off +
                "\ndata_size:" + data_size +
                "\ndata_off:" + data_off +
                "\n}";
    }
}
