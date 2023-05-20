package com.atyomi.boot.testBasic.basic01_staticVariUse;

public class Static1 {
    public static final int var1 =10;
    static class Inner1{
        static int innerVar = 20;
        static {
            System.out.println("静态内部类加载了");
        }
    }
    public int getInnerVar(){
        return Inner1.innerVar;
    }
}
