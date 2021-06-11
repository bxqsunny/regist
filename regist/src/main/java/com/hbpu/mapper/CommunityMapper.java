package com.hbpu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hbpu.entity.Community;
import com.hbpu.entity.Detail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2021-02-14
 */
public interface CommunityMapper extends BaseMapper<Community> {
    List<Community> listCommunity(@Param("page") IPage page,@Param("name") String name);
}
