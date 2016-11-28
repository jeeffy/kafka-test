package com.jeeffy.test;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * 先启动consumer,再启动producer测试
 */
public class KafkaProduceSample {
	public static final String TOPIC_NAME = "test";

	public static void main(String[] args) {
		produce();
	}

	public static void produce() {
		Properties props = new Properties();
		props.put("bootstrap.servers", "kf1:9092,kf2:9092");
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		Producer<String, String> producer = new KafkaProducer<String, String>(props);
		for (int i = 0; i < 10000; i++) {
			ProducerRecord<String, String> record = new ProducerRecord<String, String>(TOPIC_NAME, "message" + i);
			producer.send(record);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		producer.flush();
		producer.close();
	}
}
