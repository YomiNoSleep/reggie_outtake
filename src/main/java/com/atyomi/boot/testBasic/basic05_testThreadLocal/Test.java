package com.atyomi.boot.testBasic.basic05_testThreadLocal;

public class Test {
    public static void main(String[] args) {

      Thread t1 =new Thread(new MultiThread());
      Thread t2 = new Thread(new MultiThread());
      t1.start();
      t2.start();

    }
}
