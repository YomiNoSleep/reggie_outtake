package com.atyomi.boot.configures.exceptionHandlers;

import com.atyomi.boot.common.R;
import com.atyomi.boot.common.exceptions.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

@ResponseBody
@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> handlerConflictUsername(Exception e){
//        e.printStackTrace();
//        log.info("捕获到用户名重复异常了");
        if(e.getMessage().contains("Duplicate entry")){
            String msg = e.getMessage().split(" ")[2];
            return R.error("名称重复了:"+msg);
        }
        return R.error(e.getMessage());
    }
    @ExceptionHandler(CustomException.class)
    public R<String> handlerCustomException(Exception e){
//        e.printStackTrace();
//        log.info("捕获到用户名重复异常了");

        return R.error(e.getMessage());
    }
}
