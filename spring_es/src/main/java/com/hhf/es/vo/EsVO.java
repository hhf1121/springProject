package com.hhf.es.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author yangyun
 * @Description: 用户搜索文档列表
 * @date 2020/12/9 16:45
 */
@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class EsVO {

    private static final long serialVersionUID = -7971234569691119548L;

    @ApiModelProperty(name = "id", value = "")
    private Long id;

    @ApiModelProperty(name = "title", value = "标题")
    private String title;

    @ApiModelProperty(name = "createByName", value = "创建人名称")
    private String createByName;

    @ApiModelProperty(name = "createTime", value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(name = "isEnable", value = "是否启动 1 启用 2  不启用")
    private Integer isEnable;

    @ApiModelProperty(name = "descriptionContent", value = "文档正文描述")
    private String descriptionContent;

    @ApiModelProperty(name = "fileText" ,value = "文件大文本内容")
    private String fileText;

}
