package com.yr.service;

import com.yr.dao.SchoolDao;
import com.yr.entity.SchStuPage;
import com.yr.entity.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *  nongyijie
 */
@Service(value = "schoolService")
public class SchoolServiceImpl implements SchoolService{
    @Autowired
    private SchoolDao schoolDao;

    @Override
    @Transactional
    public void querySch(SchStuPage page) throws Exception{
        schoolDao.querySch(page);
    }

    @Override
    @Transactional
    public int insterSch(School school) {
       return schoolDao.insterSch(school);
    }

    @Override
    @Transactional
    public School querySchById(int id) {
        return schoolDao.querySchById(id);
    }

    @Override
    @Transactional
    public void updateSch(School school) {
        schoolDao.updateSch(school);
    }

    @Override
    @Transactional
    public void deleteSch(int id) {
        schoolDao.deleteSch(id);
    }

    @Override
    @Transactional
    public List<School> querySchName() {
        return schoolDao.querySchName();
    }
}
