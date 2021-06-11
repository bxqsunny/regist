package com.hbpu.entity;

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
public class City implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "city_id")
    private Integer cityId;

    private String cityName;
    private String provinceId;
}
