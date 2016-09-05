package com.jeeffy.test;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import com.unistacks.tamboo.kafkaclient.consumer.KafkaConsumer;
import com.unistacks.tamboo.kafkaclient.consumer.KafkaConsumerFactory;

/**
 * Created by jeeffy on 1/18/16.
 */
public class TambooSASLConsumerTest {
    public static void main(String[] args) throws Exception {
    	KafkaConsumer consumer;
		try {
			consumer = KafkaConsumerFactory.getConsumer("kfu1:9093", "acl-test", "group1","guest","guest");
			while (true) {
				ConsumerRecord<byte[], byte[]> record = consumer.receive();
				System.out.println(new String(record.key()) + ":" + new String(record.value()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
