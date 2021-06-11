package com.hbpu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

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
public class Resident implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 户口号
     */
    @TableId(value = "accountId", type = IdType.AUTO)
    private Integer accountid;
    /**
     * 户籍号
     */
    @TableField(value = "registId")
    private String registid;

    /**
     * 户主名
     */
    private String name;

    /**
     * 户主性别
     */
    private Integer sex;

    /**
     * 户主迁入日期
     */
    private LocalDateTime birthday;

    /**
     *  人员身份证
     */
    private String identity;
    /**
     * 与户主关系  1 本人 2 配偶 3 子女
     */
    private Integer relation;
    /**
     * 人员所属民族
     */
    private String nation;
    /**
     * 文化程度
     */
    private String education;
    /**
     * 户籍迁入迁出状态
     */
    private String statue;

    /**
     * 所属社区 ,可根据所属社区id获取到地址
     */
    @TableField(value = "communityid")
    private Integer communityid;

    /**
     * 该人员的详细地址
     */
    private String address;
    private String commName;

    /**
     * 该人和其家属
     */
    @TableField(exist = false)
    private List<Resident> childrenResident;
}
