package com.hbpu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hbpu.entity.City;
import com.hbpu.entity.ResultDto;
import com.hbpu.mapper.CityMapper;
import com.hbpu.service.ICityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-02-14
 */
@Service
public class CityServiceImpl extends ServiceImpl<CityMapper, City> implements ICityService {

    @Autowired
    private CityMapper cityMapper;

    @Override
    public ResultDto<List> listCityByProvinceId(String ProvinceId) {
        QueryWrapper<City> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(ProvinceId)){
            // id不为空时
            queryWrapper.eq("province_id",ProvinceId);
        }
        List<City> cityList = cityMapper.selectList(queryWrapper);
        ResultDto<List> resultDto = new ResultDto<>();
        resultDto.setData(cityList);
        return resultDto;
    }
}
