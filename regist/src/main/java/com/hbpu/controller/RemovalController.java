package com.hbpu.controller;


import com.hbpu.entity.PageResult;
import com.hbpu.entity.Removal;
import com.hbpu.entity.ResultDto;
import com.hbpu.service.IRemovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2021-02-14
 */
@RestController
@RequestMapping("/removal")
@CrossOrigin
public class RemovalController {
    @Autowired
    private IRemovalService iRemovalService;

    @GetMapping("/detailInfo")
    public ResultDto<List> detailEchart(){
        return iRemovalService.detailEchart();
    }
    @GetMapping("/emigrationEchart")
    public ResultDto<List> emigrationEchart(){
        return iRemovalService.emigrationEchart();
    }
}
