package com.yr.controller;


import com.yr.entity.School;
import com.yr.service.SchoolService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.List;

public class MyTagSchoolName extends SimpleTagSupport {
	//标签的id的值
	private String id;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public SchoolService schoolService;

	public SchoolService getSchoolService() {
		return schoolService;
	}

	public void setSchoolService() {
		PageContext pageContext = (PageContext) this.getJspContext();
		ServletContext servletContext = pageContext.getServletContext();
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		this.schoolService = (SchoolService) wac.getBean("schoolService");
		this.schoolService = schoolService;
	}

	@Override
	public void doTag() throws JspException, IOException {
		setSchoolService();

		List<School> list = schoolService.querySchName();
		JspWriter out = getJspContext().getOut();
		StringBuffer buffer = new StringBuffer();
		buffer.append("<select name=\"schName\" id=\""+id+"\">");
		buffer.append("<option value=\"0\">").append("请选择地址").append("</option>");
		for (School schName : list) {
				buffer.append("<option value=").append(schName.getId()).append(">").append(schName.getSchName()).append("</option>");

		}
		buffer.append("</select>");
		out.print(buffer.toString());

	}
}
