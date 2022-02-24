package com.yr.controller;

import com.yr.entity.SchStuPage;
import com.yr.entity.Student;
import com.yr.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
public class StudentController {
    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/queryStu",method = RequestMethod.GET)
    @ResponseBody
    public SchStuPage<Student> query(SchStuPage<Student> page, HttpServletResponse response) throws IOException {
        try {
            studentService.querystu(page);
        } catch (Exception e) {
            e.printStackTrace();
            String json= "{\"mark\":0}";
            response.getWriter().write(json);
        }
        return page;
    }

    /**
     * 添加
     * @param student
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/insterStu",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public void inster(Student student, @RequestParam("file") MultipartFile file, HttpServletResponse response) throws Exception {
        //获取文件名
        String fileName = getFileName(file.getOriginalFilename());
        String path = "C:\\Users\\nongyijie\\Desktop\\a\\"+fileName;
        File fileurl = new File(path);
        //这个流用来保存
        FileOutputStream fileOutput = new FileOutputStream(fileurl);
        //这个流用来接收文件上传
        InputStream fileInput = file.getInputStream();
        byte by[] = new byte[1024];
        int length = 0;
        while((length=fileInput.read(by)) != -1) {
            fileOutput.write(by,0,length);
        }
        student.setShowHead(path);

        try {
            int id = studentService.inster(student);
            String json= "{\"mark\":"+id+"}";
            response.getWriter().write(json);
        } catch (Exception e) {
            e.printStackTrace();
            String json= "{\"mark\":0}";
            response.getWriter().write(json);
        }

        fileInput.close();
        fileOutput.close();
    }

    /**
     * 为了保证文件名不重复 使用同步快
     * @return fileName
     */
    private synchronized String getFileName(String fileName) {
        //取到文件后缀
        String[] fileNames = fileName.split("\\.");
        //时间戳
        fileName = System.currentTimeMillis() +"."+fileNames[fileNames.length-1];
        return fileName;

    }
    /**
     * 文件下载
     */
    @RequestMapping(value = "/download",method = RequestMethod.GET)
    public void download(HttpServletRequest request, HttpServletResponse response, String filePath) throws IOException{

        if(filePath != null && !filePath.equals("")) {
            File file = new File(filePath);
            String fp[] = filePath.split("\\\\");
            response.setHeader("Content-Disposition","attachment;filename="+fp[fp.length-1]);
            response.addHeader("Content-Type","application/json;charset=UTF-8");
            try(
                    InputStream is = new FileInputStream(file);
                    OutputStream os = response.getOutputStream();
            ){
                int read = 0;

                byte[] bytes = new byte[2048];
                while ((read = is.read(bytes)) != -1) {
                    os.write(bytes, 0, read);
                }
            }
        }

    }


    /**
     * 根据ID来回显修改
     * @param id
     * @param resp
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/update/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Student getId(@PathVariable("id") Integer id, HttpServletResponse resp) throws Exception {
        Student student = studentService.queryStudentById(id);
        return student;
    }

    /**
     * 修改
     * @param
     * @param resp
     * @throws IOException
     */
    @RequestMapping(value = "/update",method = RequestMethod.PUT,produces = "application/json;charset=UTF-8")
    public void updateUser(Student student,@RequestParam("file") MultipartFile file, HttpServletResponse resp) throws Exception {

        String fileName = getFileName(file.getOriginalFilename());//获取文件名
        String path = "C:\\Users\\nongyijie\\Desktop\\a\\"+fileName;
        File fileurl = new File(path);
        //这个流用来保存
        FileOutputStream fileOutput = new FileOutputStream(fileurl);
        //这个流用来接收文件上传
        InputStream fileInput = file.getInputStream();
        byte by[] = new byte[1024];
        int length = 0;
        while((length=fileInput.read(by)) != -1) {
            fileOutput.write(by,0,length);
        }
        student.setShowHead(path);


        studentService.updateStudent(student);
        try {
            String json= "{\"mark\":1}";
            resp.getWriter().write(json);
        } catch (Exception e) {
            e.printStackTrace();
            String json= "{\"mark\":0}";
            resp.getWriter().write(json);
        }
        fileInput.close();
        fileOutput.close();
    }

    /**
     * 删除
     * @param id
     * @param resp
     * @throws IOException
     */
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void UserDelete(@PathVariable("id") Integer id,HttpServletResponse resp) throws IOException {
        Student student = studentService.queryStudentById(id);
        String path = student.getShowHead();
        File file = new File(path);
        file.delete();
        try {
            studentService.deleteStudent(id);
            String json = "{\"mark\":1}";
            resp.getWriter().write(json);
        } catch (Exception e) {
            e.printStackTrace();
            String json = "{\"mark\":0}";
            resp.getWriter().write(json);
        }
    }

}
