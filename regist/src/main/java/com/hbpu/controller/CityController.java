package com.hbpu.controller;


import com.hbpu.entity.City;
import com.hbpu.entity.ResultDto;
import com.hbpu.service.ICityService;
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
@RequestMapping("/city")
@CrossOrigin
public class CityController {
    @Autowired
    private ICityService iCityService;

    /**
     * 根据省份Id获取城市信息
     * @return
     */
    @GetMapping("/listCityByProvinceId")
    private ResultDto<List> listCityByProvinceId(String provinceId){
        return iCityService.listCityByProvinceId(provinceId);
    }

}
