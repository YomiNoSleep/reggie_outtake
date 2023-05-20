package com.atyomi.boot.controller;

import com.atyomi.boot.common.R;
import com.atyomi.boot.domain.Employee;
import com.atyomi.boot.service.EmployeeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    //需求分析
    //需要返回一个对象，json格式，里面内容有msg，data还有code属性
    @PostMapping("/login")
    public R<Employee> login(@RequestBody Employee employee, HttpSession session){
        //将页面提交的密码进行md5加密处理
        String password = employee.getPassword();
        String s = DigestUtils.md5DigestAsHex(password.getBytes());
        //查询数据库是否有该员工
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Employee::getUsername,employee.getUsername());
        Employee one = employeeService.getOne(wrapper);
        if(one == null){
            return R.error("未查找到该员工，请重新输入用户名");
        }
        if(one.getStatus() == 0){
            return R.error("账号已禁用");
        }
        if(!one.getPassword().equals(s)){
            return R.error("输入密码错误，请重新输入");
        }

        session.setAttribute("employee",one);
        return R.success(one);
    }
    @PostMapping("/logout")
    public R<String> logout(HttpSession session){
        session.removeAttribute("employee");
        log.info("session域中没employee了");
        return R.success("退出成功");
    }
    @PostMapping()
    public R<String> addEmployee(@RequestBody Employee employee,HttpSession session){
        //设置初始密码，123456
        String s = DigestUtils.md5DigestAsHex("123456".getBytes());
        employee.setPassword(s);

        //设置更新时间
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        //设置创建人
        Employee emp = (Employee) session.getAttribute("employee");

        employee.setCreateUser(emp.getId());
        employee.setUpdateUser(emp.getId());

        //保存员工信息到数据库
        employeeService.save(employee);

        //返回结果
        return R.success("保存成功");
    }
    @GetMapping("/page")
    public R<Page<Employee>> getEmployees(long page,long pageSize,String name){
        //发送过来get请求
        //参数为页码，页数，姓名
        //返回值为一个page对象，包含当页的员工信息


        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(!StringUtils.isEmpty(name),Employee::getName,name);
        queryWrapper.orderByDesc(Employee::getCreateTime);

        Page<Employee> p = new Page<>(page,pageSize);
        employeeService.page(p, queryWrapper);
        return R.success(p);
    }
    @PutMapping
    public R<String> updateEmployee(HttpSession session, @RequestBody Employee employee){
        //得到修改人信息
        Employee updateOne =(Employee) session.getAttribute("employee");
        //修改数据库中对应id的status属性
//        LambdaUpdateWrapper<Employee> updateWrapper = new LambdaUpdateWrapper<>();
//        updateWrapper.eq(Employee::getId,employee.getId());
//        updateWrapper.set(Employee::getStatus,employee.getStatus());
//        employee.setUpdateUser(updateOne.getId());
//        employee.setUpdateTime(LocalDateTime.now());
        //修改属性
        employeeService.updateById(employee);
        return R.success("修改成功");
    }
    @GetMapping("/{id}")
    public R<Employee> getOneEmployee(@PathVariable("id") Long id){
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getId,id);

        Employee one = employeeService.getOne(queryWrapper);
        return R.success(one);
    }
    @GetMapping("/test")
    public R<String> test(@RequestBody Employee employee,HttpServletRequest request)throws Exception{
//        ServletInputStream inputStream = request.getInputStream();
//        byte[] buf = new byte[1024];
//        int len  = 0;
//        while((len = inputStream.read(buf)) !=-1){
//            System.out.println(new String(buf,0,len));
//        }
//        System.out.println(username+";"+password);
        System.out.println(employee);
        return R.success("这是测试");
    }


}
