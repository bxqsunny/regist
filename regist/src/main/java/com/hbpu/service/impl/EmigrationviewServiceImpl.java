package com.hbpu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbpu.entity.DataDto;
import com.hbpu.entity.Emigrationview;
import com.hbpu.entity.PageResult;
import com.hbpu.entity.User;
import com.hbpu.mapper.EmigrationviewMapper;
import com.hbpu.service.IEmigrationviewService;
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
 * @since 2021-02-19
 */
@Service
public class EmigrationviewServiceImpl extends ServiceImpl<EmigrationviewMapper, Emigrationview> implements IEmigrationviewService {

    @Autowired
    private EmigrationviewMapper emigrationviewMapper;

    @Override
    public PageResult<Emigrationview> listEmigrationByWhere(DataDto<Emigrationview> dataDto) {
        Page<Emigrationview> emigrationviewPage = new Page<>(dataDto.getPage(), dataDto.getLimit());
        QueryWrapper<Emigrationview> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(dataDto.getData().getName())){
            queryWrapper.like("name",dataDto.getData().getName());
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        queryWrapper.eq("detail_id",user.getCommunityId());
        IPage<Emigrationview> iPage = emigrationviewMapper.selectPage(emigrationviewPage, queryWrapper);
        PageResult<Emigrationview> pageResult = new PageResult<>();
        if (iPage.getSize()>0){
            pageResult.setData(iPage.getRecords());
            pageResult.setPages(iPage.getPages());
            pageResult.setTotal(iPage.getTotal());
        }
        return pageResult;
    }
}
