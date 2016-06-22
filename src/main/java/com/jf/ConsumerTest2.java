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
public class ConsumerTest2 {
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        InputStream inputStream = ConsumerTest2.class.getResourceAsStream("/consumer.properties");
        props.load(inputStream);
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);

        consumer.subscribe(Arrays.asList("test"));

        ConsumerRecords<String, String> records = consumer.poll(1000);
        for (ConsumerRecord<String, String> record : records){
            System.out.printf("offset = %d, key = %s, value = %s", record.offset(), record.key(), record.value());
            System.out.println();

        }
    }
}
