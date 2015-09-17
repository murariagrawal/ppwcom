package com.panipuri.presentation.viewbuilding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;

public class BufferedServletOutputStream extends ServletOutputStream{
	public static final String DEFAULT_ENCODING="UTF8";
    private ByteArrayOutputStream byteArrayOutputStream;
    public BufferedServletOutputStream(ByteArrayOutputStream byteArrayOutputStream) {
        this.byteArrayOutputStream = byteArrayOutputStream;
    }
    @Override
    public void write(int b) throws IOException {
        byteArrayOutputStream.write(b);
    }

    @Override
    public void print(String s) throws IOException {
        byteArrayOutputStream.write(s.getBytes(DEFAULT_ENCODING));
    }

    @Override
    public void print(boolean b) throws IOException {
        byteArrayOutputStream.write(Boolean.valueOf(b).toString().getBytes(DEFAULT_ENCODING));
    }

    @Override
    public void print(char c) throws IOException {
        byteArrayOutputStream.write(new String(new char[]{c}).getBytes(DEFAULT_ENCODING));
    }

    @Override
    public void print(double d) throws IOException {
        byteArrayOutputStream.write(Double.toString(d).getBytes(DEFAULT_ENCODING));
    }

    @Override
    public void print(float f) throws IOException {
        byteArrayOutputStream.write(Float.toString(f).getBytes(DEFAULT_ENCODING));
    }

    @Override
    public void print(int i) throws IOException {
        byteArrayOutputStream.write(Integer.toString(i).getBytes(DEFAULT_ENCODING));
    }

    @Override
    public void print(long l) throws IOException {
        byteArrayOutputStream.write(Long.toString(l).getBytes(DEFAULT_ENCODING));

    }

    @Override
    public void println() throws IOException {
        byteArrayOutputStream.write("\n".getBytes(DEFAULT_ENCODING));
    }

    @Override
    public void println(String s) throws IOException {
        byteArrayOutputStream.write((s + "\n").getBytes(DEFAULT_ENCODING));
    }

    @Override
    public void println(boolean b) throws IOException {
        byteArrayOutputStream.write((Boolean.valueOf(b).toString() + "\n").getBytes(DEFAULT_ENCODING));
    }

    @Override
    public void println(char c) throws IOException {
        byteArrayOutputStream.write((Character.valueOf(c).toString() + "\n").getBytes(DEFAULT_ENCODING));
    }

    @Override
    public void println(double d) throws IOException {
        byteArrayOutputStream.write((Double.valueOf(d).toString() + "\n").getBytes(DEFAULT_ENCODING));
    }

    @Override
    public void println(float f) throws IOException {
        byteArrayOutputStream.write((Float.valueOf(f).toString() + "\n").getBytes(DEFAULT_ENCODING));
    }

    @Override
    public void println(int i) throws IOException {
        byteArrayOutputStream.write((Integer.valueOf(i).toString() + "\n").getBytes(DEFAULT_ENCODING));
    }

    @Override
    public void println(long l) throws IOException {
        byteArrayOutputStream.write((Long.valueOf(l).toString() + "\n").getBytes(DEFAULT_ENCODING));
    }

    @Override
    public void write(byte[] b) throws IOException {
        byteArrayOutputStream.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        byteArrayOutputStream.write(b, off, len);
    }

    public ByteArrayOutputStream getByteArrayOutputStream() {
        return byteArrayOutputStream;
    }
    

}
