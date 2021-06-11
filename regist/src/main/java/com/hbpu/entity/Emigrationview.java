package com.hbpu.entity;

import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * VIEW
 * </p>
 *
 * @author jobob
 * @since 2021-02-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Emigrationview implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 迁出id
     */
    @TableField("emigrationId")
    private Integer emigrationid;

    /**
     * 户口号
     */
    @TableField("accountId")
    private Integer accountid;

    /**
     * 户籍号
     */
    @TableField("registId")
    private String registid;

    /**
     * 人员名称
     */
    private String name;

    /**
     * 人员性别
     */
    private Integer sex;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @TableField("emigrationData")
    private LocalDate emigrationdata;
    /**
     * 身份证号码
     */
    private String identity;

    /**
     * 楼层名称
     */
    private String floor;

    /**
     * 楼层信息
     */
    private String info;

    /**
     * 楼层id
     */
    @TableField("levelId")
    private Long levelid;
    private Integer detailId;

    @TableField("communityName")
    private String communityName;

}
