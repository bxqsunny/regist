package com.hbpu.controller;


import com.hbpu.entity.*;
import com.hbpu.service.ICommunityService;
import com.hbpu.service.IDetailService;
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
@RequestMapping("/community")
@CrossOrigin
public class CommunityController {

    @Autowired
    private ICommunityService iCommunityService;

    /**
     * 遍历显示社区信息
     * @param dataDto
     * @return
     */
    @PostMapping("/listCommunity")
    public PageResult listCommunity(@RequestBody DataDto<Community> dataDto){
        return iCommunityService.listCommunity(dataDto);
    }

    @GetMapping("/listCommunity")
    public ResultDto listCommunityName(){
        return new ResultDto(true,"",iCommunityService.list());
    }

    @RequestMapping("/savaOrUpdateCommunity")
    public ResultDto saveOrUpdateCommunity(@RequestBody Community community){
        return iCommunityService.saveOrUpdateCommunity(community);
    }

}
