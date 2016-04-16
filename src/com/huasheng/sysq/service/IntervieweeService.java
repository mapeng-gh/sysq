package com.huasheng.sysq.service;

import java.util.List;

import com.huasheng.sysq.db.InterviewBasicDB;
import com.huasheng.sysq.model.InterviewBasic;
import com.huasheng.sysq.model.Page;

public class IntervieweeService {

	/**
	 * 搜索访谈记录
	 * @param searchStr
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public static Page<InterviewBasic> searchInterviewBasic(String searchStr,Integer pageNo,Integer pageSize){
		
		//构造搜索对象
		InterviewBasic interview = new InterviewBasic();
		interview.setUsername(searchStr);
		interview.setDna(searchStr);
		
		//分页计算
		Integer offset = null;
		Integer limit = pageSize;
		offset = (pageNo - 1) * pageSize;
		
		//数据查询
		List<InterviewBasic> data = InterviewBasicDB.search(interview, "or", offset, limit);
		
		//构造page
		Page<InterviewBasic> page = new Page<InterviewBasic>();
		page.setData(data);
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		
		//计算总页数
		int size = InterviewBasicDB.size(interview, "or");
		int totalPages = size % pageSize == 0 ? size / pageSize : size / pageSize + 1;
		page.setTotalPages(totalPages);
		
		return page;
	}
	
	/**
	 * 查找受访者基本信息
	 * @param id
	 * @return
	 */
	public static InterviewBasic findById(int id){
		return InterviewBasicDB.selectById(id);
	}
	
	/**
	 * 修改受访者基本信息
	 * @param interviewBasic
	 */
	public static void modifyInterviewBasic(InterviewBasic interviewBasic){
		InterviewBasicDB.update(interviewBasic);
	}
}
