package com.jf;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

/**
 * Created by jeeffy on 1/18/16.
 */
public class ConsumerTest {
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        InputStream inputStream = ConsumerTest.class.getResourceAsStream("/consumer.properties");
        props.load(inputStream);
        /*props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "group1");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");*/
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Arrays.asList("test", "bar"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records){
                System.out.printf("offset = %d, key = %s, value = %s", record.offset(), record.key(), record.value());
                System.out.println();

            }
        }
    }
}