/**
 * 
 */
package com.cmsz.ck.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cmsz.ck.model.KafkaModel;
import com.cmsz.ck.service.KafkaService;

/**
 * @author oysj
 *
 */
@RestController
public class KafkaController {
	@Autowired
	KafkaService kafkaService;
	@RequestMapping(value="sendMsgToKafka",method = RequestMethod.GET)
	public int sendMsgToKafka(KafkaModel kafkaModel){
		try {
			kafkaService.sendMsgToKafka(kafkaModel);
			return 1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
}
