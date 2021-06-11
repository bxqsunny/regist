package com.hbpu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbpu.entity.DataDto;
import com.hbpu.entity.Level;
import com.hbpu.entity.PageResult;
import com.hbpu.entity.ResultDto;
import com.hbpu.mapper.LevelMapper;
import com.hbpu.service.ILevelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

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
public class LevelServiceImpl extends ServiceImpl<LevelMapper, Level> implements ILevelService {
    @Autowired
    private LevelMapper levelMapper;

    @Override
    public ResultDto<List> getLevelInfo() {
        QueryWrapper<Level> queryWrapper = new QueryWrapper<>();
        queryWrapper.groupBy("info").select("info");
        List<Level> levels = levelMapper.selectList(queryWrapper);
        if (levels.size()>0){
            return new ResultDto<List>(true,"ok",levels);
        }
        return new ResultDto<List>(false,"null");
    }

    @Override
    public ResultDto<List> getAllLevel() {
        List<Level> levels = levelMapper.selectList(new QueryWrapper<Level>());
        if (levels.size()>0){
            return new ResultDto<List>(true,"ok",levels);
        }
        return new ResultDto<List>(false,"null");
    }

    @Override
    public PageResult<Level> getPageLevel(DataDto<Level> levelDataDto) {
        Page<Level> levelPage = new Page<>(levelDataDto.getPage(), levelDataDto.getLimit());
        QueryWrapper<Level> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(levelDataDto.getData().getInfo())){
            queryWrapper.eq("info",levelDataDto.getData().getInfo());
        }
        if (!StringUtils.isEmpty(levelDataDto.getData().getCommunityId())){
            queryWrapper.eq("communityId",levelDataDto.getData().getCommunityId());
        }
        Page<Level> page = levelMapper.selectPage(levelPage, queryWrapper);
        PageResult<Level> pageResult = new PageResult<>();
       if (page.getSize() >0){
           pageResult.setTotal(page.getTotal());
           pageResult.setPages(page.getPages());
           pageResult.setData(page.getRecords());
       }
        return pageResult;
    }

    @Override
    public ResultDto savaLevel(Level level) {
        if (!StringUtils.isEmpty(level.getLevelId())){
            levelMapper.updateById(level);
            return new ResultDto(true,"修改成功");
        }else {
            levelMapper.insert(level);
            return new ResultDto(true,"添加成功");
        }
    }


}
