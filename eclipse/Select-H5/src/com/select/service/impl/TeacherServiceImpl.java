package com.select.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.select.dao.TeacherDao;
import com.select.service.TeacherService;

@Service("TeacherService")
public class TeacherServiceImpl implements TeacherService {
	@Resource
	private TeacherDao teacherDao;

	@Override
	public List<Map<String, Object>> checkTeacher(Map<String, Object> param) {
		List<Map<String,Object>> list = teacherDao.checkTeacher(param);
		return list;
	}

	@Override
	public Map<String, Object> getTeacherById(Map<String, Object> param) {
		Map<String,Object> map = teacherDao.getTeacherById(param);
		return map;
	}

	@Override
	public List<Map<String, Object>> getTeachersByPosition(Map<String, Object> param) {
		List<Map<String,Object>> list = teacherDao.getTeachersByPosition(param);
		return list;
	}

	@Override
	public void updateTeacher(Map<String, Object> param) {
		teacherDao.updateTeacher(param);
	}

	@Override
	public List<Map<String, Object>> getAllTeachers() {
		List<Map<String, Object>> list = teacherDao.getAllTeachers();
		return list;
	}

}
