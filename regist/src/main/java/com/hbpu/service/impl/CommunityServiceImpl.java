package com.hbpu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbpu.entity.*;
import com.hbpu.mapper.*;
import com.hbpu.service.ICommunityService;
import com.hbpu.service.IDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Date;
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
public class CommunityServiceImpl extends ServiceImpl<CommunityMapper, Community> implements ICommunityService {

    @Autowired
    private CommunityMapper communityMapper;


    /**
     * 获取所有的社区信息
     * @return
     */
    @Override
    public PageResult listCommunity(DataDto<Community> dataDto) {
        String name = null;
        if (!StringUtils.isEmpty(dataDto.getData().getName())){
            name = dataDto.getData().getName();
        }
        Page<Community> page = new Page<>(dataDto.getPage(),dataDto.getLimit());
        List<Community> communityList = communityMapper.listCommunity(page,name);
        PageResult<Community> pageResult = new PageResult<>();
        if (communityList.size() <= 0 ){
            pageResult.setData(null);
            return pageResult;
        }
        pageResult.setData(communityList);
        pageResult.setTotal((long)communityList.size());
        return pageResult;
    }

    /**
     * 根据有无Id判断添加或者修改数据
     * @param community
     * @return
     */
    @Override
    public ResultDto saveOrUpdateCommunity(Community community) {
        ResultDto<Community> resultDto = new ResultDto<>();
        if (StringUtils.isEmpty(community.getId())){
            // 为空时表示添加社区信息
            community.setAreaId(community.getRegion().getRegionId());
            communityMapper.insert(community);
            resultDto.setTip(true);
            resultDto.setMsg("添加成功");
            return resultDto;
        }
        // 修改数据
        community.setAreaId(community.getRegion().getRegionId());
        communityMapper.updateById(community);
        resultDto.setTip(true);
        resultDto.setMsg("修改成功");
        return resultDto;
    }


}
