package com.yufei.eduservice.entity.subject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "二级分类", description = "课程")
public class TwoSubject implements Serializable {
    @ApiModelProperty(value = "课程类别ID")
    private String id;
    @ApiModelProperty(value = "课程名称")
    private String title;
}
