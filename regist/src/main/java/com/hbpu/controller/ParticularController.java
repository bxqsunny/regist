package com.hbpu.controller;


import com.hbpu.entity.DataDto;
import com.hbpu.entity.PageResult;
import com.hbpu.entity.Particular;
import com.hbpu.service.IParticularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * VIEW 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2021-02-16
 */
@RestController
@RequestMapping("/particular")
@CrossOrigin
public class ParticularController {
    @Autowired
    private IParticularService iParticularService;

    @PostMapping("/searchParticular")
    public PageResult<Particular> listParticularByWhere(@RequestBody DataDto<Particular> dataDto){
        return iParticularService.listParticularByWhere(dataDto);
    }


}
