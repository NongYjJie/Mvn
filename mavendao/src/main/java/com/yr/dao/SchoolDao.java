package com.yr.dao;



import com.yr.entity.SchStuPage;
import com.yr.entity.School;

import java.util.List;

public interface SchoolDao<T> {
    /**
     * 查询
     * @param page 分页
     * @throws Exception 异常
     */
    public void querySch(SchStuPage<School> page)throws Exception;
    /**
     * 添加
     */
    public int insterSch(School school);
    /**
     * 根据ID查询 ，回显数据
     */
    public School querySchById(int id);

    /**
     * 修改
     */
    public void updateSch(School school);

    /**
     * 删除
     */
    public void deleteSch(int id);

    /**
     * 查询学习名称
     * @return
     */
    public List<School> querySchName();
}
