package com.atyomi.boot.testBasic.basic02_dbTest;

import com.atyomi.boot.domain.Employee;
import com.atyomi.boot.testBasic.basic02_dbTest.mybatis.mappers.EmployeeMapper;
import com.atyomi.boot.testBasic.basic02_dbTest.mybatis.mappers.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MybatisOperaMysql {
    public static void main(String[] args) throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder =new SqlSessionFactoryBuilder();
        SqlSessionFactory build = sqlSessionFactoryBuilder.build(resourceAsStream);
        SqlSession sqlSession = build.openSession();
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        System.out.println(mapper.getOneEmp());
    }
}
