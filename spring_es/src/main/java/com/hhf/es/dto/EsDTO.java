package com.hhf.es.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yangyun
 * @Description: 知识库文档前端展示列表查询条件
 * @date 2020/12/9 11:38
 */
@Data
public class EsDTO implements Serializable {
    private static final long serialVersionUID = -7767921700425136978L;

    @ApiModelProperty(name = "keyword", value = "关键字")
    private String keyword;

    @ApiModelProperty(name = "sortField", value = "排序字段")
    private String sortField;

    @ApiModelProperty(name = "sortType", value = "排序方式")
    private String sortType;
}
