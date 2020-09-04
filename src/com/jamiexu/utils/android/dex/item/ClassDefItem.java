package com.jamiexu.utils.android.dex.item;

/**
 * @author Jamiexu/Jamie793
 * @version 1.0
 * @date 2020/9/2
 * @time 10:46
 * @blog https://blog.jamiexu.cn
 **/

public class ClassDefItem {
    private final int classIdx;
    private final int accessFlags;
    private final int superClassIdx;
    private final int interfaceOff;
    private final int sourceFileIdx;
    private final int annotationsOff;
    private final int classDataOff;
    private final int staticvaluesOff;

    public final static int
    ACC_PUBLIC = 0x00000001,
    ACC_PRIVATE = 0x00000002,
    ACC_PROTECTED = 0x00000004,
    ACC_STATIC = 0x00000008,
    ACC_FINAL = 0x00000010,
    ACC_SYNCHRONIZED = 0x00000020;


    public ClassDefItem(int classIdx, int accessFlags, int superClassIdx, int interfaceOff, int sourceFileIdx, int annotationsOff, int classDataOff, int staticvaluesOff) {
        this.classIdx = classIdx;
        this.accessFlags = accessFlags;
        this.superClassIdx = superClassIdx;
        this.interfaceOff = interfaceOff;
        this.sourceFileIdx = sourceFileIdx;
        this.annotationsOff = annotationsOff;
        this.classDataOff = classDataOff;
        this.staticvaluesOff = staticvaluesOff;
    }

    public static int getSize(){
        return 4*8;
    }


    public int getClassIdx() {
        return classIdx;
    }

    public int getAccessFlags() {
        return accessFlags;
    }

    public int getSuperClassIdx() {
        return superClassIdx;
    }

    public int getInterfaceOff() {
        return interfaceOff;
    }

    public int getSourceFileIdx() {
        return sourceFileIdx;
    }

    public int getAnnotationsOff() {
        return annotationsOff;
    }

    public int getClassDataOff() {
        return classDataOff;
    }

    public int getStaticvaluesOff() {
        return staticvaluesOff;
    }
}
