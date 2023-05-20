package com.atyomi.boot.controller;

import com.atyomi.boot.common.BaseContext;
import com.atyomi.boot.common.R;
import com.atyomi.boot.domain.AddressBook;
import com.atyomi.boot.domain.OrderDetail;
import com.atyomi.boot.domain.Orders;
import com.atyomi.boot.domain.User;
import com.atyomi.boot.dto.OrdersDto;
import com.atyomi.boot.service.AddressBookService;
import com.atyomi.boot.service.OrderDetailService;
import com.atyomi.boot.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.tomcat.jni.Address;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private AddressBookService addressBookService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;
    @PostMapping("/submit")
    public R<String> submitOrder(@RequestBody Orders orders, HttpSession session){
        orders.setUserName(((User)session.getAttribute("user")).getName());
        orderService.saveOrder(orders);
        return R.success("订单提交成功");
    }
    @GetMapping("/userPage")
    public R<Page<OrdersDto>> getOrdersPage(Integer page,Integer pageSize){
        //得到用户ID,查找该用户的所有订单，并用Page封装
        Long userId = BaseContext.threadLocal.get();
        Page<Orders> p = new Page<>(page,pageSize);
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Orders::getUserId,userId);
        orderService.page(p);

        //对每个Orders查询有关的OrderDetails，并封装成OrdersDto对象
        //先将属性复制到新的page对象
        Page<OrdersDto> p2 = new Page<>(page,pageSize);
        BeanUtils.copyProperties(p,p2,"records");
        //单独处理records属性
        //先得到原始records属性
        List<Orders> records = p.getRecords();
        //采用stream流处理属性
        List<OrdersDto> res = records.stream().map(item -> {
            OrdersDto ordersDto = new OrdersDto();
            BeanUtils.copyProperties(item, ordersDto);
            //查询对应的orderdetails并赋值
            LambdaQueryWrapper<OrderDetail> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(OrderDetail::getOrderId, item.getId());
            List<OrderDetail> list = orderDetailService.list(queryWrapper1);
            ordersDto.setOrderDetails(list);
            return ordersDto;
        }).collect(Collectors.toList());

        //返回结果
        p2.setRecords(res);
        return R.success(p2);
    }
    @GetMapping("/page")
    public R<Page<OrdersDto>> getOrderPage(Integer page,Integer pageSize){
        //得到用户ID,查找该用户的所有订单，并用Page封装
        Long userId = BaseContext.threadLocal.get();
        Page<Orders> p = new Page<>(page,pageSize);
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Orders::getUserId,userId);
        orderService.page(p);

        //对每个Orders查询有关的OrderDetails，并封装成OrdersDto对象
        //先将属性复制到新的page对象
        Page<OrdersDto> p2 = new Page<>(page,pageSize);
        BeanUtils.copyProperties(p,p2,"records");
        //单独处理records属性
        //先得到原始records属性
        List<Orders> records = p.getRecords();
        //采用stream流处理属性
        List<OrdersDto> res = records.stream().map(item -> {
            OrdersDto ordersDto = new OrdersDto();
            BeanUtils.copyProperties(item, ordersDto);
            //查询对应的orderdetails并赋值
            LambdaQueryWrapper<OrderDetail> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(OrderDetail::getOrderId, item.getId());
            List<OrderDetail> list = orderDetailService.list(queryWrapper1);
            ordersDto.setOrderDetails(list);
            return ordersDto;
        }).collect(Collectors.toList());

        //返回结果
        p2.setRecords(res);
        return R.success(p2);
    }
    @PutMapping()
    public R<String> changeState2(@RequestBody Orders orders){
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Orders::getId,orders.getId());

        orderService.update(orders,queryWrapper);
        return R.success("修改成功");
    }

}
