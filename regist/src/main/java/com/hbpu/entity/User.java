package com.hbpu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

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
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "userId", type = IdType.AUTO)
    private Integer userId;
    /**
     * 管理员名称
     */
    @TableField("userName")
    private String username;

    /**
     * 管理员性别
     */
    private String sex;

    /**
     * 管理员年龄
     */
    private Long age;

    /**
     * 管理员登录密码
     */
    private String password;

    /**
     * 联系电话
     */
    private String phone;

    @TableField(value = "communityId")
    private Integer communityId;
}
