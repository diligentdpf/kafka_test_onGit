/**
 * 
 */
package com.cmsz.ck.dao;

import java.util.Map;

import com.cmsz.ck.model.UserModel;

/**
 * @author oysj
 *
 */
public interface KafkaDao {
	/** 检测用户是否存在返回用户信息 */
	UserModel checkAndReturnUserInfo(String id)throws Exception;
	/** 保存用户消息 */
	int saveMessage(Map<String, Object> map)throws Exception;
}
