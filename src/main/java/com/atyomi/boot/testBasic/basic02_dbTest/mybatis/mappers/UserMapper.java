package com.atyomi.boot.testBasic.basic02_dbTest.mybatis.mappers;

import com.atyomi.boot.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
//@Mapper
public interface UserMapper {
//    List<User> getUsers();
    int insertUser();
}
