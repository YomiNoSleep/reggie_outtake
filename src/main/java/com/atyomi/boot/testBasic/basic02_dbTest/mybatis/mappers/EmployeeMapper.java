package com.atyomi.boot.testBasic.basic02_dbTest.mybatis.mappers;

import com.atyomi.boot.domain.Employee;
import org.apache.ibatis.annotations.Mapper;

//@Mapper
public interface EmployeeMapper {
    Employee getOneEmp();
}
