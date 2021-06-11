package com.hbpu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbpu.entity.*;
import com.hbpu.mapper.*;
import com.hbpu.service.IDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-02-14
 */
@Service
public class DetailServiceImpl extends ServiceImpl<DetailMapper, Detail> implements IDetailService {

    @Autowired
    private DetailMapper detailMapper;
    @Autowired
    private EmigrationMapper emigrationMapper;
    @Autowired
    private LevelMapper levelMapper;
    @Autowired
    private ResidentMapper residentMapper;
    @Autowired
    private RemovalMapper removalMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    @Transactional
    public ResultDto saveOrUpdateDetail(Detail detail) {
        if (StringUtils.isEmpty(detail.getDetailId())){
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            // 当id为空时向迁入记录中插入一条记录,
            detail.setImmigrationdata(LocalDateTime.now());
            detailMapper.insert(detail);
            // 修改户籍的状态statue
            Resident resident = new Resident();
            resident.setAccountid(detail.getAccountid());
            resident.setStatue("1");
            resident.setCommunityid(detail.getCommId());
            resident.setCommName(detail.getCommName());
            residentMapper.updateById(resident);
            // 向removal中添加记录，便于统计
            Removal removal = new Removal();
            removal.setAccountid(detail.getAccountid());
            removal.setOptiondata(LocalDateTime.now());
            removal.setWay(0);
            removalMapper.insert(removal);
            stringRedisTemplate.delete("detailEchart"+user.getUserId());
            return new ResultDto(true,"迁入成功");
        }else {
            detailMapper.updateById(detail);
            return new ResultDto(true,"修改成功");
        }
    }

    @Override
    @Transactional
    public ResultDto emigration(Emigration emigration) {
        if (emigration.getDetailid() != null){
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            // 从迁入表格中删除
            detailMapper.deleteById(emigration.getDetailid());
            // 在迁出表格中添加迁出数据
            emigration.setEmigrationdata(LocalDateTime.now());
            emigration.setDetailid(user.getCommunityId());
            emigrationMapper.insert(emigration);
            // 修改户籍表中的状态
            Resident resident = new Resident();
            resident.setStatue("2");
            resident.setAccountid(emigration.getAccountid());
            resident.setCommunityid(emigration.getCommId());
            resident.setCommName(emigration.getCommName());
            residentMapper.updateById(resident);
            // 向统计表中添加记录
            Removal removal = new Removal();
            removal.setOptiondata(LocalDateTime.now());
            removal.setAccountid(emigration.getAccountid());
            removal.setWay(1);
            stringRedisTemplate.delete("emigrationEchart"+user.getUserId());
            removalMapper.insert(removal);
        }else {
            return new ResultDto(true,"请规范输入");
        }
        return new ResultDto(true,"迁出成功");
    }
}
