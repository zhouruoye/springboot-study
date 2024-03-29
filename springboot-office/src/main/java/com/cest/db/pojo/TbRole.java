package com.cest.db.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 角色表
 *
 * @TableName tb_role
 */
@Data
public class TbRole implements Serializable {
    /**
     * 主键
     *
     * @mbg.generated 2021-02-27 10:26:44
     */
    private Integer id;

    /**
     * 角色名称
     *
     * @mbg.generated 2021-02-27 10:26:44
     */
    private String roleName;

    /**
     * 权限集合
     *
     * @mbg.generated 2021-02-27 10:26:44
     */
    private String permissions;

    private String desc;

    private String defaultPermissions;

    private Boolean systemic;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_role
     *
     * @mbg.generated 2021-02-27 10:26:44
     */
    private static final long serialVersionUID = 1L;
}