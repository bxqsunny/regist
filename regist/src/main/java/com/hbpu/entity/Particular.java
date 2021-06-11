package com.hbpu.entity;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * VIEW
 * </p>
 *
 * @author jobob
 * @since 2021-02-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Particular implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 户籍详情主键
     */
    @TableField("detailId")
    private Integer detailid;

    /**
     * 户口号
     */
    @TableField("registId")
    private String registid;

    @TableField("accountId")
    private Integer accountId;
    /**
     * 名称
     */
    private String name;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 迁入日期
     */
    @TableField(value = "immigrationData")
    private LocalDateTime immigrationdata;
    /**
     * 居住地址
     */
    private String address;
    /**
     * 身份证号
     */
    private String identity;

    /**
     * 楼层信息
     */
    private String info;

    /**
     * 楼层名称
     */
    private String floor;


    @TableField("levelId")
    private Integer levelId;

}
