package com.atyomi.boot.mapper;

import com.atyomi.boot.domain.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
