package com.hbpu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbpu.entity.DataDto;
import com.hbpu.entity.PageResult;
import com.hbpu.entity.ResultDto;
import com.hbpu.entity.User;
import com.hbpu.mapper.UserMapper;
import com.hbpu.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hbpu.util.JwtUtils;
import com.hbpu.util.RsaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.security.PrivateKey;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-02-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    public UserMapper userMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public ResultDto login(User user) {
        // 验证用户的用户名和密码是否正确
        if (!StringUtils.isEmpty(user)){
            // 判断是否为空
            if (!StringUtils.isEmpty(user.getPassword()) && !StringUtils.isEmpty(user.getUsername())){
                // 根据用户名从数据库中获取账号
                QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("userName", user.getUsername());
                User u = (User) userMapper.selectOne(queryWrapper);
                    if (StringUtils.isEmpty(u)){
                        return new ResultDto(false,"用户不存在");
                    }
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                boolean matches = bCryptPasswordEncoder.matches(user.getPassword(), u.getPassword());
                if (!matches) // 密码错误
                    return new ResultDto(matches,"密码错误");
                // 获取令牌
                String token = genJwtToken(u);
                stringRedisTemplate.boundValueOps("user"+u.getUserId()).set(token);
                stringRedisTemplate.expire("user"+u.getUserId(),30, TimeUnit.MINUTES);
                return new ResultDto(matches,"登录成功",token);
            }
            return new ResultDto(false,"用户名或密码不能为空");
        }
        return new ResultDto(false,"用户信息不能为空");
    }

    @Override
    public User examine() {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal;
    }

    @Override
    public ResultDto reset(User user) {
         if (!StringUtils.isEmpty(user.getUserId())){
             // 当用户id不为空时，重置密码为123456
             String pwd = new BCryptPasswordEncoder().encode("123456");
             user.setPassword(pwd);
             userMapper.updateById(user);
             return new ResultDto(true,"重置成功");
         }
        return new ResultDto(true,"请规范输入");
    }

    @Override
    public ResultDto updateUser(User user) {
        if (StringUtils.isEmpty(user.getUsername())){
            return new ResultDto(true,"请规范输入");
        }
        if (StringUtils.isEmpty(user.getPassword())){
            return new ResultDto(true,"请规范输入");
        }
        if (StringUtils.isEmpty(user.getPhone())){
            return new ResultDto(true,"请规范输入");
        }
        String pwd = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(pwd);
        userMapper.updateById(user);
        return new ResultDto(true,"修改成功");
    }

    @Override
    public PageResult<User> listUser(DataDto<User> dataDto) {
        Page<User> userPage = new Page<>(dataDto.getPage(), dataDto.getLimit());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(dataDto.getData().getUsername())){
            queryWrapper.like("userName",dataDto.getData().getUsername());
        }
        Page<User> page = userMapper.selectPage(userPage, queryWrapper);
        PageResult<User> pageResult = new PageResult<>();
        if (page.getSize() > 0){
            pageResult.setData(page.getRecords());
            pageResult.setPages(page.getPages());
            pageResult.setTotal(page.getTotal());
        }
        return pageResult;
    }

    /**
     * 获取令牌信息
     * @return
     */
    private  String  genJwtToken(User user){

        try {
            //从类路径中获取私钥
            String privateKeyPath = ResourceUtils.getFile("classpath:ras_private").getPath();
            PrivateKey privateKey = RsaUtils.getPrivateKey(privateKeyPath);

            String jwtToken = JwtUtils.generateTokenExpireInMinutes(user, privateKey, 30);
            // System.out.println(jwtToken);

            return jwtToken;

        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }


    }
}
