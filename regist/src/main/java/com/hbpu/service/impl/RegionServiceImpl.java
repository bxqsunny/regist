package com.hbpu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hbpu.entity.City;
import com.hbpu.entity.Region;
import com.hbpu.entity.ResultDto;
import com.hbpu.mapper.RegionMapper;
import com.hbpu.service.IRegionService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements IRegionService {

    @Autowired
    private RegionMapper regionMapper;
    @Override
    public ResultDto<List> listRegionByCityId(String cityId) {
        QueryWrapper<Region> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(cityId)){
            queryWrapper.eq("city_id",cityId);
        }
        List<Region> regionList = regionMapper.selectList(queryWrapper);
        ResultDto<List> resultDto = new ResultDto<>();
        resultDto.setData(regionList);
        return resultDto;
    }
}
