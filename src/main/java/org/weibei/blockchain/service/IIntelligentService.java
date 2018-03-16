/**
 * Project Name:weibei-cloud
 * File Name:IItelligentService.java
 * Package Name:org.weibei.blockchain.service
 * Date:2017年5月9日下午2:46:04
 * Copyright (c) 2017, hokuny@foxmail.com All Rights Reserved.
 *
*/

package org.weibei.blockchain.service;

import org.weibei.blockchain.model.Component;
import org.weibei.blockchain.model.ConModel;
import org.weibei.blockchain.util.Pagination;

import com.mongodb.DBCursor;

/**
 * ClassName:IItelligentService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2017年5月9日 下午2:46:04 <br/>
 * @author   hokuny@foxmail.com
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public interface IIntelligentService {
	
	public <T> Pagination queryComponents(String conf,int pageNumber,int pageSize);
	
	public Component queryComponentById(String id);
	
	public void saveComponent(Object obj);
	
	public int updateComponent(Component obj);
	
	public int deleteComponent(String id);
	
	public <T> Pagination queryModels(String conf,int pageNumber,int pageSize);
	
	public ConModel queryModelById(String id);
	
	public void saveModel(Object obj);	
}

