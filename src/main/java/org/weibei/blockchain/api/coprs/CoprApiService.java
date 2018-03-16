package org.weibei.blockchain.api.coprs;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.weibei.blockchain.api.dao.mongodb.MongoDBBaseDao;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
/**
 * @ClassName: CoprApiService
 * @Package org.weibei.blockchain.api.coprs
 * @Description:TODO ADD FUNCTION
 * @date: 2017年4月26日 上午11:05:52
 * @author hokuny@foxmail.com
 * @version 
 */
@Component("coprApiService")
public class CoprApiService {
	@Resource(name = "mongoDBBaseDao")
	MongoDBBaseDao mongoDBBaseDao;
	
	public void save(Object obj,String collectionName) throws Exception{
		mongoDBBaseDao.add(obj,collectionName);
	}
	
	public DBCursor queryByConfigInventory(String collectionName,String agentUserId,String tx_id,String inventoryId, String[] contract_hash,int pageNumber,int pageSize) throws Exception{
		return mongoDBBaseDao.queryByConfigInventory(collectionName, agentUserId, tx_id, inventoryId,contract_hash,pageNumber,pageSize);
	}
	
	public DBCursor queryByConfigContract(String collectionName,String agentUserId,String tx_id,String contractId, String[] contract_hash,int pageNumber,int pageSize) throws Exception{
		return mongoDBBaseDao.queryByConfigContract(collectionName, agentUserId, tx_id, contractId,contract_hash,pageNumber,pageSize);
	}
	
	public DBCursor queryForUid(String agentUserId,String collectionName){
		return mongoDBBaseDao.queryForUid(collectionName, agentUserId);
	}
	
	public DBCursor queryForLock(String collectionName){
		return mongoDBBaseDao.queryForLock(collectionName);
	}
	
	public void updateForLock(DBObject obj,String collectionName){
		mongoDBBaseDao.updateForLock(obj, collectionName);
	}
	
	public int queryBlockChainHigh(String tx){
		// 从区块链查询tx区块   如果有，获取高度，如果没有获取最后一个区块高度+1
		int height=1;
		DBCursor c=mongoDBBaseDao.queryBlockForTx("Blockchain", tx);
		if(c!=null && c.hasNext()){
			DBObject document = (DBObject) c.next();
			Map map = document.toMap();
			height =(int) map.get("height");
			return height;
		}else{
			DBCursor prev_block_Cur=mongoDBBaseDao.queryForPrevBlock("Blockchain");
			if(prev_block_Cur.hasNext()){
				DBObject document = (DBObject) prev_block_Cur.next();
				Map map = document.toMap();
				height =(int) map.get("height");
				return height+1;
			}
		}
		return height;
	}
	 
}
