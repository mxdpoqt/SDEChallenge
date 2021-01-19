package com.PaytmLabs.CodeChallenge.Service.impl;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.PaytmLabs.CodeChallenge.Beans.UserData;
import com.PaytmLabs.CodeChallenge.Service.Kafka_Producer;

@Service
public class Kafka_ProducerImpl implements Kafka_Producer{
	/**
	 * This service is used for handling producer of kafka. In real project we should config the kafkaTemplate and try to use factory patern to return kafkaTemplate
	 * Also, we may want to use the Async way to send the message to specific topic
	 * 
	 * **/
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	@Value("${topic}")
	private  String topic;
	
	public Kafka_ProducerImpl(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}
	@Override
	public void send(UserData userData) throws Exception {
		//1. To validate the message
		boolean isVaild = this.validateMessage(userData);
		String message = userData.getData()+"-"+userData.getEventTime();
		String key = userData.getUserId();
		if(isVaild) {
			message = this.basicProcessingForMessage(message);
			ProducerRecord producerRecord = new ProducerRecord(topic, key, message);
			this.kafkaTemplate.send(producerRecord);
		}else {
			throw new Exception("error_message");
		}
		
	}
	
	private boolean validateMessage(UserData message) {
		boolean result =true;
		/***
		 * To validate the input message and return true if the message if Vaild, otherwise return false;s
		 * **/
		return result;
	}
	
	private String basicProcessingForMessage(String message) {
		/**
		 * To do some pre-process for the input message (i.e cleaning, formating etc)
		 * **/
		return message;
	}
}
