package com.PaytmLabs.CodeChallenge.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PaytmLabs.CodeChallenge.Beans.ResponseBean;
import com.PaytmLabs.CodeChallenge.Beans.UserData;
import com.PaytmLabs.CodeChallenge.Beans.UserDataDisplay;
import com.PaytmLabs.CodeChallenge.Service.Kafka_Producer;
import com.PaytmLabs.CodeChallenge.Service.SparkService;

@RestController  
@RequestMapping("/codeChalleng")  
public class Kafka_Producer_Controller {
	@Autowired
	private Kafka_Producer kafkaService;
	
	@Autowired
	private SparkService sparkService;
	
	@PostMapping("/writeData")
	public ResponseBean receiveData(@RequestBody UserData userdata) {
		ResponseBean res = new ResponseBean();
		try {
			kafkaService.send(userdata);
			res.setSuccess(true);
			res.setMessage("custom reply message");
			//here the data can be anything kind of resposne that client needs
			res.setData(null);
		}catch (Exception e) {
			
			res.setSuccess(false);
			res.setMessage(e.getMessage());
			res.setData(null);
			// TODO: handle exception
		}
		return res;
	}
	
	@GetMapping("/readData")
	public ResponseBean receiveData() {
		ResponseBean res = new ResponseBean();
		try {
			List<UserDataDisplay> userDataList = new ArrayList<>();
			userDataList=sparkService.process();
			res.setSuccess(true);
			res.setMessage("user data to be display");
			//here the data can be anything kind of resposne that client needs
			res.setData(userDataList);
		}catch (Exception e) {
			
			res.setSuccess(false);
			res.setMessage(e.getMessage());
			res.setData(null);
			// TODO: handle exception
		}
		return res;
	}
}
