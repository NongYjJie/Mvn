package com.yr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@TableName("schooltest")
public class School extends Model<School> {
     @TableId(value = "id" ,type = IdType.AUTO)
    private int id;
    private String schName;
    private String schAddr;

    @TableField(exist = false)//表示该字段不是数据库中字段但是必须要使用的
    @JsonIgnore
    private Set<Student> students = new HashSet<>();


    @Override
    public String toString() {
        return "School{" +
                "id=" + id +
                ", schName='" + schName + '\'' +
                ", schAddr='" + schAddr + '\'' +
                ", students=" + students +
                '}';
    }
}
