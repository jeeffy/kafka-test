package com.jf;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by jeeffy on 1/19/16.
 */
public class ManualOffsetTest {
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        InputStream inputStream = ConsumerTest.class.getResourceAsStream("/consumer.properties");
        props.load(inputStream);
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);

        int commitInterval = 200;
        consumer.subscribe(Arrays.asList("test", "bar"));
        List<ConsumerRecord<String, String>> buffer = new ArrayList<ConsumerRecord<String, String>>();
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(record);
                buffer.add(record);
                if (buffer.size() >= commitInterval) {
                    //insertIntoDb(buffer);
                    consumer.commitSync();
                    buffer.clear();
                }
            }
        }
    }
}
