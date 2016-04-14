package com.cmsz.ck.runnable;

import java.util.Properties;

import com.cmsz.ck.model.KafkaModel;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class KafkaProducer implements Runnable{
		private KafkaModel kafkaModel;
		private Producer<Integer, String> producer;
		private Properties props = new Properties();
		public KafkaProducer(KafkaModel kafkaModel) {
			this.kafkaModel = kafkaModel;
			props.put("serializer.class", "kafka.serializer.StringEncoder");
			props.put("metadata.broker.list", "192.168.1.136:9092");
			producer = new Producer<Integer, String>(new ProducerConfig(props));
		}
		@Override
		public void run() {
			//将消息发送到kafka服务器
			producer.send(new KeyedMessage<Integer, String>(kafkaModel.getTopic(), kafkaModel.getTextContent()));
		}
	}