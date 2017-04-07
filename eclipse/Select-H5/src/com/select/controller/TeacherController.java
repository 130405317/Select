package com.select.controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.common.util.FormDataCollectUtil;
import com.select.service.TeacherService;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
	@Resource
	public TeacherService teacherService;

	@CrossOrigin(origins = "http://127.0.0.1:8020", maxAge = 3600, methods = { RequestMethod.POST })
	@RequestMapping("/getTeacherById")
	@ResponseBody
	public Map<String, Object> getTeacherById(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = null;
		Map<String, Object> param = FormDataCollectUtil.getInstance().getFormData(request);
		map = teacherService.getTeacherById(param);
		return map;
	}

	@CrossOrigin(origins = "http://127.0.0.1:8020", maxAge = 3600, methods = { RequestMethod.POST })
	@RequestMapping("/getTeachersByPosition")
	@ResponseBody
	public List<Map<String, Object>> getTeachersByPosition(HttpServletRequest request, HttpServletResponse response) {
		List<Map<String, Object>> list = null;
		Map<String, Object> param = FormDataCollectUtil.getInstance().getFormData(request);
		list = teacherService.getTeachersByPosition(param);
		return list;
	}

	@CrossOrigin(origins = "http://127.0.0.1:8020", maxAge = 3600, methods = { RequestMethod.POST })
	@RequestMapping("/updateTeacher")
	public void updateTeacher(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> param = FormDataCollectUtil.getInstance().getFormData(request);
		if ("tbirthday_year".equals(param.get("type")) || "graduateTime".equals(param.get("type"))
				|| "workTime".equals(param.get("type"))) {
			DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;
			try {
				date = format1.parse((String) param.get("message"));
				param.put("message", date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		teacherService.updateTeacher(param);
	}
	
	@CrossOrigin(origins = "http://127.0.0.1:8020", maxAge = 3600, methods = { RequestMethod.POST })
	@RequestMapping("/getAllTeachers")
	@ResponseBody
	public List<Map<String, Object>> getAllTeachers(HttpServletRequest request, HttpServletResponse response){
		List<Map<String, Object>> list = null;
		list = teacherService.getAllTeachers();
		return list;
	}
	
	
	@CrossOrigin(origins = "http://127.0.0.1:8020", maxAge = 3600, methods = { RequestMethod.POST })
	@RequestMapping("tea_img_upload")
	public String teaSpringUpload(HttpServletRequest request) throws IllegalStateException, IOException {
		// 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获取multiRequest 中所有的文件名
			Iterator iter = multiRequest.getFileNames();

			while (iter.hasNext()) {
				// 一次遍历所有文件
				MultipartFile file = multiRequest.getFile(iter.next().toString());
				if (file != null) {
					String path = "E:/upload" + file.getOriginalFilename();
					// 上传
					file.transferTo(new File(path));
				}

			}

		}

		return "/success";
	}

	
}
