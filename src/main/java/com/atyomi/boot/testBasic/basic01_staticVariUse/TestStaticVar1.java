package com.atyomi.boot.testBasic.basic01_staticVariUse;

import static com.atyomi.boot.testBasic.basic01_staticVariUse.Static1.var1;

public class TestStaticVar1 {
    public static void main(String[] args) {
        //静态变量可以直接使用
        System.out.println(var1);
        //初始化方法调用时机
        new InitTime();
        new Static1().getInnerVar();
        //静态内部类只是定义在类内部的一个类，其它和普通类相同
    }
}
