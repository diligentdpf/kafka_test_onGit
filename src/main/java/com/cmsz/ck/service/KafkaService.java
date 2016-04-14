/**
 * 
 */
package com.cmsz.ck.service;

import com.cmsz.ck.model.KafkaModel;
import com.cmsz.ck.model.UserModel;

/**
 * @author oysj
 *
 */
public interface KafkaService {
	/** 发送数据到Kafka服务器 */
	int sendMsgToKafka(KafkaModel kafkaModel)throws Exception;
	/** 检测用户是否存在返回用户信息 */
	UserModel checkAndReturnUserInfo(String id);
	/** 保存用户消息 */
	int saveMessage(String str)throws Exception;
}
