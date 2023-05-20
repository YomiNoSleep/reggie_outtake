package com.atyomi.boot;

import com.atyomi.boot.domain.Employee;
import com.atyomi.boot.domain.User;
//import com.atyomi.boot.testBasic.basic02_dbTest.mybatis.mappers.EmployeeMapper;
import com.atyomi.boot.mapper.EmployeeMapper;
import com.atyomi.boot.testBasic.basic02_dbTest.mybatis.mappers.UserMapper;
//import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
//@MapperScan("com.atyomi.boot.mapper")
public class SpringbootApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(SpringbootApplication.class);
//        SqlSessionFactory bean = run.getBean(SqlSessionFactory.class);
//        UserMapper mapper = bean.openSession().getMapper(UserMapper.class);
//        mapper.insertUser();
//
//        EmployeeMapper bean =  run.getBean(EmployeeMapper.class);
//        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(Employee::getId,1);
//        System.out.println(bean.selectOne(wrapper));

    }
}
