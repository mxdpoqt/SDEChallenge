package com.PaytmLabs.CodeChallenge.Service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import scala.Tuple2;
import com.PaytmLabs.CodeChallenge.Beans.UserData;
import com.PaytmLabs.CodeChallenge.Beans.UserDataDisplay;
import com.PaytmLabs.CodeChallenge.Service.SparkService;


@Service
public class SparkServiceImpl implements SparkService, Serializable{

	@Value("${topic}")
	private  String topic;
	
	@Value("${spring.kafka.consumer.group-id}")
	private String gourpId;
	
	@Value("${spring.kafka.bootstrap-servers}")
	private String bootStrapServer;
	@Override
	public List<UserDataDisplay> process() {
		List<UserDataDisplay> res = new ArrayList<>();
		SparkConf conf = new SparkConf()
                .setAppName("PaytmCodeChallenge")//give the app a name
                .setMaster("masterURL of a distribute cluster")//how many thread can be ran in local or the URL of a cluster can be ran
                .set("databaseHostLocation", "localhost:xxxx");

        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", bootStrapServer);
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", StringDeserializer.class);
        kafkaParams.put("serializedClass", UserData.class);
        kafkaParams.put("group.id", gourpId);
        kafkaParams.put("auto.offset.reset", "latest");
        kafkaParams.put("enable.auto.commit", false);
   
        Set<String> topics = Collections.singleton(topic);

        JavaStreamingContext jsc = new JavaStreamingContext(
                new JavaSparkContext(conf),
                Durations.seconds(5));//fetch the data in every 5 s
        
        jsc.checkpoint("checkpoint"); //to make sure the meta data can be restored=>the ability to reprocess historical data in case of bugs in the processing logic.

        // get the data stream from kafka
        JavaInputDStream<ConsumerRecord<String, String>> stream = KafkaUtils.createDirectStream(
                jsc,
                LocationStrategies.PreferConsistent(),
                ConsumerStrategies.<String, String>Subscribe(topics, kafkaParams)
        );
     

        JavaPairDStream<String, String> countDStream =stream.mapToPair(record->new Tuple2<>(record.key(), record.value()));//here we can initial the DStream 
        
        /***
         * regarding above we need do whatever the business requirement requires. In here I will store the data into a sql database and return the data 
         * **/
        countDStream.foreachRDD(v -> {
            v.foreach(record -> {
                String sql = String.format("sql statement here");
                UserDataDisplay oneRecord = new UserDataDisplay();
                String id = record._1;
                String time = record._2.split("-")[1];
                oneRecord.setUserId(id);
                oneRecord.setEventTime(Long.valueOf(time));
                oneRecord.setMessage(record._2.split("-")[0]);
                res.add(oneRecord);
            });
            
        });
        jsc.start();
        jsc.awaitTermination();
        return res;
	}

}
