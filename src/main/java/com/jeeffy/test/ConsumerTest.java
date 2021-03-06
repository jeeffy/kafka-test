package com.jeeffy.test;

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
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);

        consumer.subscribe(Arrays.asList("test3"));

        /*List<TopicPartition> tpList = new ArrayList<TopicPartition>();
        for (PartitionInfo pi : consumer.partitionsFor("tamboo_config")) {
            tpList.add(new TopicPartition(pi.topic(), pi.partition()));
        }
        consumer.assign(tpList);
        consumer.seekToBeginning(tpList.toArray(new TopicPartition[] {}));*/
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records){
                System.out.printf("offset = %d, key = %s, value = %s", record.offset(), record.key(), record.value());
                System.out.println();

            }
        }
    }
}
