package com.hbpu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hbpu.entity.Region;
import com.hbpu.entity.ResultDto;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2021-02-14
 */
public interface IRegionService extends IService<Region> {
    ResultDto<List> listRegionByCityId(String cityId);
}
