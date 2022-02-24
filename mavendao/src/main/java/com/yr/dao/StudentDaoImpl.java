package com.yr.dao;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yr.entity.SchStuPage;
import com.yr.entity.School;
import com.yr.entity.Student;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class StudentDaoImpl implements StudentDao{
    private Student student = new Student();

/**
     * 查询
     * @param page 分页
     * @throws Exception 异常*/


    @Override
    public void querystu(SchStuPage page) throws Exception {
        QueryWrapper<Student> queryWrapper = new QueryWrapper();
        int count= student.selectCount(queryWrapper);
        page.setSum(count);

        IPage<Student> iPage = student.selectPage(new Page<Student>(page.getPageNumb(),page.getPageSize()),queryWrapper);
        List<Student> list = iPage.getRecords();
        for (Student student : list) {
            School school = new School();
            School school1 = school.selectById(student.getSchId());
            student.setSchool(school1);
        }

        page.setList(list);

    }

/**
     * 添加*/


    @Override
    public int inster(Student student) {
        student.insert();
        if(student != null){
            return student.getId();
        }else {
            return 0;
        }
    }
/*
     * 根据ID查询 ，回显数据
*/
    @Override
    public Student queryStudentById(int id){
        return student.selectById(id);
    }


/*
     * 修改
*/
    @Override
    public void updateStudent(Student student) {
        student.updateById();
    }

/**
     * 删除*/


    @Override
    public void deleteStudent(int id) {
//        Session session = sessionFactory.getCurrentSession();
//        Student student = session.get(Student.class,id);
//        if(student != null){
//            session.remove(student);
//        }
//        studentMapper.deleteStudent(id);
        if(student != null) {
        }
        student.deleteById(id);
    }
}
