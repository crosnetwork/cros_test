/**
 * Project Name:weibei-cloud
 * File Name:IItelligentService.java
 * Package Name:org.weibei.blockchain.service
 * Date:2017年5月9日下午2:46:04
 * Copyright (c) 2017, hokuny@foxmail.com All Rights Reserved.
 *
*/

package org.cros.blockchain.service;

import org.cros.blockchain.model.Component;
import org.cros.blockchain.model.ConModel;
import org.cros.blockchain.util.Pagination;

/**
 * ClassName:IItelligentService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年5月9日 下午2:46:04 <br/>
 *
 * @author hokuny@foxmail.com
 * @version
 * @since JDK 1.6
 * @see
 */
public interface IIntelligentService {

    public int deleteComponent(String id);

    public Component queryComponentById(String id);

    public <T> Pagination queryComponents(String conf, int pageNumber, int pageSize);

    public ConModel queryModelById(String id);

    public <T> Pagination queryModels(String conf, int pageNumber, int pageSize);

    public void saveComponent(Object obj);

    public void saveModel(Object obj);

    public int updateComponent(Component obj);
}
