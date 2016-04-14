/**
 * 
 */
package com.cmsz.ck.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.ck.dao.KafkaDao;
import com.cmsz.ck.model.KafkaModel;
import com.cmsz.ck.model.UserModel;
import com.cmsz.ck.runnable.KafkaProducer;
import com.cmsz.ck.service.KafkaService;

import scala.actors.threadpool.ExecutorService;
import scala.actors.threadpool.Executors;

/**
 * @author oysj
 *
 */
@Service
public class KafkaServiceImpl implements KafkaService {
    @Autowired
    KafkaDao kafkaDao;
    private ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    public int sendMsgToKafka(KafkaModel kafkaModel) throws Exception {
        KafkaProducer runnable = new KafkaProducer(kafkaModel);
        executorService.execute(runnable);
        return 0;
    }

    @Override
    public UserModel checkAndReturnUserInfo(String id) {
        try {
            UserModel user = kafkaDao.checkAndReturnUserInfo(id);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public int saveMessage(String str) throws Exception {
        String[] list = spliceContent(str);
        String letterTime = list[0];
        String topic = list[1];
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("letterTime", new Date(Long.parseLong(letterTime)));
        map.put("letterContent", str);
        map.put("topic", topic);
        return kafkaDao.saveMessage(map);
    }

    private String[] spliceContent(String str) {
        String[] arr = str.split("\\$!\\$");
        System.out.println(arr[0]);
        return arr;
    }
}
