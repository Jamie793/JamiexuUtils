package com.jamiexu;


import com.jamiexu.utils.android.dex.DexParser;

import java.io.Serializable;

/**
 * @author Jamiexu or Jamie793
 * @version 1.0
 * 博客 blog.jamiexu.cn
 */

public class Main {

    public static int post = 0x00000001;
    public static int download = 0x00000002;
    public static int get = 0x00000004;
    public static int upload = 0x00000008;


    public void a(String a, int b, char c, boolean d, Boolean e, CharSequence f) {
    }

    public static void main(String[] args) {
        DexParser dexParser = new DexParser();
        dexParser.parse("c:\\users\\jamiexu\\desktop\\classes.dex");
        String[] strings = dexParser.getDexString().getStrings();
        dexParser.getDexString().encrypt("1234567891111111".getBytes());
        dexParser.getDexString().commit();
        dexParser.getDexString().writeDex("c:\\users\\jamiexu\\desktop\\classes2.dex");
        System.out.println(dexParser.verifyCheckSum());

    }


    static class A implements Serializable {
        private String a;
        public Main m;
        protected Main d;

        public A(Main m) {
            this.m = m;
        }

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }
    }


}
