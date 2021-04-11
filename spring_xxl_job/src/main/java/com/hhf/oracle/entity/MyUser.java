package com.hhf.oracle.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@TableName("MY_ORACLE_USER")
@KeySequence(value = "MY_SEQ",clazz = Long.class)
@Data
public class MyUser {

//    @TableId(value = "ID", type = IdType.INPUT)
    private Long id;
    private String name;
    private Double money;
    private LocalDateTime birth;

    @TableField(exist = false)
    private Integer currentPage;
    @TableField(exist = false)
    private Integer pageSize;

}
