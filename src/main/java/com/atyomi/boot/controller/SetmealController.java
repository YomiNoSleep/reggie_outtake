package com.atyomi.boot.controller;

import com.atyomi.boot.common.R;
import com.atyomi.boot.domain.Category;
import com.atyomi.boot.domain.Dish;
import com.atyomi.boot.domain.Setmeal;
import com.atyomi.boot.dto.DishDto;
import com.atyomi.boot.dto.SetmealDto;
import com.atyomi.boot.service.CategoryService;
import com.atyomi.boot.service.SetmealService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private CategoryService categoryService;
    @CacheEvict(value = "setmealCache",allEntries = true)
    @PostMapping()
    public R<String> saeSetmeal(@RequestBody SetmealDto setmealDto){
        setmealService.addSetmeal(setmealDto);
        return R.success("添加成功");
    }
    @GetMapping("/page")
    public R<Page<SetmealDto>> getSetmealPage(long page, long pageSize,String name){
        //发送过来get请求
        //参数为页码，页数，姓名
        //返回值为一个page对象，包含当页的菜品信息

        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Setmeal::getUpdateTime);
        queryWrapper.like(name != null,Setmeal::getName,name);
        Page<Setmeal> p = new Page<>(page,pageSize);
        //查找到对应的菜品Page对象
        setmealService.page(p, queryWrapper);
        //但是需要返回的是DishDto对象，里面有CategoryName属性
        //创建DishDto的Page对象
        Page<SetmealDto> setmealDtoPage = new Page<>();
        BeanUtils.copyProperties(p,setmealDtoPage,"records");

        //单独处理records属性
        //将records属性的每一项record中的Dish替换成对应的DishDto
        List<Setmeal> records = p.getRecords();
        List<SetmealDto> newRecords = records.stream().map((setmeal)->{
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(setmeal,setmealDto);
            //得到菜品名称
            LambdaQueryWrapper<Category> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(Category::getId,setmeal.getCategoryId());
            Category one = categoryService.getOne(queryWrapper1);
            String categoryName = one.getName();
            //将名称赋值给DishDto
            setmealDto.setCategoryName(categoryName);
            return setmealDto;
        }).collect(Collectors.toList());

        //把新的records赋值给Page
        setmealDtoPage.setRecords(newRecords);
        return R.success(setmealDtoPage);
    }
    @GetMapping("/{setmealId}")
    public R<SetmealDto> getOneSetmeal(@PathVariable("setmealId") Long setmealId){
        SetmealDto oneSetmeal = setmealService.getOneSetmeal(setmealId);
        return R.success(oneSetmeal);
    }
    @CacheEvict(value = "setmealCache",allEntries = true)
    @PostMapping("/status/{state}")
    public R<String> changeState(@PathVariable("state") Integer state,String ids){
        String[] split = ids.split(",");
        List<Long> collect = Arrays.stream(split).map(Long::parseLong).collect(Collectors.toList());
        for (Long id : collect) {
            LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Setmeal::getId, id);
            Setmeal setmeal = new Setmeal();
            setmeal.setStatus(state);
            setmealService.update(setmeal, queryWrapper);
        }
        return R.success("修改状态成功");
    }
    @CacheEvict(value = "setmealCache",allEntries = true)
    @DeleteMapping()
    public R<String> deleteSetmeals(String ids){
        String[] split = ids.split(",");
        List<Long> collect = Arrays.stream(split).map(Long::parseLong).collect(Collectors.toList());
        setmealService.removeByIds(collect);
        return R.success("删除成功");
    }
    @Cacheable(value = "setmealCache",key = "#categoryId.toString()+'_'+#status.toString()")
    @GetMapping("/list")
    public R<List<Setmeal>> getSetmealByCategoryId(Long categoryId,Integer status){
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Setmeal::getCategoryId,categoryId);
        queryWrapper.eq(Setmeal::getStatus,status);


        List<Setmeal> list = setmealService.list(queryWrapper);

        return R.success(list);
    }

}
