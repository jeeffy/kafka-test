package com.jeeffy.test;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 先启动consumer,再启动producer测试
 */
public class SparkProduceTest {
	public static final String TOPIC_NAME = "test3";

	public static void main(String[] args) {
		produce();
	}

	public static void produce() {
		Properties props = new Properties();
		//props.put("bootstrap.servers", "kfq1:9092,kfq2:9092");
		props.put("bootstrap.servers", "localhost:9092");
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		Producer<String, String> producer = new KafkaProducer<String, String>(props);
		for (int i = 0; i < 1000000; i++) {
            Random r = new Random();
            int n = r.nextInt(10);
			ProducerRecord<String, String> record = new ProducerRecord<String, String>(TOPIC_NAME, "message" + n);
            Future<RecordMetadata> future = producer.send(record);
            try {
                System.out.println(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            System.out.println(record);
            try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		producer.flush();
		producer.close();
	}
}
