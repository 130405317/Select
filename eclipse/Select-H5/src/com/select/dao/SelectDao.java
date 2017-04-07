package com.select.dao;

import java.util.List;
import java.util.Map;

public interface SelectDao {
	
	/**
	 * 更新选择
	 * <p>Title:</p>
	 * @author: 徐德荣
	 * @date: 2017年3月24日
	 *
	 */
	public void updateSelectRound(Map<String, Object> param);
	
	public List<Map<String, Object>> getSelectStudentsByTId(Map<String, Object> param);

	public void deleteSelectResult(Map<String, Object> param);
	
	public List<Map<String, Object>> getSelectRoundBySId(Map<String, Object> param);
	
	public void insertSelectRound(Map<String, Object> param);
	
	public void insertSelectResult(Map<String, Object> param);
	
	public List<Map<String, Object>> getSelectResultByTId(Map<String, Object> param);
	
	public void updateSelectResult(Map<String, Object> param);
	
	public List<Map<String, Object>> getAllSelectResult();
}
