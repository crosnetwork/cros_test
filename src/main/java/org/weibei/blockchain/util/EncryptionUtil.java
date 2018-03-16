package org.weibei.blockchain.util;


/**
 * @ClassName: EncryptionUtil
 * @Package org.weibei.blockchain.util
 * @Description:TODO ADD FUNCTION
 * @date: 2016年11月21日 上午8:51:50
 * @author hokuny@foxmail.com
 * @version
 */
public class EncryptionUtil {

	public static String encrytion(
			String params) {
		StringBuilder builder = new StringBuilder();
		builder.append("rongdangtong");
		builder.append(params);
		builder.append("rongdangtong");
		return MD5Util.MD5(builder.toString()).toUpperCase();
	}

}
