package com.atyomi.boot.service.impl;

import com.atyomi.boot.domain.Employee;
import com.atyomi.boot.mapper.EmployeeMapper;
import com.atyomi.boot.service.EmployeeService;
import com.atyomi.boot.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
