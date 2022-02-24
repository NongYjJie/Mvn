package com.yr.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yr.entity.SchStuPage;
import com.yr.entity.School;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SchoolDaoImpl implements SchoolDao{
    private School school = new School();
    /**
     * 查询
     * @param page 分页
     * @throws Exception 异常
     */
    @Override
    public void querySch(SchStuPage page) throws Exception {
        QueryWrapper<School> queryWrapper = new QueryWrapper();
        int count= school.selectCount(queryWrapper);
        page.setSum(count);

        IPage<School> iPage = school.selectPage(new Page<School>(page.getPageNumb(),page.getPageSize()),queryWrapper);
        List<School> list = iPage.getRecords();
        page.setList(list);

    }
    /**
     * 添加
     */
    @Override
    public int insterSch(School school) {
        school.insert();
        if(school != null){
            return school.getId();
        }else {
            return 0;
        }
    }
    /**
     * 根据ID查询 ，回显数据
     */
    @Override
    public School querySchById(int id) {
        return school.selectById(id);
    }
    /**
     * 修改
     */
    @Override
    public void updateSch(School school) {
        school.updateById();
    }
    /**
     * 删除
     */
    @Override
    public void deleteSch(int id) {
        school.deleteById(id);
    }

    @Override
    public List<School> querySchName() {
        List<School> list = school.selectAll();
        return list;
    }
}
