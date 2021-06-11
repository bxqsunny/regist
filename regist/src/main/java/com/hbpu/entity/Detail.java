package com.hbpu.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
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
public class Detail implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "detailId", type = IdType.AUTO)
    private Integer detailId;

    /**
     * 户口号
     */
    @TableField("accountId")
    private Integer accountid;

    /**
     * 名称
     */
    @TableField(exist = false)
    private String name;
    @TableField(exist = false)
    private String commName;
    @TableField(exist = false)
    private Integer commId;
    /**
     * 迁入日期
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @TableField(value = "immigrationData")
    private LocalDateTime immigrationdata;
    /**
     * 所住楼层
     */
    private Integer floor;

}
