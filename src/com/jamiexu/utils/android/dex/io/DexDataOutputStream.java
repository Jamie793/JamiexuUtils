package com.jamiexu.utils.android.dex.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class DexDataOutputStream extends ByteArrayOutputStream {

    public DexDataOutputStream() {
        super();
    }

    public DexDataOutputStream(int size) {
        super(size);
    }


    public synchronized void writeLeb128(int value) {
        while ((value & 0xffffffffL) > 0x7f) {
            this.write((value & 0x7f) | 0x80);
            value >>>= 7;
        }
        this.write(value);
    }

    public synchronized void writeString(String str) throws IOException {
        this.write(str.getBytes());
    }


    public synchronized void writeIndex(int offset, byte[] data) {
        this.write(data, 0, data.length);
    }

    @Override
    public synchronized void write(int b) {
        super.write(b);
    }

    @Override
    public synchronized void write(byte[] b, int off, int len) {
        super.write(b, off, len);
    }

    @Override
    public synchronized void writeTo(OutputStream out) throws IOException {
        super.writeTo(out);
    }

    @Override
    public synchronized void reset() {
        super.reset();
    }

    @Override
    public synchronized byte[] toByteArray() {
        return super.toByteArray();
    }

    @Override
    public synchronized int size() {
        return super.size();
    }

    @Override
    public synchronized String toString() {
        return super.toString();
    }

    @Override
    public synchronized String toString(String charsetName) throws UnsupportedEncodingException {
        return super.toString(charsetName);
    }


    @Override
    public void close() throws IOException {
        super.close();
    }
}
