package com.jamiexu;


import com.jamiexu.utils.android.dex.DexParser;
import com.jamiexu.utils.convert.ConvertUtils;

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
//        System.out.println(Arrays.toString(" L̀̀́;".toCharArray()));
//        int c= 0x318E-0x3131*5;
//        for(int i = 0x3131;i<0x318E;i++){
////            System.out.println((char)((i*5+'0')));
//        }
//        char[] s = "/ۖۖ\"".toCharArray();
//        char[] b = new char[2];
//        System.arraycopy(s,1,b,0,2);
//        System.out.println(Arrays.toString(b));
//        System.out.println((char)b[0]);
//        System.out.println((char)1750);
//        System.out.println((char)1536);

//        ElfParserUtils.parseElf("c:\\users\\jamiexu\\desktop\\su");
//        ElfParserUtils.parseElf("c:\\users\\jamiexu\\desktop\\libarvip.so");


        DexParser dexParser = new DexParser();
        dexParser.parse("c:\\users\\jamiexu\\desktop\\classes.dex");
        System.out.println(ConvertUtils.bytesToInt(new byte[]{0,0,0,-82}));
        System.out.println(ConvertUtils.bytesToInt(new byte[]{0,0,0,2}));

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
