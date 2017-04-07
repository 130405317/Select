package com.select.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.select.dao.SelectDao;
import com.select.service.SelectService;

@Service("selectService")
public class SelectServiceImpl implements SelectService {

	@Resource
	private SelectDao selectDao;

	@Override
	public void updateSelectRound(Map<String, Object> param) {
		selectDao.updateSelectRound(param);
	}

	@Override
	public List<Map<String, Object>> getSelectStudentsByTId(Map<String, Object> param) {
		return selectDao.getSelectStudentsByTId(param);
	}

	@Override
	public List<Map<String, Object>> getSelectRoundBySId(Map<String, Object> param) {
		return selectDao.getSelectRoundBySId(param);
	}

	@Override
	public void insertSelectRound(Map<String, Object> param) {
		selectDao.insertSelectRound(param);
	}

	@Override
	public void insertSelectResult(Map<String, Object> param) {
		selectDao.insertSelectResult(param);
	}

	@Override
	public List<Map<String, Object>> getSelectResultByTId(Map<String, Object> param) {
		return selectDao.getSelectResultByTId(param);
	}

	@Override
	public void updateSelectResult(Map<String, Object> param) {
		selectDao.updateSelectResult(param);
	}

	@Override
	public List<String> getAllSelectResult() {
		List<String> list_data = new ArrayList<String>();
		List<Map<String, Object>> list = selectDao.getAllSelectResult();
		for (Map<String, Object> map : list) {
			if (map.get("RESULT") != null) {
				String[] str = ((String) map.get("RESULT")).split(",");
				for (int i = 0; i < str.length; i++) {
					list_data.add(str[i]);
				}
			}

		}
		return list_data;
	}

}
