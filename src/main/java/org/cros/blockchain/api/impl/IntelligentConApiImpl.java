/**
 * File Name:IntelligentConApiImpl.java
 * Package Name:org.weibei.blockchain.api.impl
 * Date:2017年4月26日上午10:58:38
 * Copyright (c) 2017, hokuny@foxmail.com All Rights Reserved.
 *
 */
package org.cros.blockchain.api.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.Document;
import org.cros.blockchain.api.IIntelligentConApi;
import org.cros.blockchain.api.coprs.CoprApiService;
import org.cros.blockchain.util.EncryptionUtil;

import com.alibaba.fastjson.JSON;

import net.sf.json.JSONObject;

/**
 * ClassName:IntelligentConApiImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年4月26日 上午10:58:38 <br/>
 *
 * @author hokuny@foxmail.com
 * @version
 * @since JDK 1.6
 * @see
 */
public class IntelligentConApiImpl implements IIntelligentConApi {
    private Log logger = LogFactory.getLog(IntelligentConApiImpl.class);

    @Resource(name = "coprApiService")
    private CoprApiService coprApiService;

    /**
     * 创建智能合约组件
     *
     * @param params
     * @return
     */
    @Override
    @POST
    @Path("/newComponents")
    public String createContractComponents(String params) {

        System.out.println("newContract");
        logger.info("智能合约组件");
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
            JSONObject json = JSONObject.fromObject(str);

            /* 校验厂家是否正确 */
            String agentId = json.getString("agent_id");
            if (!StringUtils.equals("beijiaosuo", agentId)) {
                result.put("status", 402);
                result.put("err_msg", "厂家不正确");
                return JSON.toJSONString(result);
            }
            /* l */
            String sign = json.getString("sign");
            json.remove("sign");
            String sign1 = EncryptionUtil.encrytion(json.toString());
            if (!StringUtils.equals(sign1, sign)) {
                logger.info(json.toString());
                result.put("status", 403);
                result.put("err_msg", "sign验证错误");
                return JSON.toJSONString(result);
            }
            // 判断规则 三个字段 key值 描述 自定义
            // key description status
            if (json.containsKey("key") && json.containsKey("description")
                    && json.containsKey("status")) {

            } else {
                result.put("status", 401);
                result.put("err_msg", "必填参数缺失");
                return JSON.toJSONString(result);
            }
            // 解析参数
            String key = json.has("key") == true ? json.getString("key") : "";
            String description = json.has("description") == true ? json.getString("description")
                    : "";
            String value = json.has("value") == true ? json.getString("value") : "";
            String status = json.has("status") == true ? json.getString("status") : "";
            // create a document for data

            Document doc = new Document("agent_id", agentId).append("key", key)
                    .append("description", description).append("value", value)
                    .append("status", status);

            logger.info("Raw document - " + doc.toJson());
            coprApiService.save(doc, "components");

        } catch (Exception ex) {
            String msg = "保存组件发生异常:" + ex.getMessage();
            logger.error(msg, ex);
            result.put("status", 405);
            result.put("err_msg", "系统故障:" + msg);
        }
        return JSON.toJSONString(result);
    }

    /**
     * 创建合同实例
     *
     * @param params
     * @return
     */
    @Override
    @POST
    @Path("/newContractIn")
    public String createContractInstance(String params) {

        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 创建合同模板
     *
     * @param params
     * @return
     */
    @Override
    @POST
    @Path("/newModel")
    public String createContractModel(String params) {
        System.out.println("newContract");
        logger.info("智能合约模板");
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
            JSONObject json = JSONObject.fromObject(str);

            /* 校验厂家是否正确 */
            String agentId = json.getString("agent_id");
            if (!StringUtils.equals("beijiaosuo", agentId)) {
                result.put("status", 402);
                result.put("err_msg", "厂家不正确");
                return JSON.toJSONString(result);
            }
            /* l */
            String sign = json.getString("sign");
            json.remove("sign");
            String sign1 = EncryptionUtil.encrytion(json.toString());
            if (!StringUtils.equals(sign1, sign)) {
                logger.info(json.toString());
                result.put("status", 403);
                result.put("err_msg", "sign验证错误");
                return JSON.toJSONString(result);
            }
            // 判断规则 三个字段 key值 描述 自定义
            // key description status
            if (json.containsKey("contract_template")) {
            } else {
                result.put("status", 401);
                result.put("err_msg", "必填参数缺失");
                return JSON.toJSONString(result);
            }
            // 解析参数

            String key = json.has("contract_template") == true ? json.getString("contract_template")
                    : "";
            // create a document for data
            Document doc = new Document("contract_template", agentId);
            logger.info("Raw document - " + doc.toJson());
            coprApiService.save(doc, "tx_pool");

        } catch (Exception ex) {
            String msg = "保存组件发生异常:" + ex.getMessage();
            logger.error(msg, ex);
            result.put("status", 405);
            result.put("err_msg", "系统故障:" + msg);
        }
        return JSON.toJSONString(result);
    }

    /**
     * 获取智能合约组件
     *
     * @param params
     * @return
     */
    @Override
    @POST
    @Path("/getComponents")
    public String getContractComponents(String params) {

        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 获取合同实例
     *
     * @param params
     * @return
     */
    @Override
    @POST
    @Path("/getContractIn")
    public String getContractInstance(String params) {

        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 获取合同模板
     *
     * @param params
     * @return
     */
    @Override
    @POST
    @Path("/getModel")
    public String getContractModel(String params) {

        // TODO Auto-generated method stub
        return null;
    }

}
