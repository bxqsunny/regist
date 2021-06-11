package com.hbpu.controller;


import com.hbpu.entity.DataDto;
import com.hbpu.entity.PageResult;
import com.hbpu.entity.Resident;
import com.hbpu.entity.ResultDto;
import com.hbpu.service.IResidentService;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2021-02-14
 */
@RestController
@RequestMapping("/resident")
@CrossOrigin
public class ResidentController {
    @Autowired
    private IResidentService iResidentService;

    @PostMapping("/listResident")
    public PageResult<Resident> listPageResident(@RequestBody DataDto<Resident> dataDto){
        return iResidentService.listPageResident(dataDto);
    }
    @PostMapping("/registsava")
    public ResultDto saveResident(@RequestBody Resident resident){
        return iResidentService.saveResident(resident);
    }

    /**
     * 获取statue为0的
     * @return
     */
    @GetMapping("/selectStatue")
    public ResultDto selectByWhere(){
        return iResidentService.listResidentByWhere();
    }

    /**
     * 户籍核销
     * @param resident
     * @return
     */
    @PostMapping("/verification")
    public ResultDto verification(@RequestBody Resident resident){
        return iResidentService.Verification(resident);
    }

    /**
     * 根据户籍编号查看户口信息
     * @param registId
     * @return
     */
    @GetMapping("/gainRegistById")
    public ResultDto<List> gainRegistById(String registId){
        return iResidentService.gainRegistById(registId);
    }

    /**
     * 户籍信息的迁入
     * @param resident
     * @return
     */
    @PostMapping("/detailResident")
    public ResultDto detailResident(@RequestBody Resident resident){
        return null;
    }

}
