package com.atyomi.boot.controller;

import com.atyomi.boot.common.R;
import com.atyomi.boot.domain.Category;
import com.atyomi.boot.domain.Dish;
import com.atyomi.boot.domain.Employee;
import com.atyomi.boot.domain.Setmeal;
import com.atyomi.boot.service.CategoryService;
import com.atyomi.boot.service.DishService;
import com.atyomi.boot.service.SetmealService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;
    @GetMapping("/page")
    public R<Page<Category>> getCategories(long page, long pageSize, HttpServletRequest request){
        //发送过来get请求
        //参数为页码，页数，姓名
        //返回值为一个page对象，包含当页的员工信息

        ServletContext servletContext = request.getServletContext();
        String realPath = servletContext.getRealPath("/");
        String contextPath = servletContext.getContextPath();

        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);


        Page<Category> p = new Page<>(page,pageSize);
        categoryService.page(p, queryWrapper);
        return R.success(p);
    }
    @DeleteMapping()
    public R<String> deleteCategory(@RequestParam("ids") Long id){
        categoryService.removeById(id);
        return R.success("删除成功");
    }
    @PostMapping()
    public R<String> addClass(@RequestBody Category category, HttpSession session){
        Employee updateOne =(Employee) session.getAttribute("employee");
//        category.setCreateTime(LocalDateTime.now());
//        category.setUpdateUser(updateOne.getId());
//        category.setCreateUser(updateOne.getId());
//        category.setUpdateTime(LocalDateTime.now());


        categoryService.save(category);
        return R.success("成功保存");
    }
    @PutMapping()
    public R<String> updateCategory(@RequestBody Category category, HttpSession session){
//        Employee updateOne =(Employee) session.getAttribute("employee");

        categoryService.updateById(category);
        return R.success("成功保存");
    }
    @GetMapping("/list")
    public R<List<Category>> getDishes(Integer type){
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(type!=null,Category::getType,type);
        List<Category> list = categoryService.list(queryWrapper);
        return R.success(list);
    }
}
