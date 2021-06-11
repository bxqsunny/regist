package com.hbpu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hbpu.entity.*;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2021-02-14
 */
public interface ICommunityService extends IService<Community> {

    PageResult listCommunity(DataDto<Community> dataDto);

    ResultDto saveOrUpdateCommunity(Community community);
}
