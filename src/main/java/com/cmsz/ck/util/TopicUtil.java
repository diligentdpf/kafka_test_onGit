package com.cmsz.ck.util;

import kafka.admin.DeleteTopicCommand;
import kafka.admin.TopicCommand;

public class TopicUtil {
	public static void main(String[] args) {
		queryTopics("192.168.1.199:2181");
	}
	/** 查询所以主题 */
	public static void queryTopics(String zookeeperAddr) {
		String[] options = new String[] { "--list", "--zookeeper", zookeeperAddr };
		TopicCommand.main(options);
	}

	/** 创建主题 */
	public static void createTopic(String zookeeperAddr, String topicName) {
		String[] options = new String[] { "--create", "--zookeeper", zookeeperAddr, "--partitions", "20", "--topic",
				topicName, "--replication-factor", "1" };
		TopicCommand.main(options);
	}

	/** 查询指定主题 */
	public static void detailTopic(String zookeeperAddr, String topicName) {
		String[] options = new String[] { "--describe", "--zookeeper", zookeeperAddr, "--topic", topicName };
		TopicCommand.main(options);
	}

	/** 删除主题 */
	public static void delTopic(String zookeeperAddr, String topicName) {
		String[] options = new String[] { "--zookeeper", zookeeperAddr, "--topic", topicName };
		DeleteTopicCommand.main(options);
	}

}
