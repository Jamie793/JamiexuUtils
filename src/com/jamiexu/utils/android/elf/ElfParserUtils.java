package com.jamiexu.utils.android.elf;

import com.jamiexu.utils.file.FileUtils;
import com.jamiexu.utils.reflect.ReflectUtils;

import java.lang.reflect.Field;

public class ElfParserUtils {
    public static void parseElf(String elfPath) {
        byte[] elfBytes = FileUtils.readFile(elfPath);
        if (elfBytes == null) {
            System.out.println("read elf file byte failed...");
            return;
        }
        System.out.println(elfBytes.length);
        ElfHeader elfHeader = new ElfHeader(elfBytes);
        elfHeader.print();

    }

    public static void print(Class<?> clas, Object object) {
        Field[] fields = ReflectUtils.getAllFields(clas);
        for (Field field : fields) {
            System.out.println(field.getName() + ":" +
                    ReflectUtils.getFieldValue(field, object));
        }
    }


}
