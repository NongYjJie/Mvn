package com.yr.service;

import com.yr.dao.StudentDao;
import com.yr.entity.SchStuPage;
import com.yr.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *  nongyijie
 */
@Service
public class StudentServiceImpl implements StudentService{
    @Autowired
    private StudentDao studentDao;

    @Override
    @Transactional
    public void querystu(SchStuPage page) throws Exception{
        studentDao.querystu(page);
    }

    @Override
    @Transactional
    public int inster(Student student) {
       return studentDao.inster(student);
    }

    @Override
    @Transactional
    public Student queryStudentById(int id) {
        return studentDao.queryStudentById(id);
    }

    @Override
    @Transactional
    public void updateStudent(Student student) {
        studentDao.updateStudent(student);
    }

    @Override
    @Transactional
    public void deleteStudent(int id) {
        studentDao.deleteStudent(id);
    }

}
