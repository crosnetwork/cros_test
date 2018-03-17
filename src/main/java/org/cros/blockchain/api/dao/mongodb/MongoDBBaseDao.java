package org.cros.blockchain.api.dao.mongodb;

import java.util.List;

import org.cros.blockchain.api.dao.BaseDao;
import org.cros.blockchain.model.Component;
import org.cros.blockchain.model.ConModel;
import org.cros.blockchain.util.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 * @ClassName: MongoDBBaseDao
 * @Package org.weibei.blockchain.api.dao.mongodb
 * @Description:mongodb数据泛型dao类
 * @date: 2016年11月19日 上午11:15:14
 * @author hokuny@foxmail.com
 * @version
 */
/**
 * @ClassName: MongoDBBaseDao
 * @Package org.weibei.blockchain.api.dao.mongodb
 * @Description:TODO ADD FUNCTION
 * @date: 2017年4月26日 上午11:07:17
 * @author hokuny@foxmail.com
 * @version
 */
@Repository(value = "mongoDBBaseDao")
public class MongoDBBaseDao implements BaseDao {
    protected static final int PAGE_SIZE = 10;

    @Autowired
    @Qualifier("mongoTemplate")
    protected MongoTemplate mongoTemplate;

    /**
     * 添加对象
     *
     * @param obj
     *            要添加的Mongo对象
     */
    @Override
    public void add(Object obj, String collectionName) {
        if (collectionName != null) {
            mongoTemplate.insert(obj, collectionName);
        } else {
            mongoTemplate.insert(obj);
        }

    }

    @Override
    public int deleteCompById(String collection, String id) {
        Criteria criteria = Criteria.where("_id").is(id);
        Query query = new Query(criteria);
        return mongoTemplate.remove(query, collection).getN();
    }

    /**
     * 根据类获取全部的对象列表
     *
     * @param entityClass
     *            返回类型
     * @return List<T> 返回对象列表
     */
    @Override
    public <T> List<T> findAll(Class<T> entityClass) {
        return mongoTemplate.findAll(entityClass);
    }

    /**
     * 根据主键id返回对象
     *
     * @param id
     *            唯一标识
     * @return T 对象
     */
    @Override
    public <T> T findById(Class<T> entityClass, String id) {
        return mongoTemplate.findById(id, entityClass);
    }

    /**
     * 通过条件查询,查询分页结果
     *
     * @param <T>
     */
    public Pagination getPage(int currentPage, Query query, Object entity) {
        // 获取总条数
        long totalCount = mongoTemplate.count(query, entity.getClass());
        // 总页数
        int totalPage = (int) (totalCount / PAGE_SIZE);

        int skip = (currentPage - 1) * PAGE_SIZE;

        Pagination page = new Pagination(currentPage, totalPage, (int) totalCount);

        query.skip(skip);// skip相当于从那条记录开始

        query.limit(PAGE_SIZE);// 从skip开始,取多少条记录

        List datas = mongoTemplate.find(query, entity.getClass());

        page.build(datas); // 获取数据

        return page;
    }

    public int queryBlockchainHigh(String tx) {
        return 0;

    }

    @Override
    public DBCursor queryBlockForTx(String collection, String tx) {
        DBCursor dbCursor = mongoTemplate.getCollection(collection)
                .find(new BasicDBObject("tx.tx_id", tx));
        // 排序
        return dbCursor;
    }

    @Override
    public DBCursor queryByConfigContract(String collection, String agentUserId, String tx_id,
            String contractId, String[] product_hash, int pageNumber, int pageSize) {

        BasicDBObject basicDBObject = new BasicDBObject();
        DBObject orderBy = new BasicDBObject();
        orderBy.put("tx_time", -1);
        if (agentUserId != null) {
            basicDBObject.put("out.data.agent_user_id", agentUserId);
        }
        if (tx_id != null) {
            basicDBObject.put("tx_id", tx_id);
        }
        if (contractId != null) {
            basicDBObject.put("out.data.sys_contract_no", contractId);
        }
        if (product_hash != null) {
            basicDBObject.put("out.data.contract_hash", new BasicDBObject("$all", product_hash));
        }
        if (basicDBObject.size() > 0) {
            return mongoTemplate.getCollection(collection).find(basicDBObject).sort(orderBy)
                    .skip((pageNumber - 1) * pageSize).limit(pageSize);
        } else {
            return null;
        }
    }

    @Override
    public DBCursor queryByConfigInventory(String collection, String agentUserId, String tx_id,
            String inventoryId, String[] product_hash, int pageNumber, int pageSize) {
        BasicDBObject basicDBObject = new BasicDBObject();
        DBObject orderBy = new BasicDBObject();
        orderBy.put("tx_time", -1);
        if (agentUserId != null) {
            basicDBObject.put("out.data.agent_user_id", agentUserId);
        }
        if (tx_id != null) {
            basicDBObject.put("tx_id", tx_id);
        }
        if (inventoryId != null) {
            basicDBObject.put("out.data.inventory_id", inventoryId);
        }
        if (product_hash != null) {
            basicDBObject.put("out.data.inventory_hash", new BasicDBObject("$all", product_hash));
        }
        if (basicDBObject.size() > 0) {
            return mongoTemplate.getCollection(collection).find(basicDBObject).sort(orderBy)
                    .skip((pageNumber - 1) * pageSize).limit(pageSize);
        } else {
            return null;
        }
    }

    public DBCursor queryById(String collection, String agent_user_id) {
        return mongoTemplate.getCollection(collection)
                .find(new BasicDBObject("out.data.agent_user_id", agent_user_id));
    }

    public Component queryCompByConfig(String id) {
        return mongoTemplate.findById(id, Component.class);
    }

    /**
     * ===============================组件数据库操作==================================
     * =============
     */
    public DBCursor queryCompByConfig(String name, int pageNumber, int pageSize) {
        BasicDBObject basicDBObject = new BasicDBObject();
        DBObject orderBy = new BasicDBObject();
        orderBy.put("_id", -1);
        if (name != null) {
            basicDBObject.put("description", name);
        }
        return mongoTemplate.getCollection("components").find(basicDBObject).sort(orderBy)
                .skip((pageNumber - 1) * pageSize).limit(pageSize);
    }

    public ConModel queryContractByConfig(String id) {
        return mongoTemplate.findById(id, ConModel.class);
    }

    @Override
    public DBCursor queryForLock(String collection) {

        // TODO Auto-generated method stub
        return mongoTemplate.getCollection(collection).find();
    }

    @Override
    public DBCursor queryForPrevBlock(String collection) {
        DBCursor dbCursor = mongoTemplate.getCollection(collection).find();
        // 排序
        DBObject sortDBObject = new BasicDBObject();
        sortDBObject.put("height", -1);
        return dbCursor.sort(sortDBObject).limit(1);
    }

    @Override
    public DBCursor queryForTxHash(String collection) {
        return mongoTemplate.getCollection(collection).find(new BasicDBObject("status", "pending"));
    }

    @Override
    public DBCursor queryForUid(String collection, String agentUserId) {
        // TODO Auto-generated method stub
        return mongoTemplate.getCollection(collection)
                .find(new BasicDBObject("agentUserId", agentUserId));
    }

    /**
     * 删除一个对象
     *
     * @param obj
     *            要删除的Mongo对象
     */
    @Override
    public void remove(Object obj) {
        mongoTemplate.remove(obj);
    }

    public void save(String collection, DBObject doc) {
        mongoTemplate.getCollection(collection).insert(doc);
    }

    @Override
    public int updateCompById(String collection, Component c) {
        Criteria criteria = Criteria.where("_id").is(c.getId());
        Query query = new Query(criteria);
        Update update = new Update();
        update.set("id", c.getId());
        update.set("key", c.getKey());
        update.set("description", c.getDescription());
        update.set("comValue", c.getComValue());
        update.set("status", c.getStatus());
        return mongoTemplate.updateFirst(query, update, collection).getN();
    }

    /**
     * 修改对象
     *
     * @param obj
     *            要修改的Mongo对象
     */
    @Override
    public void updateForLock(DBObject obj, String collectionName) {
        mongoTemplate.getCollection(collectionName).update(new BasicDBObject("_id", "1"), obj);
    }

    @Override
    public void updateTxStatusConfirmed(String collection, BasicDBList ids) {
        Criteria criteria = Criteria.where("_id").in(ids);
        Query query = new Query(criteria);
        Update update = Update.update("status", "confirmed");
        mongoTemplate.updateMulti(query, update, collection);
    }

    @Override
    public void updateTxStatusInvalid(String collection, BasicDBList ids) {
        Criteria criteria = Criteria.where("_id").in(ids);
        Query query = new Query(criteria);
        Update update = Update.update("status", "invalid");
        mongoTemplate.updateMulti(query, update, collection);
        // TODO Auto-generated method stub

    }

}
