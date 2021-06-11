package com.hbpu.service;

import com.hbpu.entity.DataDto;
import com.hbpu.entity.Level;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hbpu.entity.PageResult;
import com.hbpu.entity.ResultDto;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2021-02-14
 */
public interface ILevelService extends IService<Level> {
    public ResultDto<List> getLevelInfo();
    public ResultDto<List> getAllLevel();
    public PageResult<Level> getPageLevel(DataDto<Level> levelDataDto);
    public ResultDto savaLevel(Level level);
}
