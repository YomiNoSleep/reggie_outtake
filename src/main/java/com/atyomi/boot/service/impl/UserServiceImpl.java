package com.atyomi.boot.service.impl;

import com.atyomi.boot.domain.Employee;
import com.atyomi.boot.domain.User;
import com.atyomi.boot.mapper.UserMapper;
import com.atyomi.boot.service.UserService;
import com.atyomi.boot.testBasic.basic02_dbTest.mybatis.mappers.EmployeeMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
