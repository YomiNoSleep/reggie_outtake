package com.atyomi.boot.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.atyomi.boot.common.BaseContext.threadLocal;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("createTime", LocalDateTime.now());
        Long aLong = threadLocal.get();
        metaObject.setValue("createUser",threadLocal.get());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", threadLocal.get());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", threadLocal.get());
    }
}
