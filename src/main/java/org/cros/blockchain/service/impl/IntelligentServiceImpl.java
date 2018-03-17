/**
 * Project Name:weibei-cloud
 * File Name:ItelligentServiceImpl.java
 * Package Name:org.weibei.blockchain.service.impl
 * Date:2017年5月9日下午3:12:35
 * Copyright (c) 2017, hokuny@foxmail.com All Rights Reserved.
 *
*/

package org.cros.blockchain.service.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cros.blockchain.api.dao.mongodb.MongoDBBaseDao;
import org.cros.blockchain.model.Component;
import org.cros.blockchain.model.ConModel;
import org.cros.blockchain.service.IIntelligentService;
import org.cros.blockchain.util.Pagination;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.ctc.wstx.dtd.ConcatModel;

/**
 * ClassName:ItelligentServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年5月9日 下午3:12:35 <br/>
 *
 * @author hokuny@foxmail.com
 * @version
 * @since JDK 1.6
 * @see
 */
@Service("intelligentService")
public class IntelligentServiceImpl implements IIntelligentService {
    @Resource(name = "mongoDBBaseDao")
    MongoDBBaseDao mongoDBBaseDao;
    private Log logger = LogFactory.getLog(WeibeiBlockChainServiceImpl.class);

    @Override
    public int deleteComponent(String id) {
        int index = mongoDBBaseDao.deleteCompById("components", id);
        return index;
    }

    @Override
    public Component queryComponentById(String id) {
        return mongoDBBaseDao.queryCompByConfig(id);
    }

    @Override
    public <T> Pagination queryComponents(String conf, int pageNumber, int pageSize) {
        Query query = new Query();
        if (conf != null && !conf.trim().isEmpty()) {
            Criteria criteria = Criteria.where("description").regex(conf);
            query.addCriteria(criteria);
        }
        return mongoDBBaseDao.getPage(pageNumber, query, new Component());
        // TODO Auto-generated method stub

    }

    @Override
    public ConModel queryModelById(String id) {
        return mongoDBBaseDao.queryContractByConfig(id);
    }

    @Override
    public <T> Pagination queryModels(String conf, int pageNumber, int pageSize) {
        Query query = new Query();
        if (conf != null && !conf.trim().isEmpty()) {
            Criteria criteria = Criteria.where("description").regex(conf);
            query.addCriteria(criteria);
        }
        return mongoDBBaseDao.getPage(pageNumber, query, ConcatModel.class);

    }

    @Override
    public void saveComponent(Object obj) {
        mongoDBBaseDao.add(obj, "components");
        // TODO Auto-generated method stub

    }

    @Override
    public void saveModel(Object obj) {
        mongoDBBaseDao.add(obj, "contract_model");
    }

    @Override
    public int updateComponent(Component obj) {
        // TODO Auto-generated method stub
        int index = mongoDBBaseDao.updateCompById("components", obj);
        return index;
    }

}
