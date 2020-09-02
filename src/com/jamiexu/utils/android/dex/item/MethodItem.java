package com.jamiexu.utils.android.dex.item;

public class MethodItem {
    private int classTypeIndex;
    private int methodNameIndex;
    private ProtoIDItem methodProto;

    public MethodItem(int classTypeIndex, int methodNameIndex, ProtoIDItem methodProto) {
        this.classTypeIndex = classTypeIndex;
        this.methodNameIndex = methodNameIndex;
        this.methodProto = methodProto;
    }

    public static int getSize(){
        return 2+2+4;
    }

    public int getClassTypeIndex() {
        return classTypeIndex;
    }

    public void setClassTypeIndex(int classTypeIndex) {
        this.classTypeIndex = classTypeIndex;
    }

    public int getMethodNameIndex() {
        return methodNameIndex;
    }

    public void setMethodNameIndex(int methodNameIndex) {
        this.methodNameIndex = methodNameIndex;
    }

    public ProtoIDItem getMethodProto() {
        return methodProto;
    }

    public void setMethodProto(ProtoIDItem methodProto) {
        this.methodProto = methodProto;
    }
}
