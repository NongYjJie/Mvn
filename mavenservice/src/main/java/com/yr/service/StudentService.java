package com.yr.service;


import com.yr.entity.SchStuPage;
import com.yr.entity.Student;

public interface StudentService<T> {

    public void querystu(SchStuPage<Student> page)throws Exception;


    public int inster(Student student);
    /**
     * 根据ID查询 ，回显数据
     */
    public Student queryStudentById(int id);
    /**
     * 修改
     */
    public void updateStudent(Student student);
    /**
     * 删除
     */
    public void deleteStudent(int id);

}
