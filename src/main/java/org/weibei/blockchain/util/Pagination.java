/**
 * Project Name:weibei-cloud
 * File Name:Pagination.java
 * Package Name:org.weibei.blockchain.util
 * Date:2017年5月15日下午2:20:32
 * Copyright (c) 2017, hokuny@foxmail.com All Rights Reserved.
 *
*/

package org.weibei.blockchain.util;

import java.util.List;

/**
 * ClassName:Pagination <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2017年5月15日 下午2:20:32 <br/>
 * @author   hokuny@foxmail.com
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class Pagination<T> {
	/** 每页显示条数 */  
    private Integer pageSize = 10;  
  
    /** 当前页 */  
    private Integer currentPage = 1;  
  
    /** 总页数 */  
    private Integer totalPage = 1;  
  
    /** 查询到的总数据量 */  
    private Integer totalNumber = 0;  
  
    /** 数据集 */  
    private List items;  
  
    public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(Integer totalNumber) {
		this.totalNumber = totalNumber;
	}

	public List getItems() {
		return items;
	}

	public void setItems(List items) {
		this.items = items;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageSize() {  
        return pageSize;  
    }  
	
	
    
    public Pagination(Integer currentPage, Integer totalPage,
			Integer totalNumber) {
		super();
		this.currentPage = currentPage;
		this.totalPage = totalPage;
		this.totalNumber = totalNumber;
	}

	/** 
     * 处理查询后的结果数据 
     *  
     * @param items 
     *            查询结果集 
     * @param count 
     *            总数 
     */  
    public void build(List items) {  
        this.setItems(items);  
        int count =  this.getTotalNumber();  
        int divisor = count / this.getPageSize();  
        int remainder = count % this.getPageSize();  
        this.setTotalPage(remainder == 0 ? divisor == 0 ? 1 : divisor : divisor + 1);  
    } 
}

