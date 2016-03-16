package com.jf;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Arrays;
import java.util.Properties;

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
		props.put("bootstrap.servers", "99.12.156.134:9092,99.12.156.135:9092");
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		Producer<String, String> producer = new KafkaProducer<String, String>(props);
		for (int i = 0; i < 10; i++) {
			ProducerRecord<String, String> record = new ProducerRecord<String, String>(TOPIC_NAME, "message" + i);
			producer.send(record);
		}
		producer.flush();
		producer.close();
	}
}
