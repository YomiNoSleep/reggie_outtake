package com.atyomi.boot.testBasic.basic01_staticVariUse;

public class InitTime {
    public String name = "张三";
    public int i;

    public InitTime() {
    }
    public void init(){
        System.out.println("初始化方法执行");
    }
}
