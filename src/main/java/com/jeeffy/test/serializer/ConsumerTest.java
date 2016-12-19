package com.jeeffy.test.serializer;

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
        props.setProperty("value.deserializer","com.jeeffy.test.serializer.ObjectDeserializer");
        props.setProperty("value.deserializer.class","com.jeeffy.test.serializer.User");

        KafkaConsumer<String, User> consumer = new KafkaConsumer<String, User>(props);

        consumer.subscribe(Arrays.asList("test"));

        while (true) {
            ConsumerRecords<String, User> records = consumer.poll(100);
            for (ConsumerRecord<String, User> record : records){
                //User user = (User)SerializationUtils.deserialize(record.value());
                //System.out.printf("offset = %d, key = %s, value = %s", record.offset(), record.key(), new String(record.value()));
                System.out.printf("offset = %d, key = %s, value = %s", record.offset(), record.key(), record.value());
                System.out.println();

            }
        }
    }
}
