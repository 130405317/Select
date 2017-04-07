
package com.select.controller;

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
import com.common.util.FormDataCollectUtil;
import com.select.service.StudentService;
import com.select.service.TeacherService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Resource
	private TeacherService teacherService;
	@Resource
	private StudentService studentService;

	/**
	 * 
	 * <p>
	 * 登录验证
	 * </p>
	 * 
	 * @author: 徐德荣
	 * @date: 2016年11月6日
	 *
	 */
	@CrossOrigin(origins = "http://127.0.0.1:8020", maxAge = 3600, methods = { RequestMethod.POST })
	@RequestMapping("/loginCheck")
	@ResponseBody
	public int loginCheck(HttpServletRequest request, HttpServletResponse response) {
		int result = 0;
		List<Map<String, Object>> list = null;
		Map<String, Object> param = FormDataCollectUtil.getInstance().getFormData(request);
		String id = param.get("id").toString();
		if (id.length() == 5) {
			list = teacherService.checkTeacher(param);
			if (list.size() == 1) {
				result = 2;
			}
		} else {
			list = studentService.checkStudent(param);
			if (list.size() == 1) {
				result = 1;
			}
		}
		return result;
	}
}
