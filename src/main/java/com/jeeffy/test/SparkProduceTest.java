package com.jeeffy.test;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.Random;

/**
 * 先启动consumer,再启动producer测试
 */
public class SparkProduceTest {
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
            Random r = new Random();
            int n = r.nextInt(10);
			ProducerRecord<String, String> record = new ProducerRecord<String, String>(TOPIC_NAME, "message" + n);
			producer.send(record);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		producer.flush();
		producer.close();
	}
}
