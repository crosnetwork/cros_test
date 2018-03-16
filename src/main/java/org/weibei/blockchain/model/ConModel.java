/**
 * Project Name:weibei-cloud
 * File Name:ConModel.java
 * Package Name:org.weibei.blockchain.model
 * Date:2017年5月16日上午10:17:14
 * Copyright (c) 2017, hokuny@foxmail.com All Rights Reserved.
 *
*/

package org.weibei.blockchain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * ClassName:ConModel <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2017年5月16日 上午10:17:14 <br/>
 * @author   hokuny@foxmail.com
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
@Document(collection="contract_model")
public class ConModel {
	 @Id
	  private String id;
	  private String key;
	  private String description;
	  private String comValue;
	  private String indexNum;
	  
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getComValue() {
		return comValue;
	}
	public void setComValue(String comValue) {
		this.comValue = comValue;
	}
	public String getIndexNum() {
		return indexNum;
	}
	public void setIndexNum(String indexNum) {
		this.indexNum = indexNum;
	}	
	
}

