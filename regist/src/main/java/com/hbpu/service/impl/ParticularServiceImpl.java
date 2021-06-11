package com.hbpu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbpu.entity.DataDto;
import com.hbpu.entity.PageResult;
import com.hbpu.entity.Particular;
import com.hbpu.entity.User;
import com.hbpu.mapper.ParticularMapper;
import com.hbpu.service.IParticularService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * VIEW 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-02-16
 */
@Service
public class ParticularServiceImpl extends ServiceImpl<ParticularMapper, Particular> implements IParticularService {

    @Autowired
    private ParticularMapper particularMapper;
    @Override
    public PageResult<Particular> listParticularByWhere(DataDto<Particular> dataDto) {
        Page<Particular> page = new Page<>(dataDto.getPage(), dataDto.getLimit());
        QueryWrapper<Particular> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(dataDto.getData().getName())){
            queryWrapper.like("name",dataDto.getData().getName());
        }
        if (!StringUtils.isEmpty(dataDto.getData().getInfo())){
            queryWrapper.eq("info",dataDto.getData().getInfo());
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        queryWrapper.eq("communityid",user.getCommunityId());
        Page<Particular> particularPage = particularMapper.selectPage(page, queryWrapper);
        PageResult<Particular> pageResult = new PageResult<>();
        if (particularPage.getSize()>0 && particularPage.getCurrent() >0){
            pageResult.setTotal(particularPage.getTotal());
            pageResult.setPages(particularPage.getPages());
            pageResult.setData(particularPage.getRecords());
        }
        return pageResult;
    }
}
