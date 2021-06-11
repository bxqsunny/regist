package com.hbpu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2021-02-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Emigration implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 迁出id
     */
    @TableId(value = "emigrationId", type = IdType.AUTO)
    private Integer emigrationid;

    /**
     * 迁出人姓名
     */
    @TableField(value = "accountId")
    private Integer accountid;

    /**
     * 迁出前所住楼层
     */
    private String floor;
    @TableField(exist = false)
    private String commName;
    @TableField(exist = false)
    private Integer commId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @TableField(value = "emigrationData")
    private LocalDateTime emigrationdata;

    @TableField("detail_id")
    private Integer detailid;

    @TableField(exist = false)
    private Integer beforeComId;

}
