package com.atyomi.boot.controller;

import com.atyomi.boot.common.R;
import com.atyomi.boot.domain.User;
import com.atyomi.boot.dto.UserCodeDto;
import com.atyomi.boot.service.UserService;
import com.atyomi.boot.utils.CodeSaveUtil;
import com.atyomi.boot.utils.SMSUtils;
import com.atyomi.boot.utils.ValidateCodeUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private UserService userService;
    @PostMapping()
    public R<User> addUser(User user){
        userService.save(user);
        return R.success(user);
    }
    @PostMapping("/sendMsg")
    public R<Integer> sendMsg(Long phone){
        if(phone!=null){
            Integer code = ValidateCodeUtils.generateValidateCode(4);
//            CodeSaveUtil.saveCode(phone,code);
            //用Redis缓存生成的验证码
            String sPhone = String.valueOf(phone);
            redisTemplate.opsForValue().set(sPhone,code.toString(),5, TimeUnit.MINUTES);
            return R.success(code);
        }
       return R.error("发送失败");
    }
    @PostMapping("/login")
    public R<User> userLogin(@RequestBody UserCodeDto user,  HttpSession session){
        Object oCode = redisTemplate.opsForValue().get(user.getPhone());
        String code = oCode.toString();
        if(code.equals(user.getCode().toString())){
            //在Redis中删除验证码
            redisTemplate.delete(user.getPhone());

            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,user.getPhone());


            User one = userService.getOne(queryWrapper);
            session.setAttribute("user",one);
            return R.success(one);
        }
        return R.error("登陆失败，验证码错误");
    }
    @PostMapping("/loginout")
    public R<String> logout(HttpSession session){
        session.removeAttribute("user");
        return R.success("退出成功");
    }
}
