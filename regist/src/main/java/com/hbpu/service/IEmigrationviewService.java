package com.hbpu.service;

import com.hbpu.entity.DataDto;
import com.hbpu.entity.Emigrationview;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hbpu.entity.PageResult;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 * VIEW 服务类
 * </p>
 *
 * @author jobob
 * @since 2021-02-19
 */
public interface IEmigrationviewService extends IService<Emigrationview> {
    public PageResult<Emigrationview> listEmigrationByWhere(DataDto<Emigrationview> dataDto);
}
