package com.hbpu.mapper;

import com.hbpu.entity.Removal;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2021-02-14
 */
public interface RemovalMapper extends BaseMapper<Removal> {

    @Select("SELECT optionData,COUNT(optionData) as num FROM removal \n" +
            "WHERE way = #{way} GROUP BY optionData limit 7")
    public List<Map> DetailEchart(Integer way);
}
