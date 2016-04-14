/**
 * 
 */
package com.cmsz.ck.runnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

/**
 * @author oysj
 * 此类用于和kafka消费消息
 *
 */
public class KafkaConsumer{
	private ConsumerIterator<byte[], byte[]> it;
	ConsumerConnector consumer;
	public KafkaConsumer(String groupId,String topic){
		it = getConsumerIterator(topic,groupId);
	}
	
	private static ConsumerConfig createConsumerConfig(String groupId){
		Properties props = new Properties();
		props.put("zookeeper.connect", "192.168.1.136:2181");
		props.put("group.id", groupId);
		props.put("zookeeper.session.timeout.ms", "40000");
		props.put("zookeeper.sync.time.ms", "2000");
		props.put("auto.commit.enable", "false");
		return new ConsumerConfig(props);
	}
	public ConsumerIterator<byte[], byte[]> getConsumerIterator(String topic,String groupId){
		consumer = Consumer.createJavaConsumerConnector(createConsumerConfig(groupId));
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(topic, new Integer(1));
		Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
		KafkaStream<byte[], byte[]> stream = consumerMap.get(topic).get(0);
		return stream.iterator();
	}
	public String getMessage(){
		if(it.hasNext())
			return new String(it.next().message());
		return null;
	}
	public void commitOffsets(){
		System.out.println(it.next().offset());
		consumer.commitOffsets();
	}
	public void shutdown(){
		if(it!=null)
			it.clearCurrentChunk();
		if(consumer!=null)
			consumer.shutdown();
		System.out.println("consumer关闭了");
	}
}
