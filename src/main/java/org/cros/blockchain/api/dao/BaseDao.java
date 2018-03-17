package org.cros.blockchain.api.dao;

import java.util.List;

import org.cros.blockchain.model.Component;

import com.mongodb.BasicDBList;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public interface BaseDao {

    void add(Object obj, String collectionName);

    int deleteCompById(String collection, String id);

    <T> List<T> findAll(Class<T> entityClass);

    <T> T findById(Class<T> entityClass, String id);

    DBCursor queryBlockForTx(String collection, String tx);

    DBCursor queryByConfigContract(String collection, String agentUserId, String tx_id,
            String ContractId, String[] product_hash, int pageNumber, int pageSize);

    DBCursor queryByConfigInventory(String collection, String agentUserId, String tx_id,
            String inventoryId, String[] product_hash, int pageNumber, int pageSize);

    DBCursor queryForLock(String collection);

    DBCursor queryForPrevBlock(String collection);

    DBCursor queryForTxHash(String collection);

    DBCursor queryForUid(String collection, String agentUserId);

    void remove(Object obj);

    int updateCompById(String collection, Component id);

    void updateForLock(DBObject obj, String collectionName);

    void updateTxStatusConfirmed(String collection, BasicDBList Id);

    void updateTxStatusInvalid(String collection, BasicDBList Id);

}
