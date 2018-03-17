/**
 * Project Name:weibei-cloud
 * File Name:IIntelligentConApi.java
 * Package Name:org.weibei.blockchain.api
 * Date:2017年4月25日上午9:17:37
 * Copyright (c) 2017, hokuny@foxmail.com All Rights Reserved.
 *
*/

package org.cros.blockchain.api;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * ClassName:IIntelligentConApi <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年4月25日 上午9:17:37 <br/>
 * 
 * @author hokuny@foxmail.com
 * @version
 * @since JDK 1.6
 * @see
 */
public interface IIntelligentConApi {
    /**
     * 创建智能合约组件
     * 
     * @param params
     * @return
     */
    @POST
    @Path("/newComponents")
    public String createContractComponents(String params);

    /**
     * 创建合同实例
     * 
     * @param params
     * @return
     */
    @POST
    @Path("/newContractIn")
    public String createContractInstance(String params);

    /**
     * 创建合同模板
     * 
     * @param params
     * @return
     */
    @POST
    @Path("/newModel")
    public String createContractModel(String params);

    /**
     * 获取智能合约组件
     * 
     * @param params
     * @return
     */
    @POST
    @Path("/getComponents")
    public String getContractComponents(String params);

    /**
     * 获取合同实例
     * 
     * @param params
     * @return
     */
    @POST
    @Path("/getContractIn")
    public String getContractInstance(String params);

    /**
     * 获取合同模板
     * 
     * @param params
     * @return
     */
    @POST
    @Path("/getModel")
    public String getContractModel(String params);
}
