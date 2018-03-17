/**
 * Project Name:weibei-cloud
 * File Name:Component.java
 * Package Name:org.weibei.blockchain.model
 * Date:2017年5月15日下午3:21:36
 * Copyright (c) 2017, hokuny@foxmail.com All Rights Reserved.
 *
*/

package org.cros.blockchain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * ClassName:Component <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年5月15日 下午3:21:36 <br/>
 * 
 * @author hokuny@foxmail.com
 * @version
 * @since JDK 1.6
 * @see
 */
@Document(collection = "components")
public class Component {
    @Id
    private String id;
    private String key;
    private String description;
    private String comValue;
    private String status;

    public String getComValue() {
        return comValue;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getStatus() {
        return status;
    }

    public void setComValue(String comValue) {
        this.comValue = comValue;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
