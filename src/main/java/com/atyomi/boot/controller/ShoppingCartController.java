package com.atyomi.boot.controller;

import com.atyomi.boot.common.BaseContext;
import com.atyomi.boot.common.R;
import com.atyomi.boot.domain.ShoppingCart;
import com.atyomi.boot.domain.User;
import com.atyomi.boot.service.ShoppingCartService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;
    @RequestMapping("/list")
    public R<List<ShoppingCart>> getShoppingCartByUserId(HttpSession session){
        User user =(User) session.getAttribute("user");

        LambdaQueryWrapper<ShoppingCart> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,user.getId());

        List<ShoppingCart> list = shoppingCartService.list(queryWrapper);
        return R.success(list);
    }
    @PostMapping("/add")
    public R<ShoppingCart> addShoppingCart(@RequestBody ShoppingCart shoppingCart){
        //设置用户ID
        shoppingCart.setUserId(BaseContext.threadLocal.get());
        //先查有没有对应的菜品或者套餐
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,shoppingCart.getUserId());
        queryWrapper.eq(shoppingCart.getDishId()!=null,ShoppingCart::getDishId,shoppingCart.getDishId());
        queryWrapper.eq(shoppingCart.getSetmealId()!=null,ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        ShoppingCart one = shoppingCartService.getOne(queryWrapper);

        if(one!=null){
            //先获取菜品数量
            Integer number = one.getNumber();
            //更新菜品数量
            one.setNumber(number+1);
            shoppingCartService.saveOrUpdate(one);
            return R.success(one);
        }
        else {
            shoppingCartService.save(shoppingCart);
            return R.success(shoppingCart);
        }
    }
    @PostMapping("/sub")
    public R<String> subShoppingCart(@RequestBody ShoppingCart shoppingCart){
        //设置用户ID
        shoppingCart.setUserId(BaseContext.threadLocal.get());
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,shoppingCart.getUserId());
        queryWrapper.eq(shoppingCart.getDishId()!=null,ShoppingCart::getDishId,shoppingCart.getDishId());
        queryWrapper.eq(shoppingCart.getSetmealId()!=null,ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        ShoppingCart one = shoppingCartService.getOne(queryWrapper);

        //先获取菜品数量
        Integer number = one.getNumber();
        //更新菜品数量
        if(one.getNumber() == 1){
            shoppingCartService.removeById(one.getId());
        }
        else{
            one.setNumber(number-1);
            shoppingCartService.saveOrUpdate(one);
        }
        return R.success("减少成功");
    }
    @DeleteMapping("/clean")
    public R<String> cleanShoppingCart(){
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,BaseContext.threadLocal.get());

        shoppingCartService.remove(queryWrapper);
        return R.success("清空成功");
    }
}
