package com.jeeffy.test.serializer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.Future;

/**
 * Created by jeeffy on 1/18/16.
 */
public class ProducerTest {
	private static final Logger log = LoggerFactory.getLogger(ProducerTest.class);
	
    public static void main(String[] args) throws Exception {

        Properties props = new Properties();
        props.setProperty("bootstrap.servers","localhost:9092");
        props.setProperty("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        props.setProperty("value.serializer","org.apache.kafka.common.serialization.ByteArraySerializer");

        Producer<String, byte[]> producer = new KafkaProducer<String, byte[]>(props);
        long start = System.currentTimeMillis();


        for(int i = 0; i < 10; i++){
            User user = new User("user"+i,i);
            Future<RecordMetadata> future = producer.send(new ProducerRecord<String, byte[]>("test", Integer.toString(i), user.toString().getBytes()));
            System.out.println(future.get().offset());
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        producer.close();
    }
}
