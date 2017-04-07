package com.select.service;

import java.util.List;
import java.util.Map;

public interface SelectService {
	
	public void updateSelectRound(Map<String, Object> param);
	
	public List<Map<String, Object>> getSelectStudentsByTId(Map<String, Object> param);
	
	public List<Map<String, Object>> getSelectRoundBySId(Map<String, Object> param);
	
	public void insertSelectRound(Map<String, Object> param);
	
	public void insertSelectResult(Map<String, Object> param);
	
	public List<Map<String, Object>> getSelectResultByTId(Map<String, Object> param);
	
	public void updateSelectResult(Map<String, Object> param);
	
	public List<String> getAllSelectResult();
	
}
