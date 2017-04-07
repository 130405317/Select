package com.select.service;

import java.util.List;
import java.util.Map;

public interface TeacherService {

	public List<Map<String,Object>> checkTeacher(Map<String, Object> param);
	
	public Map<String,Object> getTeacherById(Map<String, Object> param);
	
	public List<Map<String, Object>> getTeachersByPosition(Map<String, Object> param);

	public void updateTeacher(Map<String, Object> param);
	
	public List<Map<String, Object>> getAllTeachers();
}
