package com.cest.pojo;

import com.excel.poi.annotation.ExportField;
import com.excel.poi.annotation.ImportField;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 导出导入实体对象
 *
 * 导出注解说明
 * columnName:导出Excel列名
 * scale:导出BigDecimal类型格式化精度
 * roundingMode:导出BigDecimal类型舍入规则
 * dateFormat:导出Data类型格式化模式
 * defaultCellValue:导出模板默认值
 *
 * 导入注解说明
 * required:是否非空校验
 * regex:正则校验规则
 * regexMessage:正则校验失败信息
 * scale:导出BigDecimal类型格式化精度
 * roundingMode:导出BigDecimal类型舍入规则
 * dateFormat:导出Data类型格式化模式
 */
@Data
@ToString
@Table(name = "userentity")
public class UserEntity {
    /**
     * Integer类型字段
     */
    @ExportField(columnName = "ID", defaultCellValue = "1")
    @ImportField(required = true)
    private Integer id;
    /**
     * String类型字段
     */
    @ExportField(columnName = "姓名", defaultCellValue = "张三")
    @ImportField
    private String name;
    /**
     * BigDecimal类型字段
     */
    @ExportField(columnName = "收入金额", defaultCellValue = "100", scale = 2, roundingMode= BigDecimal.ROUND_HALF_EVEN)
    @ImportField(scale = 2, roundingMode=BigDecimal.ROUND_HALF_EVEN)
    private BigDecimal money;
    /**
     * Date类型字段
     */
    @ExportField(columnName = "创建时间")
    @ImportField
    @Column(name = "date")
    private String birthDayTime;
}