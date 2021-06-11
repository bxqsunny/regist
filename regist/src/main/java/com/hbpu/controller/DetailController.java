package com.hbpu.controller;


import com.hbpu.entity.*;
import com.hbpu.service.IDetailService;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2021-02-14
 */
@RestController
@RequestMapping("/detail")
@CrossOrigin
public class DetailController {

    @Autowired
    private IDetailService iDetailService;

    @PostMapping("/saveDetail")
   public ResultDto saveOrUpdateDetail(@RequestBody Detail detail){
       return iDetailService.saveOrUpdateDetail(detail);
   }
    @PostMapping("/emigration")
    public ResultDto emigration(@RequestBody Emigration emigration){
        return iDetailService.emigration(emigration);
    }

}
