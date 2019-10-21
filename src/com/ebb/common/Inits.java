package com.ebb.common;

import com.ebb.model.dto.ToPage;

public class Inits {
	
	/**
	 * 初始化分页
	 * @param currentPage
	 * @param pagesize
	 * @return
	 */
	public static ToPage initPage(Integer currentPage, Integer pagesize) {
		if(currentPage == null){
			currentPage = Code.CURRENTPAGE;
		}
		if(pagesize == null){
			pagesize = Code.PAGESIZE; 
		}
		ToPage page = new ToPage();
		page.setCurrentPage(currentPage);
		page.setPagesize(pagesize);
		return page;
	}
}
