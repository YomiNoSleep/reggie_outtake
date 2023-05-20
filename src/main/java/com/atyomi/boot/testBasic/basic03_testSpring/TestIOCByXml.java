package com.atyomi.boot.testBasic.basic03_testSpring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestIOCByXml {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext();
    }
}
