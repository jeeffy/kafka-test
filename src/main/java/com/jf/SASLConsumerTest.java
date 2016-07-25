package com.jf;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * Created by jeeffy on 1/18/16.
 */
public class SASLConsumerTest {
    public static void main(String[] args) throws Exception {
    	System.setProperty("java.security.auth.login.config", "/etc/kafka/kafka_client_jaas.conf");
    	
        Properties props = new Properties();
        InputStream inputStream = SASLConsumerTest.class.getResourceAsStream("/sasl-consumer.properties");
        props.load(inputStream);
//        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        KafkaConsumer<byte[], byte[]> consumer = new KafkaConsumer<byte[], byte[]>(props);

        consumer.subscribe(Arrays.asList("acl-test"));

        while (true) {
            ConsumerRecords<byte[], byte[]> records = consumer.poll(100);
            for (ConsumerRecord<byte[], byte[]> record : records){
                System.out.printf("offset = %d, key = %s, value = %s", record.offset(), new String(record.key()), new String(record.value()));
                System.out.println();

            }
        }
    }
}
