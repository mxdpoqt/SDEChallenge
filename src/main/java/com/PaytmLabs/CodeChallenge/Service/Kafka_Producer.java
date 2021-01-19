package com.PaytmLabs.CodeChallenge.Service;

import com.PaytmLabs.CodeChallenge.Beans.UserData;

public interface Kafka_Producer {
	public void send(UserData inputData) throws Exception;
}
