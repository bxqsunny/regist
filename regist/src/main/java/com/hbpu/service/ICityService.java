package com.hbpu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hbpu.entity.City;
import com.hbpu.entity.Province;
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
public interface ICityService extends IService<City> {
   ResultDto<List> listCityByProvinceId(String ProvinceId);
}
