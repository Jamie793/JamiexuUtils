package com.jamiexu.utils.android.elf;

import com.jamiexu.utils.convert.ByteUtils;

public class ProgramHeader {
    byte[] p_type;
    byte[] p_offset;
    byte[] p_vaddr;
    byte[] p_paddr;
    byte[] p_filesz;
    byte[] p_memsz;
    byte[] p_flags;
    byte[] p_align;

    public ProgramHeader(byte[] bytes) {
        this.p_type = ByteUtils.copyBytes(bytes, 0, 4);
        this.p_offset = ByteUtils.copyBytes(bytes, 4, 4);
        this.p_vaddr = ByteUtils.copyBytes(bytes, 8, 4);
        this.p_paddr = ByteUtils.copyBytes(bytes, 16, 4);
        this.p_filesz = ByteUtils.copyBytes(bytes, 20, 4);
        this.p_memsz = ByteUtils.copyBytes(bytes, 24, 4);
        this.p_flags = ByteUtils.copyBytes(bytes, 26, 4);
        this.p_align = ByteUtils.copyBytes(bytes, 26, 4);
    }

    public void print() {
        System.out.println("+++++++++++++++++Program Header+++++++++++++++++++");
        System.out.println("Program Header:");
        Class<?> clas = ProgramHeader.class;
        ElfParserUtils.print(clas, this);
    }
}
