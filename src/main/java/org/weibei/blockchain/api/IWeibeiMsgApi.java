package org.weibei.blockchain.api;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


/**
 * @ClassName: IWeibeiMsgApi
 * @Package org.weibei.blockchain.api
 * @Description:TODO 融担通合同、抵押物存证API接口
 * @date: 2017年4月25日 上午9:13:45
 * @author hokuny@foxmail.com
 * @version 
 */
@Path("/weibei")
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public interface IWeibeiMsgApi {
	/**
	 * 提交合同登记信息
	 * @param params
	 * @return
	 */
	@POST
	@Path("/newContract")
	public String contractRegisterData(String params);

	/**
	 * 查询合同信息
	 * @param params
	 * @return
	 */
	@POST
	@Path("/getContract")
	public String contractQueryData(String params);
	
	/**
	 * 提交抵押物登记信息
	 * @param params
	 * @return
	 */
	@POST
	@Path("/newInventory")
	public String inventoryRegisterData(String params);

	/**
	 * 查询抵押物信息
	 * @param params
	 * @return
	 */
	@POST
	@Path("/getInventory")
	public String inventoryQueryData(String params);
}
