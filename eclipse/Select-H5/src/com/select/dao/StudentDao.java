package com.select.dao;

import java.util.List;
import java.util.Map;

public interface StudentDao {
  
	public List<Map<String,Object>> checkStudent(Map<String, Object> param);

	public List<Map<String,Object>> getStudentsByClass(Map<String, Object> param);
	
	public Map<String,Object> getStudentById(Map<String, Object> param);
	
	public void  updateStudent(Map<String, Object> param);
	
	public List<Map<String, Object>> getAllStudents();
	
}