package com.atyomi.boot.testBasic.basic02_dbTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCOpraMysql {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //原生JDBC操作数据库

        //1.加载驱动类
        //这一步做了什么事？
        //：通过静态代码块，即类加载代码块将mysql连接java的驱动Driver对象注册到DriverManager中
        //这样DriverManager对象就能够通过具体的Diver对象，操作Mysql
        Class.forName("com.mysql.cj.jdbc.Driver");

        //2.设置基础属性获取数据库连接
        String username = "root";
        String password = "ybl";
        String url = "jdbc:mysql://localhost:3306/Yomi?characterEncoding=UTF-8";

        //3.通过DriverManager获取连接对象，通过连接对象操作数据库
        //这一步具体做了什么？
        //1.遍历DriverManager中注册的驱动，传入url和用户信息
        //2.判断该驱动是否支持此类格式的url  （注册有好多种驱动，此时有3个）
        //3.判断到支持，则调用driver的connection方法返回一个数据库连接
        Connection connection1 = DriverManager.getConnection(url, username, password);
        Connection connection2 = DriverManager.getConnection(url, username, password);
        ResultSet resultSet = connection2.prepareStatement("select * from employee").executeQuery();

        while(resultSet.next()){
            System.out.println(resultSet.getString("username"));
        }

    }
}
