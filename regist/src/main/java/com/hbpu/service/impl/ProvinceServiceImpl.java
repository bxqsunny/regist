package com.hbpu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hbpu.entity.*;
import com.hbpu.mapper.*;
import com.hbpu.service.IDetailService;
import com.hbpu.service.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
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
public class ProvinceServiceImpl extends ServiceImpl<ProvinceMapper, Province> implements IProvinceService {

    @Autowired
    private ProvinceMapper provinceMapper;

    @Override
    public ResultDto<List> listProvince() {
        List<Province> provinceList = provinceMapper.selectList(null);
        ResultDto<List> resultDto = new ResultDto<>();
        resultDto.setTip(true);
        resultDto.setData(provinceList);
        return resultDto;
    }
}
