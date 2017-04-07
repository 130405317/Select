package com.select.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.select.dao.StudentDao;
import com.select.service.StudentService;

@Service("studentService")
public class StudentServiceImpl implements StudentService {
	@Resource
	private StudentDao studentDao;
	
	@Override
	public List<Map<String,Object>> checkStudent(Map<String, Object> param) {
		List<Map<String,Object>> list = studentDao.checkStudent(param);
		return list;
	}

	@Override
	public List<Map<String, Object>> getStudentsByClass(Map<String, Object> param) {
		List<Map<String,Object>> list = studentDao.getStudentsByClass(param);
		return list;
	}

	@Override
	public Map<String, Object> getStudentById(Map<String, Object> param) {
		Map<String,Object> map = studentDao.getStudentById(param);
		return map;
	}

	@Override
	public void updateStudent(Map<String, Object> param) {
		studentDao.updateStudent(param);
	}

	@Override
	public List<String> getAllStudents() {
		List<Map<String, Object>> list = studentDao.getAllStudents();
		List<String> list_data = new ArrayList<String>();
		for(Map<String, Object> map: list){
			list_data.add(String.valueOf(map.get("S_ID")));
		}
		return list_data;
	}

}
