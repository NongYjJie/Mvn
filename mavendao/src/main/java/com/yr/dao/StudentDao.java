package com.yr.dao;


import com.yr.entity.SchStuPage;
import com.yr.entity.Student;

public interface StudentDao<T> {
    /**
     * 查询
     * @param page 分页
     * @throws Exception 异常
     */
    public void querystu(SchStuPage<Student> page)throws Exception;
    /**
     * 添加
     */
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
