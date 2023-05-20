package com.atyomi.boot.testBasic.basic05_testThreadLocal;

public class MultiThread implements Runnable {
    @Override
    public void run() {
        ThreadLocal<String> threadLocal = Context.threadLocal;
        threadLocal.set("啊~你好哈哈哈");
    }
}
