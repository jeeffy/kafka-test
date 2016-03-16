package com.jf;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class KafkaConsumeSample {
	public static final String TOPIC_NAME = "test";

	public static void main(String[] args) {
		 consume();
	}

	public static void consume() {
		Properties props = new Properties();
		props.put("bootstrap.servers", "99.12.156.134:9092,99.12.156.135:9092");
		props.put("group.id", "test");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Arrays.asList(TOPIC_NAME));
		try {
			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(100);
				for (ConsumerRecord<String, String> record : records) {
					System.out.printf("message = %s", record.value()).println();
				}
			}
		} finally {
			consumer.close();
		}

	}

}
