package com.hbpu.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hbpu.entity.DataDto;
import com.hbpu.entity.PageResult;
import com.hbpu.entity.ResultDto;
import com.hbpu.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2021-02-14
 */
public interface IUserService extends IService<User> {
    public ResultDto login(User user);
    public User examine();
    // 充值用户密码为123456
    public ResultDto reset(User user);
    public ResultDto updateUser(User user);

    public PageResult<User> listUser(DataDto<User> dataDto);
}
