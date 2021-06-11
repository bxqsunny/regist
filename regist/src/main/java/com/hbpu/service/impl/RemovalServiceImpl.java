package com.hbpu.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hbpu.entity.Removal;
import com.hbpu.entity.ResultDto;
import com.hbpu.entity.User;
import com.hbpu.mapper.RemovalMapper;
import com.hbpu.service.IRemovalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-02-14
 */
@Service
public class RemovalServiceImpl extends ServiceImpl<RemovalMapper, Removal> implements IRemovalService {

    @Autowired
    private RemovalMapper removalMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public ResultDto<List> detailEchart() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 获取迁ru 的一个记录和个数
        String echart = stringRedisTemplate.boundValueOps("detailEchart"+user.getCommunityId()).get();
        if (StringUtils.isEmpty(echart)){
            synchronized (this){
                echart = stringRedisTemplate.boundValueOps("detailEchart"+user.getCommunityId()).get();
                if (StringUtils.isEmpty(echart)){
                    List<Map> list = removalMapper.DetailEchart(0);
                    try {
                        stringRedisTemplate.boundValueOps("detailEchart"+user.getCommunityId()).set(new ObjectMapper().writeValueAsString(list));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    return new ResultDto<>(true,"",list);
                }
            }
        }
        if (echart != null){
            try {
                List list = new ObjectMapper().readValue(echart, List.class);
                return new ResultDto<>(true,"",list);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return new ResultDto<>(false,"暂没有数据");
    }
    @Override
    public ResultDto<List> emigrationEchart() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 获取迁出的一个记录和个数
        String echart = stringRedisTemplate.boundValueOps("emigrationEchart"+user.getCommunityId()).get();
        if (StringUtils.isEmpty(echart)){
            synchronized (this){
                echart = stringRedisTemplate.boundValueOps("emigrationEchart"+user.getCommunityId()).get();
                if (StringUtils.isEmpty(echart)){
                    List<Map> list = removalMapper.DetailEchart(1);
                    try {
                        stringRedisTemplate.boundValueOps("emigrationEchart"+user.getCommunityId()).set(new ObjectMapper().writeValueAsString(list));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    return new ResultDto<>(true,"",list);
                }
            }
        }
        if (echart != null){
            try {
                List list = new ObjectMapper().readValue(echart, List.class);
                return new ResultDto<>(true,"",list);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return new ResultDto<>(false,"暂没有数据");
    }
}
