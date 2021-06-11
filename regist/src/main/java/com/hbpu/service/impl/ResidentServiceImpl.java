package com.hbpu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbpu.RegistApplication;
import com.hbpu.entity.*;
import com.hbpu.mapper.ResidentMapper;
import com.hbpu.service.IResidentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sun.plugin.liveconnect.SecurityContextHelper;

import java.security.Principal;
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
public class ResidentServiceImpl extends ServiceImpl<ResidentMapper, Resident> implements IResidentService {

    @Autowired
    private ResidentMapper residentMapper;

    /**
     * 遍历户籍的基础信息
     * @param dataDto
     * @return
     */
    @Override
    public PageResult<Resident> listPageResident(DataDto<Resident> dataDto) {
        // 分页获取
        Page<Resident> residentPage = new Page<>(dataDto.getPage(),dataDto.getLimit());
        QueryWrapper<Resident> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(dataDto.getData().getName())){
            queryWrapper.like("name",dataDto.getData().getName());
        }
        if (!StringUtils.isEmpty(dataDto.getData().getStatue())){
            queryWrapper.eq("statue",dataDto.getData().getStatue());
        }
        if (!StringUtils.isEmpty(dataDto.getData().getCommunityid())){
            queryWrapper.eq("communityid",dataDto.getData().getCommunityid());
        }
        // 根据条件查询户主信息
        Page<Resident> page = residentMapper.selectPage(residentPage, queryWrapper);
        // 或获取的有数据时
        PageResult<Resident> result = new PageResult<>();
        if (page.getSize()> 0 && page.getCurrent() >0){
            result.setPages(page.getPages());
            result.setData(page.getRecords());
            result.setTotal(page.getTotal());
        }
        return result;
    }

    @Override
    public ResultDto saveResident(Resident resident) {
        try {
            if (resident.getAccountid() != null){
                // 当不为空时表示修改
                residentMapper.updateById(resident);
                return new ResultDto(true,"修改成功");
            }else {
                // 添加户籍信息
                residentMapper.insert(resident);
                if (resident.getChildrenResident().size() >0){
                    // 判断是否有子节点
                    List<Resident> residentList = resident.getChildrenResident();
                    for (Resident resident1 : residentList) {
                        // 设置其亲属的地址
                        resident1.setAddress(resident.getAddress());
                        // 设置其亲属的户口号
                        resident1.setRegistid(resident.getRegistid());
                        // 设置其亲属的所属社区
                        resident1.setCommunityid(resident.getCommunityid());
                        residentMapper.insert(resident1);
                    }
                }
                return new ResultDto(true,"添加成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResultDto(false,"请规范输入");
        }
    }

    @Override
    /**
     * 获取有户籍但是暂未迁入的 即statue为0的
     */
    public ResultDto<List> listResidentByWhere() {
        QueryWrapper<Resident> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("statue",0);
        List<Resident> residents = residentMapper.selectList(queryWrapper);
        ResultDto<List> resultDto = new ResultDto<>();
        resultDto.setData(residents);
        resultDto.setTip(true);
        return resultDto;
    }

    @Override
    public ResultDto Verification(Resident resident) {
        if (!StringUtils.isEmpty(resident.getStatue())){
            // 修改核销原因
            Resident resident1 = new Resident();
            resident1.setAccountid(resident.getAccountid());
            resident1.setStatue(resident.getStatue());
            residentMapper.updateById(resident1);
            return new ResultDto(false,"核销成功");
        }
        return new ResultDto(false,"核销原因不能为空");
    }

    @Override
    public ResultDto<List> gainRegistById(String residentId) {
        if (StringUtils.isEmpty(residentId)){
            return new ResultDto<>(false,"户籍信息不能为空");
        }
        QueryWrapper<Resident> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("registId",residentId);
        List<Resident> residents = residentMapper.selectList(queryWrapper);
        ResultDto<List> resultDto = new ResultDto<>();
        resultDto.setTip(true);
        resultDto.setData(residents);
        return resultDto;
    }

    @Override
    public ResultDto detailResident(Resident resident) {
        if (StringUtils.isEmpty(resident.getAccountid())){
            return new ResultDto<>(false,"户籍信息不能为空");
        }
        // 当户籍信息为空时，表示添加到当前社区
        if (StringUtils.isEmpty(resident.getCommunityid())){
           User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
           resident.setCommunityid(principal.getCommunityId());
        }
        residentMapper.updateById(resident);
        return new ResultDto<>(true,"迁入成功");
    }

}
