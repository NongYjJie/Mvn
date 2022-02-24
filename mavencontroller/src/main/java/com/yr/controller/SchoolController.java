package com.yr.controller;

import com.yr.entity.SchStuPage;
import com.yr.entity.School;
import com.yr.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class SchoolController {
    @Autowired
    private SchoolService schoolService;

    @RequestMapping(value = "/querySch",method = RequestMethod.GET)
    @ResponseBody
    public SchStuPage<School> query(SchStuPage<School> page, HttpServletResponse response) throws IOException {
        try {
            schoolService.querySch(page);
        } catch (Exception e) {
            e.printStackTrace();
            String json= "{\"mark\":0}";
            response.getWriter().write(json);
        }
        return page;
    }
    /**
     * 添加
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/insterSch",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public void inster(School school,HttpServletResponse response) throws Exception {
        try {
            int id = schoolService.insterSch(school);
            String json= "{\"mark\":"+id+"}";
            response.getWriter().write(json);
        } catch (Exception e) {
            e.printStackTrace();
            String json= "{\"mark\":0}";
            response.getWriter().write(json);
        }
    }

    /**
     * 根据ID来回显修改
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateSch/{id}",method = RequestMethod.GET)
    @ResponseBody
    public School getId(@PathVariable("id") Integer id) throws Exception {
        School school = schoolService.querySchById(id);
        return school;
    }

    /**
     * 修改
     * @param
     * @param resp
     * @throws IOException
     */
    @RequestMapping(value = "/updateSch",method = RequestMethod.PUT)
    public void updateSch(School school, HttpServletResponse resp) throws IOException {
/*        School sch = new School();
        sch.setId(school.getId());
        sch.setSchName(school.getSchName());
        sch.setSchAddr(school.getSchAddr());*/

        try {
            schoolService.updateSch(school);
            String json= "{\"mark\":1}";
            resp.getWriter().write(json);
        } catch (Exception e) {
            e.printStackTrace();
            String json= "{\"mark\":0}";
            resp.getWriter().write(json);
        }
    }

    /**
     * 删除
     * @param id
     * @param resp
     * @throws IOException
     */
    @RequestMapping(value = "/deleteSch/{id}",method = RequestMethod.DELETE,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void schDelete(@PathVariable("id") Integer id,HttpServletResponse resp) throws IOException {
        try {
            schoolService.deleteSch(id);
            String json = "{\"mark\":1}";
            resp.getWriter().write(json);
        } catch (Exception e) {
            e.printStackTrace();
            String json = "{\"mark\":0}";
            resp.getWriter().write(json);
        }
    }
}
