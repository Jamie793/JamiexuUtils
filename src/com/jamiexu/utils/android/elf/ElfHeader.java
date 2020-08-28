package com.jamiexu.utils.android.elf;

import com.jamiexu.utils.convert.ByteUtils;
import com.jamiexu.utils.convert.ConvertUtils;

import java.util.Arrays;


public class ElfHeader {

    /**
     * e_phoff:是程序头(Program Header)内容在整个文件的偏移值，可以用这个偏移值来定位程序头的开始位置，用于解析程序头信息
     * e_shoff:是段头(Section Header)内容在这整个文件的偏移值，可以用这个偏移值来定位段头的开始位置，用于解析段头信息
     * e_phnum:程序头的个数
     * e_shnum:段头的个数
     * e_shstrndx:是String段在整个段列表中的索引值，用于后面定位string段的位置
     */

    public byte[] e_ident;

    public short e_type;

    public short e_machine;

    public int e_version;

    public int e_entry;

    public int e_phoff;

    public int e_shoff;

    public int e_flags;

    public short e_ehsize;

    public short e_phentsize;

    public short e_phnum;

    public short e_shentsize;

    public short e_shnum;

    public short e_shstrndx;


    public ElfHeader(byte[] elfBytes) {
        this.e_ident = ByteUtils.copyBytes(elfBytes, 0, 16);
        this.e_type = ConvertUtils.bytesToShort(ByteUtils.copyBytes(elfBytes, 16, 2));
        this.e_machine = ConvertUtils.bytesToShort(ByteUtils.copyBytes(elfBytes, 18, 2));
        this.e_version = ConvertUtils.bytesToInt(ByteUtils.copyBytes(elfBytes, 20, 4));
        this.e_entry = ConvertUtils.bytesToInt(ByteUtils.copyBytes(elfBytes, 24, 4));
        this.e_phoff = ConvertUtils.bytesToInt(ByteUtils.copyBytes(elfBytes, 28, 4));
        this.e_shoff = ConvertUtils.bytesToInt(ByteUtils.copyBytes(elfBytes, 32, 4));
        this.e_flags = ConvertUtils.bytesToInt(ByteUtils.copyBytes(elfBytes, 36, 4));
        this.e_ehsize = ConvertUtils.bytesToShort(ByteUtils.copyBytes(elfBytes, 40, 2));
        this.e_phentsize = ConvertUtils.bytesToShort(ByteUtils.copyBytes(elfBytes, 42, 2));
        this.e_phnum = ConvertUtils.bytesToShort(ByteUtils.copyBytes(elfBytes, 44, 2));
        this.e_shentsize = ConvertUtils.bytesToShort(ByteUtils.copyBytes(elfBytes, 46, 2));
        this.e_shnum = ConvertUtils.bytesToShort(ByteUtils.copyBytes(elfBytes, 48, 2));
        this.e_shstrndx = ConvertUtils.bytesToShort(ByteUtils.copyBytes(elfBytes, 50, 2));
    }

    public void print() {
        System.out.println("+++++++++++++++++ELF Header+++++++++++++++++++");
        System.out.println("ELF Header:");
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return "ElfHeader{" +
                "\ne_ident:" + Arrays.toString(e_ident) +
                "\n e_type:" + e_type +
                "\n e_machine:" + e_machine +
                "\n e_version:" + e_version +
                "\n e_entry:" + e_entry +
                "\n e_phoff:" + e_phoff +
                "\n e_shoff:" + e_shoff +
                "\n e_flags:" + e_flags +
                "\n e_ehsize:" + e_ehsize +
                "\n e_phentsize:" + e_phentsize +
                "\n e_phnum:" + e_phnum +
                "\n e_shentsize:" + e_shentsize +
                "\n e_shnum:" + e_shnum +
                "\n e_shstrndx:" + e_shstrndx +
                "\n}";
    }
}
