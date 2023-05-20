package com.atyomi.boot.controller;

import com.atyomi.boot.common.R;
import com.atyomi.boot.domain.Category;
import com.atyomi.boot.domain.Dish;
import com.atyomi.boot.domain.DishFlavor;
import com.atyomi.boot.dto.DishDto;
import com.atyomi.boot.service.CategoryService;
import com.atyomi.boot.service.DishFlavorService;
import com.atyomi.boot.service.DishService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.bcel.internal.generic.LNEG;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private DishFlavorService dishFlavorService;
    @GetMapping("/page")
    public R<Page<DishDto>> getDishes(long page, long pageSize,String name){
        //发送过来get请求
        //参数为页码，页数，姓名
        //返回值为一个page对象，包含当页的菜品信息

        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Dish::getUpdateTime);
        queryWrapper.like(name != null,Dish::getName,name);
        Page<Dish> p = new Page<>(page,pageSize);
        //查找到对应的菜品Page对象
        dishService.page(p, queryWrapper);
        //但是需要返回的是DishDto对象，里面有CategoryName属性
        //创建DishDto的Page对象
        Page<DishDto> dishDtoPage = new Page<>();
        BeanUtils.copyProperties(p,dishDtoPage,"records");

        //单独处理records属性
        //将records属性的每一项record中的Dish替换成对应的DishDto
        List<Dish> records = p.getRecords();
        List<DishDto> newRecords = records.stream().map((dish)->{
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(dish,dishDto);
            //得到菜品名称
            LambdaQueryWrapper<Category> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(Category::getId,dish.getCategoryId());
            Category one = categoryService.getOne(queryWrapper1);
            String categoryName = one.getName();
            //将名称赋值给DishDto
            dishDto.setCategoryName(categoryName);
            return dishDto;
        }).collect(Collectors.toList());

        //把新的records赋值给Page
        dishDtoPage.setRecords(newRecords);
        return R.success(dishDtoPage);
    }
    @PostMapping()
    public R<String> addDish(@RequestBody DishDto dishDto){
        dishService.saveWithFlavor(dishDto);
        return R.success("添加成功");
    }
    @GetMapping("/{dishId}")
    public R<DishDto> getOneDish(@PathVariable("dishId") Long dishId){
        DishDto oneDish = dishService.getOneDish(dishId);
        return R.success(oneDish);
    }
    @PutMapping()
    public R<String> alterDish(@RequestBody DishDto dishDto){
        dishService.saveWithFlavor(dishDto);
        return R.success("保存");
    }
    @PostMapping("/status/{state}")
    public R<String> changeState(@PathVariable("state") Integer state,String ids){
        String[] split = ids.split(",");
        List<Long> collect = Arrays.stream(split).map(Long::parseLong).collect(Collectors.toList());
        for (Long id : collect) {
            LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Dish::getId, id);
            Dish dish = new Dish();
            dish.setStatus(state);
            dishService.update(dish, queryWrapper);
        }
        return R.success("修改状态成功");
    }
    @DeleteMapping()
    public R<String> deleteBatch(String ids){
        String[] split = ids.split(",");
        List<Long> collect = Arrays.stream(split).map(Long::parseLong).collect(Collectors.toList());
        dishService.deleteDishes(collect);
        return R.success("删除成功");
    }
    @GetMapping("/list")
    public R<List<DishDto>> getDishesByCategoryId(Long categoryId,Integer status){
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dish::getCategoryId,categoryId);
        queryWrapper.eq(Dish::getStatus,status);


        List<Dish> list = dishService.list(queryWrapper);
        List<DishDto> res = list.stream().map(item -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            LambdaQueryWrapper<DishFlavor> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(DishFlavor::getDishId, dishDto.getId());
            List<DishFlavor> list1 = dishFlavorService.list(queryWrapper1);

            dishDto.setFlavors(list1);
            return dishDto;
        }).collect(Collectors.toList());
        return R.success(res);
    }
}
