package com.hbpu.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2021-02-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Level implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "levelId", type = IdType.AUTO)
    private Integer levelId;
    /**
     * 楼层名称
     */
    private String floor;

    /**
     * 楼层信息
     */
    private String info;
    /**
     * 是否有人居住
     */
    @TableField(value = "`describe`")
    private String describe;

    @TableField(value = "communityId")
    private Integer communityId;
    @TableField(value = "commName")
    private String commName;
}
