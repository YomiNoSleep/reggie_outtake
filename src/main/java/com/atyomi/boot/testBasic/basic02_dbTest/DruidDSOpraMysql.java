package com.atyomi.boot.testBasic.basic02_dbTest;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class DruidDSOpraMysql {
    public static void main(String[] args) throws Exception {
        Map<String,String> properties = new HashMap<>();
        properties.put("url","jdbc:mysql://localhost:3306/Yomi?characterEncoding=UTF-8");
        properties.put("username","root");
        properties.put("password","ybl");
//        properties.put("driverClass","com.mysql.cj.jdbc.Driver");
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
        Connection connection = dataSource.getConnection();
        ResultSet resultSet = connection.prepareStatement("select * from employee").executeQuery();
        while(resultSet.next()){
            System.out.println(resultSet.getString("username"));
        }
    }
}
