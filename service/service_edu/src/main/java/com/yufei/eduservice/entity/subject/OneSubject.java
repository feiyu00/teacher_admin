package com.yufei.eduservice.entity.subject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sun.javafx.beans.IDProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//一级分类
@Data
@Accessors(chain = true)
@ApiModel(value = "一级分类", description = "课程")
public class OneSubject implements Serializable {
    @ApiModelProperty(value = "课程类别ID")
    private String id;
    @ApiModelProperty(value = "课程名称")
    private String title;
    //一个一级分类对应多个二级分类
    private List<TwoSubject> children = new ArrayList<>();

}
