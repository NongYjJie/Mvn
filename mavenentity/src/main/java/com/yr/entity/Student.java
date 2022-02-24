package com.yr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@TableName("student")
public class Student extends Model<Student> {

    @TableId(value = "id" ,type = IdType.AUTO)
    private int id;
    //学生姓名
    private String stuName;
    //处理查询请求时的日期问题
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    //处理增加时为日期问题
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date age;
    //学生班级
    private String stuClass;
    //1 male男, 0 female 女
    private int gender;
    //学校id
    private int schId;
    //头像
    private String showHead;
    @TableField(exist = false)//表示该字段不是数据库中字段但是必须要使用的
    private School school;


    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", stuName='" + stuName + '\'' +
                ", age=" + age +
                ", studentClass='" + stuClass + '\'' +
                ", gender=" + gender +
                ", sch_id=" + schId +
                '}';
    }
}
