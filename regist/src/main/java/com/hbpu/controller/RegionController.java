package com.hbpu.controller;


import com.hbpu.entity.ResultDto;
import com.hbpu.service.IRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/region")
@CrossOrigin
public class RegionController {
    @Autowired
    private IRegionService iRegionServicel;

    @GetMapping("/listRegionByCityId")
    private ResultDto<List> listRegionByCityId(String cityId){
        return iRegionServicel.listRegionByCityId(cityId);
    }

}
