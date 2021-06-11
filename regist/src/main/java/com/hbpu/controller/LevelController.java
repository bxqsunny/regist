package com.hbpu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hbpu.entity.DataDto;
import com.hbpu.entity.Level;
import com.hbpu.entity.PageResult;
import com.hbpu.entity.ResultDto;
import com.hbpu.service.ILevelService;
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
@RequestMapping("/level")
@CrossOrigin
public class LevelController {

    @Autowired
    public ILevelService iLevelService;
    @GetMapping("/levelInfo")
    /**
     * 获取楼层程名 info
     */
    public ResultDto<List> getLevelInfo(){
        return iLevelService.getLevelInfo();
    }
    /**
     * 获取所有房屋信息
     */
    @GetMapping("/allLevle")
    public ResultDto<List> getLevel(){
        return iLevelService.getAllLevel();
    }

    @PostMapping("/getPageLevel")
    public PageResult<Level> getPageLevel(@RequestBody DataDto<Level> dataDto){
        return iLevelService.getPageLevel(dataDto);
    }
    @PostMapping("/savaLevel")
    public ResultDto savaLevel(@RequestBody Level level){
        return iLevelService.savaLevel(level);
    }

    @GetMapping("/getLevelByCommId")
    public PageResult<Level> getLevelByCommId(Integer id){
        List<Level> levelList = iLevelService.list(new QueryWrapper<Level>().eq("communityId", id));
        PageResult<Level> pageResult = new PageResult<>();
        pageResult.setData(levelList);
        return pageResult;
    }
}
