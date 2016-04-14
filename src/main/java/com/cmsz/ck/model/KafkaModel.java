/**
 * 
 */
package com.cmsz.ck.model;

/**
 * @author oysj
 *
 */
public class KafkaModel {
	private String textContent;
	private String topic;

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}


	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}
}
