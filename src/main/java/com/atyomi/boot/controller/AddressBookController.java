package com.atyomi.boot.controller;

import com.atyomi.boot.common.BaseContext;
import com.atyomi.boot.common.R;
import com.atyomi.boot.domain.AddressBook;
import com.atyomi.boot.domain.User;
import com.atyomi.boot.service.AddressBookService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/addressBook")
public class AddressBookController {
    @Autowired
    private AddressBookService addressBookService;
    @GetMapping("/list")
    public R<List<AddressBook>> getAddresses(HttpSession session){
        User user =(User) session.getAttribute("user");
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(user.getId()!=null,AddressBook::getUserId,user.getId());

        List<AddressBook> list = addressBookService.list(queryWrapper);
        return R.success(list);
    }
    @PostMapping()
    public R<String> addAddress(@RequestBody AddressBook addressBook){
        Long userId = BaseContext.threadLocal.get();
        addressBook.setUserId(userId);
        addressBookService.save(addressBook);
        return R.success("保存成功");
    }
    @PutMapping("/default")
    public R<String> setDefaultAddress(@RequestBody AddressBook addressBook){
        LambdaUpdateWrapper<AddressBook> updateWrapper = new LambdaUpdateWrapper<>();

        updateWrapper.eq(AddressBook::getUserId,BaseContext.threadLocal.get());
        updateWrapper.set(AddressBook::getIsDefault,0);
        addressBookService.update(updateWrapper);

        addressBook.setIsDefault(1);
        addressBookService.updateById(addressBook);
        return R.success("修改成功");
    }
    @GetMapping("/default")
    public R<AddressBook> getAddressBook(){
        //获取用户ID
        Long userId = BaseContext.threadLocal.get();
        //设置查找条件
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getUserId,userId);
        queryWrapper.eq(AddressBook::getIsDefault,1);
        //查找对应地址
        AddressBook one = addressBookService.getOne(queryWrapper);
        return R.success(one);
    }
}
