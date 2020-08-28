package com.jamiexu.utils.android.elf;

import com.jamiexu.utils.convert.ByteUtils;

import java.util.ArrayList;

public class SectionHeader {
    private ArrayList<Section> sections;
    private final int SIZE = 40;
    private final int OFFSET = 40;


    public SectionHeader(byte[] bytes, short size) {
        this.sections = new ArrayList<>();
        bytes = ByteUtils.copyBytes(bytes, OFFSET, bytes.length - 52);
        byte[] des = new byte[this.SIZE];
        for (int i = 0; i < size; i++) {
            System.arraycopy(bytes, i * this.SIZE + OFFSET, des, 0, this.SIZE);
            this.sections.add(new Section(des));
        }

    }

    public void print() {
        System.out.println("+++++++++++++++++Section Header+++++++++++++++++++");
        System.out.println("Section Header:");
        for (int i = 0; i < this.sections.size(); i++) {
            System.out.println("The " + i + " Section Header:");
            ElfParserUtils.print(Section.class, sections.get(i));
        }
    }

    static class Section {
        public byte[] sh_name;
        public byte[] sh_type;
        public byte[] sh_flags;
        public byte[] sh_addr;
        public byte[] sh_offset;
        public byte[] sh_size;
        public byte[] sh_link;
        public byte[] sh_info;
        public byte[] sh_addralign;
        public byte[] sh_entsize;

        public Section(byte[] bytes) {
            this.sh_name = ByteUtils.copyBytes(bytes, 0, 4);
            this.sh_type = ByteUtils.copyBytes(bytes, 0, 4);
            this.sh_flags = ByteUtils.copyBytes(bytes, 0, 4);
            this.sh_addr = ByteUtils.copyBytes(bytes, 0, 4);
            this.sh_offset = ByteUtils.copyBytes(bytes, 0, 4);
            this.sh_size = ByteUtils.copyBytes(bytes, 0, 4);
            this.sh_link = ByteUtils.copyBytes(bytes, 0, 4);
            this.sh_info = ByteUtils.copyBytes(bytes, 0, 4);
            this.sh_addralign = ByteUtils.copyBytes(bytes, 0, 4);
            this.sh_entsize = ByteUtils.copyBytes(bytes, 0, 4);
        }
    }
}
