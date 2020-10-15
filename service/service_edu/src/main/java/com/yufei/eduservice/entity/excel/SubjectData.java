package com.yufei.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SubjectData {
    @ApiModelProperty("一级目录")
    @ExcelProperty(value = "一级目录",index = 0)
    private String oneSubjectName;
    @ApiModelProperty("二级目录")
    @ExcelProperty(value = "二级目录",index = 1)
    private String twoSubjectName;
}
