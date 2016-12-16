package com.jeeffy.test.serializer;

import org.apache.commons.lang.SerializationUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

/**
 * Created by jeeffy on 1/18/16.
 */
public class ConsumerTest {
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers","localhost:9092");
        props.setProperty("group.id","group1");
        props.setProperty("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer","org.apache.kafka.common.serialization.ByteArrayDeserializer");

        KafkaConsumer<String, byte[]> consumer = new KafkaConsumer<String, byte[]>(props);

        consumer.subscribe(Arrays.asList("test"));

        while (true) {
            ConsumerRecords<String, byte[]> records = consumer.poll(100);
            for (ConsumerRecord<String, byte[]> record : records){
                User user = (User)SerializationUtils.deserialize(record.value());
                //System.out.printf("offset = %d, key = %s, value = %s", record.offset(), record.key(), new String(record.value()));
                System.out.printf("offset = %d, key = %s, value = %s", record.offset(), record.key(), user);
                System.out.println();

            }
        }
    }
}
