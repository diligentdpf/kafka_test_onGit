package com.cmsz.ck.kafka;

import com.cmsz.ck.util.TopicUtil;

public class KafkaConsumerProducerDemo {
    public static void main(String[] args) {
        String topicName = "linxinru";
        TopicUtil.createTopic("192.168.1.199:2181", topicName);
        // KafkaProducer producerThread = new KafkaProducer(topicName);
        // producerThread.start();


        // TopicUtil.queryTopics("192.168.1.199:2181");
        KafkaConsumer consumerThread = new KafkaConsumer(topicName);
        consumerThread.start();
    }

}
