package com.select.controller;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.select.service.SelectService;
import com.select.service.StudentService;
import com.select.service.TeacherService;

@Controller
@RequestMapping("/select")
public class SelectController {

	@Resource
	private SelectService selectService;

	@Resource
	private StudentService studentService;

	@Resource
	private TeacherService teacherService;

	/**
	 * 
	 * <p>
	 * Title:保存学生选择教师
	 * </p>
	 * 
	 * @author: 徐德荣
	 * @date: 2017年3月29日
	 *
	 */
	@CrossOrigin(origins = "http://127.0.0.1:8020", maxAge = 3600, methods = { RequestMethod.POST })
	@RequestMapping("/saveSelectRound")
	public void saveSelectRound(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> param = FormDataCollectUtil.getInstance().getFormData(request);
		List<Map<String, Object>> students = selectService.getSelectRoundBySId(param);
		if (students.size() == 0) {
			selectService.insertSelectRound(param);
		} else {
			selectService.updateSelectRound(param);
		}

	}

	/**
	 * 
	 * <p>
	 * Title:根据教师id获取教师选择结果
	 * </p>
	 * 
	 * @author: 徐德荣
	 * @date: 2017年3月29日
	 *
	 */
	@CrossOrigin(origins = "http://127.0.0.1:8020", maxAge = 3600, methods = { RequestMethod.POST })
	@RequestMapping("/getSelectRoundStudent")
	@ResponseBody
	public List<Map<String, Object>> getSelectStudentsByTId(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> param = FormDataCollectUtil.getInstance().getFormData(request);
		return selectService.getSelectStudentsByTId(param);
	}

	/**
	 *
	 * <p>
	 * Title: 保存教师选择学生
	 * </p>
	 * 
	 * @author: 徐德荣
	 * @date: 2017年3月29日
	 *
	 */
	@CrossOrigin(origins = "http://127.0.0.1:8020", maxAge = 3600, methods = { RequestMethod.POST })
	@RequestMapping("/saveSelectResult")
	@ResponseBody
	public void saveSelectResult(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> param = FormDataCollectUtil.getInstance().getFormData(request);
		List<Map<String, Object>> teachers = selectService.getSelectResultByTId(param);
		if (teachers.size() == 0) {
			selectService.insertSelectResult(param);
		} else {
			selectService.updateSelectResult(param);
		}
	}

	/**
	 * 
	 * <p>
	 * Title:系统自动分配选择
	 * </p>
	 * 
	 * @author: 徐德荣
	 * @date: 2017年3月29日
	 *
	 */
	@CrossOrigin(origins = "http://127.0.0.1:8020", maxAge = 3600, methods = { RequestMethod.POST })
	@RequestMapping("/setSelectResult")
	@ResponseBody
	public void setSelectResult(HttpServletRequest request, HttpServletResponse response) {
		// i=0时进行自动选择配对（每位教师保留一位名额）; i=1时进行使用教师的最后一个名额; i=2时进行最终配对
		for (int i = 0; i < 3; i++) {
			List<String> select_list = selectService.getAllSelectResult();
			List<String> student_list = studentService.getAllStudents();

			for (String s_id : student_list) {
				if (select_list.contains(s_id)) {

				} else {
					// 没有选择结果的学生
					System.out.println(s_id);
					if (i == 2) {
						List<Map<String, Object>> tea = teacherService.getAllTeachers();
						setFinalSelect(s_id, tea);
					} else {
						List<String> teachers = getstudentSelectRound(s_id);
						setAutoSelectTeacher(s_id, teachers, i);
					}

				}
			}

		}

	}

	/**
	 *
	 * <p>
	 * Title: 获取学生选择教师的结果
	 * </p>
	 * 
	 * @author: 徐德荣
	 * @date: 2017年3月29日
	 *
	 */
	public List<String> getstudentSelectRound(String s_id) {
		List<String> teachers = new ArrayList<String>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("sid", s_id);
		List<Map<String, Object>> list = selectService.getSelectRoundBySId(param);

		for (Map<String, Object> map : list) {
			if (map.get("ROUND1") != null) {
				teachers.add(String.valueOf(map.get("ROUND1")));
			}
			if (map.get("ROUND2") != null) {
				teachers.add(String.valueOf(map.get("ROUND2")));
			}
			if (map.get("ROUND3") != null) {
				teachers.add(String.valueOf(map.get("ROUND3")));
			}
			if (map.get("ROUND4") != null) {
				teachers.add(String.valueOf(map.get("ROUND4")));
			}
		}

		return teachers;
	}

	public void setAutoSelectTeacher(String sid, List<String> teachers, int i) {
		for (String tid : teachers) {
			int s_limit = SurplusTeaherLimit(tid);
			if (i == 1) {
				s_limit++;
			}

			if (s_limit > 1) {
				List<String> res = getTeacherResultByTId(tid);
				res.add(sid);
				// 保存选择
				saveSelectteacher(tid, res);
				break;
			}
		}
	}

	/**
	 * 
	 * <p>
	 * Title: 获取教师可选学生人数
	 * </p>
	 * 
	 * @author: 徐德荣
	 * @date: 2017年3月29日
	 *
	 */
	public int SurplusTeaherLimit(String tid) {
		// 配置参数
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", tid);
		// 获取教师人数限制
		Map<String, Object> teacher = teacherService.getTeacherById(param);
		int limit = (int) teacher.get("LIMIT");
		// 获取教师选择学生结果
		List<String> res = getTeacherResultByTId(tid);
		// 教师已选人数
		int select_num = res.size();
		return limit - select_num;
	}

	public List<String> getTeacherResultByTId(String tid) {
		List<String> res = new ArrayList<String>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("tid", Integer.parseInt(tid));
		List<Map<String, Object>> results = selectService.getSelectResultByTId(param);
		for (Map<String, Object> map : results) {
			if (map.get("RESULT") != null) {
				String[] str = ((String) map.get("RESULT")).split(",");
				for (int i = 0; i < str.length; i++) {
					res.add(str[i]);
				}
			}
		}
		return res;
	}

	public void saveSelectteacher(String tid, List<String> res) {
		System.out.println(tid);
		String sids = "";
		for (int i = 0; i < res.size(); i++) {
			if (i == (res.size() - 1)) {
				sids += res.get(i);
			} else {
				sids += res.get(i) + ",";
			}
		}
		// 配置参数
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("tid", Integer.parseInt(tid));
		param.put("result", sids);
		List<Map<String, Object>> teachers = selectService.getSelectResultByTId(param);
		if (teachers.size() == 0) {
			selectService.insertSelectResult(param);
		} else {
			selectService.updateSelectResult(param);
		}
	}

	public void setFinalSelect(String sid, List<Map<String, Object>> teachers) {
		for (Map<String, Object> tea : teachers) {
			String tid = (String) tea.get("T_ID");
			int s_limit = SurplusTeaherLimit(tid);
			if (s_limit > 0) {
				List<String> res = getTeacherResultByTId(tid);
				res.add(sid);
				// 保存选择
				saveSelectteacher(tid, res);
				break;
			}
		}
	}

}
