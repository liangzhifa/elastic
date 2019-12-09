package com.zhifa.elastic;

public class Test  {
    public static void main(String[] args) throws CloneNotSupportedException {
        CloneTest cloneTest = new CloneTest(1, "66");
        CloneTest clone = (CloneTest) cloneTest.clone();
        System.out.println(clone==cloneTest);
    }
}
