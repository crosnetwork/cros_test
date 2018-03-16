package org.weibei.blockchain.api.dao;

import java.util.List;

import org.weibei.blockchain.model.Component;

import com.mongodb.BasicDBList;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

public interface BaseDao {

	<T> T findById(Class<T> entityClass, String id);

	<T> List<T> findAll(Class<T> entityClass);

	void remove(Object obj);

	void add(Object obj,String collectionName);

	void updateForLock(DBObject obj,String collectionName);
	
	DBCursor queryByConfigContract(String collection,String agentUserId,String tx_id,String ContractId,String[] product_hash,int pageNumber,int pageSize);
	
	DBCursor queryByConfigInventory(String collection,String agentUserId,String tx_id,String inventoryId,String[] product_hash,int pageNumber,int pageSize);
	
	DBCursor queryForPrevBlock(String collection);
	
	DBCursor queryBlockForTx(String collection,String tx);
	
	DBCursor queryForTxHash(String collection);
	
	DBCursor queryForUid(String collection,String agentUserId);
	
	DBCursor queryForLock(String collection);
	
	void updateTxStatusConfirmed(String collection,BasicDBList Id);
	
	void updateTxStatusInvalid(String collection,BasicDBList Id);

	int updateCompById(String collection, Component id);

	int deleteCompById(String collection, String id);
		
}
