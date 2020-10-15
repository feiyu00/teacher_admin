package com.atguigu.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ExcelData {
    @ApiModelProperty("学生编号")
    @ExcelProperty(value = "学生编号",index = 0)
    private  Integer sno;
    @ApiModelProperty("学生名称")
    @ExcelProperty(value = "学生姓名",index = 1)
    private String sname;

}
