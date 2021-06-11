package com.hbpu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

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
public class Community implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "Id", type = IdType.AUTO)
    private Integer id;


    /**
     * 社区名称
     */
    private String name;

    /**
     * 社区相关描述
     */
    @TableField("`describe`")
    private String describe;

    /**
     * 所属的社区Id，
     */
    @TableField("areaid")
    private Integer areaId;

    /**
     * 社区所属省份
     */
    @TableField(exist = false)
    private Province province;
    /**
     * 社区所属城市
     */
    @TableField(exist = false)
    private City city;
    /**
     * 社区所属辖区
     */
    @TableField(exist = false)
    private Region region;

}
