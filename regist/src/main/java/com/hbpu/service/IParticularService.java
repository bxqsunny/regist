package com.hbpu.service;

import com.hbpu.entity.DataDto;
import com.hbpu.entity.PageResult;
import com.hbpu.entity.Particular;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * VIEW 服务类
 * </p>
 *
 * @author jobob
 * @since 2021-02-16
 */
public interface IParticularService extends IService<Particular> {
    public PageResult<Particular> listParticularByWhere(DataDto<Particular> dataDto);
}
