package com.hhf.entity;

/**
 * book类，jpa操作对象
 */

import com.alibaba.druid.support.monitor.annotation.MTable;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;



@Data
public class Book {

    private  Long id;
    private  String name;
    private String author;
    private Integer count;
    @TableField("countSize")
    private Integer countSize;
}
