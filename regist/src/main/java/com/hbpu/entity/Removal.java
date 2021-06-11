package com.hbpu.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
public class Removal implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "removalId", type = IdType.AUTO)
    private Integer removalId;
    /**
     * 名称
     */
    @TableField(value = "accountId")
    private Integer accountid;

    /**
     * 迁入或迁出 0为迁入 1 为迁出
     */
    private Integer way;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @TableField(value = "optionData")
    private LocalDateTime optiondata;

}
