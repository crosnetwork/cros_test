package org.weibei.blockchain.api.impl;

import static java.util.Arrays.asList;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.bitcoinj.params.MainNetParams;
import org.bson.Document;
import org.weibei.blockchain.api.IWeibeiMsgApi;
import org.weibei.blockchain.api.coprs.CoprApiService;
import org.weibei.blockchain.util.EncryptionUtil;
import org.weibei.blockchain.util.TripleDES;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 * @ClassName: WeibeiMsgApiImpl
 * @Package org.weibei.blockchain.api.impl
 * @Description:TODO ADD FUNCTION
 * @date: 2016年12月7日 上午12:31:10
 * @author hokuny@foxmail.com
 * @version
 */
public class WeibeiMsgApiImpl implements IWeibeiMsgApi {

	private Log logger = LogFactory.getLog(WeibeiMsgApiImpl.class);

	@Resource(name = "coprApiService")
	private CoprApiService coprApiService;

	@POST
	@Path("/newContract")
	public String contractRegisterData(String params) {
		System.out.println("newContract");
		logger.info("合同信息登记开始 开始");
		Map<String, Object> result = new HashMap<String, Object>();
		// TODO Auto-generated method stub
		if (StringUtils.isBlank(params)) {
			result.put("status", 401);
			result.put("err_msg", "参数为空");
			return JSON.toJSONString(result);
		}
		try {
			// 解码
			byte[] asBytes = Base64.decodeBase64(params);
			String str = new String(asBytes, "utf-8");
			JSONObject json = (JSONObject) JSONObject.fromObject(str);

			/* 校验厂家是否正确 */
			String agentId = json.getString("agent_id");
			if (!StringUtils.equals("beijiaosuo", agentId)) {
				result.put("status", 402);
				result.put("err_msg", "厂家不正确");
				return JSON.toJSONString(result);
			}
			/* 校验加密是否正确 */
			String sign = json.getString("sign");
			json.remove("sign");
			String sign1 = EncryptionUtil.encrytion(json.toString());
			if (!StringUtils.equals(sign1, sign)) {
				logger.info(json.toString());
				result.put("status", 403);
				result.put("err_msg", "sign验证错误");
				return JSON.toJSONString(result);
			}
			
			if(json.containsKey("loan_type") && json.containsKey("sys_contract_no") && json.containsKey("signing_date") && json.containsKey("loan_limit") && json.containsKey("contract_hash")){
				
			}else{
				result.put("status", 401);
				result.put("err_msg", "必填参数缺失");
				return JSON.toJSONString(result);
			}
			// 解析参数
			
			String agentUserId = json.has("agent_user_id") == true ? json
					.getString("agent_user_id") : "";// 用户唯一ID


			String userPublicKey = "";
			String userPrivateKey = "";
			String userWalletAddress = "";
			DBCursor userInfo = coprApiService.queryForUid(agentUserId,
					"Wallet");
			try {
				if (userInfo.hasNext()) {
					logger.info(" : old user========================================");
					DBObject userObj = userInfo.next();
					userPublicKey = userObj.get("userPublicKey").toString();
					userPrivateKey = userObj.get("userPrivateKey").toString();
					userWalletAddress = userObj.get("userWalletAddress")
							.toString();
				} else {
					logger.info(" : new user========================================");
					ECKey eckey = new ECKey();
					userPublicKey = eckey.getPublicKeyAsHex();
					userPrivateKey = eckey.getPrivateKeyAsHex();
					userWalletAddress = eckey.toAddress(MainNetParams.get())
							.toBase58();
					DBObject obj = new BasicDBObject();
					obj.put("userPublicKey", TripleDES.encrypt(userPublicKey.getBytes()).toString());
					obj.put("userPrivateKey",TripleDES.encrypt(userPrivateKey.getBytes()).toString());
					obj.put("userWalletAddress", userWalletAddress);
					obj.put("agentUserId", agentUserId);
					coprApiService.save(obj, "Wallet");
				}
			} finally {
				userInfo.close();
			}
			logger.info("Public key : " + TripleDES.encrypt(userPublicKey.getBytes()));
			logger.info("Private key : " + TripleDES.encrypt(userPrivateKey.getBytes()));
			logger.info("Weibei wallet address : " + userWalletAddress);
			logger.info("\n\n");

			// Weibei system keys (TODO: get value from Wallet collection)
			String systemPublicKey = "0275ef688470a1ed81c5b6dc6c9410c8e48f3605eeb06a170419a99bdb0ed6549d";
			String systemPrivateKey = "e9016ee5acc7952af2a99cd8971bd0197499270c626230319917a98657679ef0";
			ECKey systemECKey = ECKey.fromPrivateAndPrecalculatedPublic(
					Utils.HEX.decode(systemPrivateKey),
					Utils.HEX.decode(systemPublicKey));
			String systemWalletAddress = systemECKey.toAddress(
					MainNetParams.get()).toBase58();
			logger.info("System wallet address : " + systemWalletAddress); // Should
																			// return
																			// '1KctVnjncFK8QsoJHjx6q73VXVxaqc76Q5'
			Calendar cal = Calendar.getInstance();
			String contractHash = json.has("contract_hash") == true ? json
					.getString("contract_hash") : ""; // 抵押物hash(md5)
			String[] contractHashs = contractHash.split(",");
			// create a document for data

			Document docData = new Document("agent_id", agentId).append("inventory_hash", asList(contractHashs));
			json.remove("agent_id");
			json.remove("inventory_hash");
			Iterator iterator = json.keys();
			while(iterator.hasNext()){
				String key= (String) iterator.next();
				docData.append(key, json.getString(key));
			}
			// Create a document
			Document doc = new Document("ver", 1)
					.append("vin_size", 1)
					.append("vout_size", 1)
					.append("tx_time", cal.getTimeInMillis())
					.append("in",
							asList(new Document().append("prev_out",
									"00000000000000000000000000000000").append(
									"prev_n", -1)))
					.append("out",
							asList(new Document()
									.append("value", 1)
									.append("scriptPubKey",
											"OP_DUP OP_HASH160 "
													+ userWalletAddress
													+ " OP_EQUALVERIFY OP_CHECKSIG")
									.append("data",
											docData)));
			logger.info("Raw document - " + doc.toJson());
//			doc.append("status", "pending");
			// Hash raw document twice, then sign the hash to get signature.
			String docHash = Utils.HEX.encode(Sha256Hash.hashTwice(doc.toJson()
					.getBytes()));
			String signature2 = systemECKey.signMessage(docHash);
			logger.info("Signature - " + signature2);
			doc.append("scriptSig", signature2 + " " + systemPublicKey);
			logger.info("Document with signature- " + doc.toJson());
			// Generate hash256 hex and add to document
			String docHash2 = Utils.HEX.encode(Sha256Hash.hashTwice(doc
					.toJson().getBytes()));
			doc.append("tx_id", docHash2);
			logger.info("Document with hash - " + doc.toJson());
			if (verifyTx(doc)) {
				doc.append("status", "pending");
				doc.append("tx_id", docHash2);
				doc.append("scriptSig", signature2 + " " + systemPublicKey);
				coprApiService.save(doc, "TxPool");
				result.put("status", 200);
				result.put("tx_id", docHash2);
				result.put("wallet_id", userWalletAddress);
				result.put("tx_time", doc.get("tx_time"));
				result.put("height", coprApiService.queryBlockChainHigh(docHash2));
			} else {
				result.put("status", 405);
				result.put("err_msg", "系统故障");
			}
		} catch (Exception ex) {
			String msg = "合同信息发生异常:" + ex.getMessage();
			logger.error(msg, ex);
			result.put("status", 405);
			result.put("err_msg", "系统故障:"+msg);
		}
		logger.info("合同信息结束");
		return JSON.toJSONString(result);

	}

	@POST
	@Path("/getContract")
	public String contractQueryData(String params) {
		// TODO Auto-generated method stub
		System.out.println("getContract");
		logger.info("合同信息 查询");
		Map<String, Object> result = new HashMap<String, Object>();
		// TODO Auto-generated method stub
		if (StringUtils.isBlank(params)) {
			result.put("status", 401);
			result.put("err_msg", "参数为空");
			return JSON.toJSONString(result);
		}
		try {
			// 解码
			byte[] asBytes = Base64.decodeBase64(params);
			String str = new String(asBytes, "utf-8");
			logger.info("=====" + str); // 输出为: some string
			JSONObject json = (JSONObject) JSONObject.fromObject(str);
			/* 校验厂家是否正确 */
			String agentId = json.getString("agent_id");
			if (!StringUtils.equals("beijiaosuo", agentId)) {
				result.put("status", 402);
				result.put("err_msg", "厂家不正确");
				return JSON.toJSONString(result);
			}
			/* 校验加密是否正确 */
			String sign = json.getString("sign");
			json.remove("sign");
			String sign1 = EncryptionUtil.encrytion(json.toString());
			if (!StringUtils.equals(sign1, sign)) {
				result.put("status", 403);
				result.put("err_msg", "sign验证错误");
				return JSON.toJSONString(result);
			}
			String agentUserId = json.has("agent_user_id") == true ? json
					.getString("agent_user_id") : null;// 用户唯一ID
			String contractHash = json.has("contract_hash") == true ? json
					.getString("contract_hash") : null; // 合同hash(md5)
			String[] contractHashs;
			if (contractHash != null) {
				contractHashs = contractHash.split(",");
			} else {
				contractHashs = null;
			}
			String txId = json.has("tx_id") == true ? json.getString("tx_id")
					: null; // 区块交易记录号
			String contractId = json.has("sys_contract_no") == true ? json.getString("sys_contract_no")
					: null; // 抵押物唯一标示
			int pageNumber = json.has("pageNumber") == true ? json
					.getInt("pageNumber") : 1; //
			int pageSize = json.has("pageSize") == true ? json
					.getInt("pageSize") : 10; // 

			DBCursor dbs = coprApiService.queryByConfigContract("TxPool", agentUserId,
					 txId,contractId, contractHashs, pageNumber, pageSize);
			try {
				JSONArray jsonArray = new JSONArray();
				if (dbs != null && dbs.size() > 0) {
					result.put("status", 200);
					while (dbs.hasNext()) {
						DBObject document = dbs.next();
						Map map = document.toMap();
						JSONArray jArray = JSONArray.parseArray(map.get("out")
								.toString());
						System.out.println(jArray.getJSONObject(0).get("data"));
						String datajson = jArray.getJSONObject(0).get("data")
								.toString();
						JSONObject jsonObject = JSONObject.fromObject(datajson);
						jsonObject.put("tx_id", document.get("tx_id"));
						jsonObject.put("tx_time", document.get("tx_time"));
						int high =  coprApiService.queryBlockChainHigh(document.get("tx_id").toString());
						jsonObject.put("height", high);
						jsonArray.add(jsonObject);
					}
					result.put("data", jsonArray);
				} else {
					result.put("status", 200);
					result.put("data", jsonArray);
				}
			} finally {
				dbs.close();
			}
		} catch (Exception ex) {
			String msg = "合同信息发生异常:" + ex.getMessage();
			logger.error(msg, ex);
			result.put("status", 405);
			result.put("err_msg", "系统故障");
		}
		logger.info("合同信息结束");
		return JSON.toJSONString(result);
	}

	private boolean verifyTx(Document document) {
		String txHash = document.getString("tx_id");
		String scriptSig = document.getString("scriptSig");
		String[] tokens = scriptSig.split(" ");
		String signature = tokens[0];
		String publicKey = tokens[1];
		document.remove("_id");
		document.remove("tx_id");
		logger.info("Document minus hash and id - " + document.toJson());
		String docHash = Utils.HEX.encode(Sha256Hash.hashTwice(document
				.toJson().getBytes()));
		logger.info("Is hash the same = " + docHash.equals(txHash));
		document.remove("scriptSig");
		logger.info("Raw document - " + document.toJson());
		try {
			String docHash2 = Utils.HEX.encode(Sha256Hash.hashTwice(document
					.toJson().getBytes()));
			ECKey eckey3 = ECKey.fromPublicOnly(Utils.HEX.decode(publicKey));
			eckey3.verifyMessage(docHash2, signature);
			logger.info("Signature verified.");
			return true;
		} catch (java.security.SignatureException sigExe) {
			logger.info("Signature verification failed.");
			return false;
		}
	}
	
	@POST
	@Path("/newInventory")
	public String inventoryRegisterData(String params) {
		System.out.println("newContract");
		logger.info("抵押物信息登记开始 开始");
		Map<String, Object> result = new HashMap<String, Object>();
		// TODO Auto-generated method stub
		if (StringUtils.isBlank(params)) {
			result.put("status", 401);
			result.put("err_msg", "参数为空");
			return JSON.toJSONString(result);
		}
		try {
			// 解码
			byte[] asBytes = Base64.decodeBase64(params);
			String str = new String(asBytes, "utf-8");
			JSONObject json = (JSONObject) JSONObject.fromObject(str);

			/* 校验厂家是否正确 */
			String agentId = json.getString("agent_id");
			if (!StringUtils.equals("beijiaosuo", agentId)) {
				result.put("status", 402);
				result.put("err_msg", "厂家不正确");
				return JSON.toJSONString(result);
			}
			/* 校验加密是否正确 */
			String sign = json.getString("sign");
			json.remove("sign");
			String sign1 = EncryptionUtil.encrytion(json.toString());
			if (!StringUtils.equals(sign1, sign)) {
				logger.info(json.toString());
				result.put("status", 403);
				result.put("err_msg", "sign验证错误");
				return JSON.toJSONString(result);
			}
			if(json.containsKey("agent_id") && json.containsKey("agent_user_id") && json.containsKey("inventory_id") && json.containsKey("inventory_hash")){
				
			}else{
				result.put("status", 401);
				result.put("err_msg", "必填参数缺失");
				return JSON.toJSONString(result);
			}
			// 解析参数
			String agentUserId = json.has("agent_user_id") == true ? json
					.getString("agent_user_id") : "";// 用户唯一ID

			String userPublicKey = "";
			String userPrivateKey = "";
			String userWalletAddress = "";
			DBCursor userInfo = coprApiService.queryForUid(agentUserId,
					"Wallet");
			try {
				if (userInfo.hasNext()) {
					logger.info(" : old user========================================");
					DBObject userObj = userInfo.next();
					userPublicKey = userObj.get("userPublicKey").toString();
					userPrivateKey = userObj.get("userPrivateKey").toString();
					userWalletAddress = userObj.get("userWalletAddress")
							.toString();
				} else {
					logger.info(" : new user========================================");
					ECKey eckey = new ECKey();
					userPublicKey = eckey.getPublicKeyAsHex();
					userPrivateKey = eckey.getPrivateKeyAsHex();
					userWalletAddress = eckey.toAddress(MainNetParams.get())
							.toBase58();
					DBObject obj = new BasicDBObject();
					obj.put("userPublicKey", TripleDES.encrypt(userPublicKey.getBytes()).toString());
					obj.put("userPrivateKey",TripleDES.encrypt(userPrivateKey.getBytes()).toString());
					obj.put("userWalletAddress", userWalletAddress);
					obj.put("agentUserId", agentUserId);
					coprApiService.save(obj, "Wallet");
				}
			} finally {
				userInfo.close();
			}
			logger.info("Public key : " + TripleDES.encrypt(userPublicKey.getBytes()));
			logger.info("Private key : " + TripleDES.encrypt(userPrivateKey.getBytes()));
			logger.info("Weibei wallet address : " + userWalletAddress);
			logger.info("\n\n");

			// Weibei system keys (TODO: get value from Wallet collection)
			String systemPublicKey = "0275ef688470a1ed81c5b6dc6c9410c8e48f3605eeb06a170419a99bdb0ed6549d";
			String systemPrivateKey = "e9016ee5acc7952af2a99cd8971bd0197499270c626230319917a98657679ef0";
			ECKey systemECKey = ECKey.fromPrivateAndPrecalculatedPublic(
					Utils.HEX.decode(systemPrivateKey),
					Utils.HEX.decode(systemPublicKey));
			String systemWalletAddress = systemECKey.toAddress(
					MainNetParams.get()).toBase58();
			logger.info("System wallet address : " + systemWalletAddress); // Should
																			// return
																			// '1KctVnjncFK8QsoJHjx6q73VXVxaqc76Q5'
			Calendar cal = Calendar.getInstance();
			String inventoryHash = json.has("inventory_hash") == true ? json
					.getString("inventory_hash") : ""; // 抵押物hash(md5)
			String[] inventoryHashs = inventoryHash.split(",");
			// create a document for data

			Document docData = new Document("agent_id", agentId).append("inventory_hash", asList(inventoryHashs));
			json.remove("agent_id");
			json.remove("inventory_hash");
			Iterator iterator = json.keys();
			while(iterator.hasNext()){
				String key= (String) iterator.next();
				docData.append(key, json.getString(key));
			}
			
			// Create a document
			Document doc = new Document("ver", 1)
					.append("vin_size", 1)
					.append("vout_size", 1)
					.append("tx_time", cal.getTimeInMillis())
					.append("in",
							asList(new Document().append("prev_out",
									"00000000000000000000000000000000").append(
									"prev_n", -1)))
					.append("out",
							asList(new Document()
									.append("value", 1)
									.append("scriptPubKey",
											"OP_DUP OP_HASH160 "
													+ userWalletAddress
													+ " OP_EQUALVERIFY OP_CHECKSIG")
									.append("data",docData)));
			logger.info("Raw document - " + doc.toJson());
//			doc.append("status", "pending");
			// Hash raw document twice, then sign the hash to get signature.
			String docHash = Utils.HEX.encode(Sha256Hash.hashTwice(doc.toJson()
					.getBytes()));
			String signature2 = systemECKey.signMessage(docHash);
			logger.info("Signature - " + signature2);
			doc.append("scriptSig", signature2 + " " + systemPublicKey);
			logger.info("Document with signature- " + doc.toJson());
			// Generate hash256 hex and add to document
			String docHash2 = Utils.HEX.encode(Sha256Hash.hashTwice(doc
					.toJson().getBytes()));
			doc.append("tx_id", docHash2);
			logger.info("Document with hash - " + doc.toJson());
			if (verifyTx(doc)) {
				doc.append("status", "pending");
				doc.append("tx_id", docHash2);
				doc.append("scriptSig", signature2 + " " + systemPublicKey);
				coprApiService.save(doc, "TxPool");
				result.put("status", 200);
				result.put("tx_id", docHash2);
				result.put("wallet_id", userWalletAddress);
				result.put("tx_time", doc.get("tx_time"));
				result.put("height", coprApiService.queryBlockChainHigh(docHash2));
			} else {
				result.put("status", 405);
				result.put("err_msg", "系统故障");
			}
		} catch (Exception ex) {
			String msg = "抵押物发生异常:" + ex.getMessage();
			logger.error(msg, ex);
			result.put("status", 405);
			result.put("err_msg", "系统故障:"+msg);
		}
		logger.info("抵押物登记结束");
		return JSON.toJSONString(result);

	}

	@POST
	@Path("/getInventory")
	public String inventoryQueryData(String params) {
		// TODO Auto-generated method stub
		System.out.println("getInventory");
		logger.info("抵押物 查询");
		Map<String, Object> result = new HashMap<String, Object>();
		// TODO Auto-generated method stub
		if (StringUtils.isBlank(params)) {
			result.put("status", 401);
			result.put("err_msg", "参数为空");
			return JSON.toJSONString(result);
		}
		try {
			// 解码
			byte[] asBytes = Base64.decodeBase64(params);
			String str = new String(asBytes, "utf-8");
			logger.info("=====" + str); // 输出为: some string
			JSONObject json = (JSONObject) JSONObject.fromObject(str);
			/* 校验厂家是否正确 */
			String agentId = json.getString("agent_id");
			if (!StringUtils.equals("beijiaosuo", agentId)) {
				result.put("status", 402);
				result.put("err_msg", "厂家不正确");
				return JSON.toJSONString(result);
			}
			/* 校验加密是否正确 */
			String sign = json.getString("sign");
			json.remove("sign");
			String sign1 = EncryptionUtil.encrytion(json.toString());
			if (!StringUtils.equals(sign1, sign)) {
				result.put("status", 403);
				result.put("err_msg", "sign验证错误");
				return JSON.toJSONString(result);
			}
			String agentUserId = json.has("agent_user_id") == true ? json
					.getString("agent_user_id") : null;// 用户唯一ID
			String inventoryHash = json.has("inventory_hash") == true ? json
					.getString("inventory_hash") : null; // 作品hash(md5)
			String[] inventoryHashs;
			if (inventoryHash != null) {
				inventoryHashs = inventoryHash.split(",");
			} else {
				inventoryHashs = null;
			}
			String txId = json.has("tx_id") == true ? json.getString("tx_id")
					: null; // 区块交易记录号
			String inventoryId = json.has("inventory_id") == true ? json.getString("inventory_id")
					: null; // 抵押物唯一标示
			int pageNumber = json.has("pageNumber") == true ? json
					.getInt("pageNumber") : 1; //
			int pageSize = json.has("pageSize") == true ? json
					.getInt("pageSize") : 10; // 

			DBCursor dbs = coprApiService.queryByConfigInventory("TxPool", agentUserId,
					 txId,inventoryId,inventoryHashs, pageNumber, pageSize);
			try {
				JSONArray jsonArray = new JSONArray();
				if (dbs != null && dbs.size() > 0) {
					result.put("status", 200);
					while (dbs.hasNext()) {
						DBObject document = dbs.next();
						Map map = document.toMap();
						JSONArray jArray = JSONArray.parseArray(map.get("out")
								.toString());
						System.out.println(jArray.getJSONObject(0).get("data"));
						String datajson = jArray.getJSONObject(0).get("data")
								.toString();
						JSONObject jsonObject = JSONObject.fromObject(datajson);
						jsonObject.put("tx_id", document.get("tx_id"));
						jsonObject.put("tx_time", document.get("tx_time"));
						jsonObject.put("height", coprApiService.queryBlockChainHigh(document.get("tx_id").toString()));
						jsonArray.add(jsonObject);
					}
					result.put("data", jsonArray);
				} else {
					result.put("status", 200);
					result.put("data", jsonArray);
				}
			} finally {
				dbs.close();
			}
		} catch (Exception ex) {
			String msg = "抵押物信息发生异常:" + ex.getMessage();
			logger.error(msg, ex);
			result.put("status", 405);
			result.put("err_msg", "系统故障");
		}
		logger.info("抵押物信息结束");
		return JSON.toJSONString(result);
	}

	


}
