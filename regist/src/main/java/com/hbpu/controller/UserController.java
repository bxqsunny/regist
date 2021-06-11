package com.hbpu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hbpu.entity.PageResult;
import com.hbpu.entity.ResultDto;
import com.hbpu.entity.User;
import com.hbpu.entity.DataDto;
import com.hbpu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2021-02-14
 */
@RestController
@RequestMapping("/user")
@CrossOrigin // 跨域
public class UserController {
    @Autowired
    public IUserService iUserService;

    /**
     * 用户登录判断
     * @return
     */
    @PostMapping("/login")
    public ResultDto login(@RequestBody User user){
        return iUserService.login(user);
    }

    @PostMapping("/userInfo")
    public User userInfo(){
        return iUserService.examine();
    }

    @PostMapping("/userReset")
    public ResultDto reset(@RequestBody User user){
        return iUserService.reset(user);
    }

    @PostMapping("/updateUser")
    public ResultDto updateUser(@RequestBody User user){
        return iUserService.updateUser(user);
    }
    @PostMapping("/listUser")
    public PageResult listUser(@RequestBody DataDto<User> dataDto){
        return iUserService.listUser(dataDto);
    }

}
