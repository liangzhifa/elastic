package com.zhifa.elastic;

public class CloneTest implements  Cloneable{
    private int a;
    private String str;

    public CloneTest(int a, String str) {
        this.a = a;
        this.str = str;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
