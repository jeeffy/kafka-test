package com.jf;

import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jeeffy on 1/18/16.
 */
public class ProducerTest {
	private static final Logger log = LoggerFactory.getLogger(ProducerTest.class);
	
    public static void main(String[] args) throws Exception {

        Properties props = new Properties();
        InputStream inputStream = ProducerTest.class.getResourceAsStream("/producer.properties");
        props.load(inputStream);
        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        long start = System.currentTimeMillis();
        //producer.send(new ProducerRecord<String, String>("test", Integer.toString(1), Integer.toString(21)));


        for(int i = 0; i < 10; i++){
            //long start = System.currentTimeMillis();
            Future<RecordMetadata> future = producer.send(new ProducerRecord<String, String>("acl-test", Integer.toString(i), Integer.toString(i)));
            //future.get().offset();
            //long end = System.currentTimeMillis();
            //System.out.println(end - start);
            //future.get().offset();
            log.info("offset is {}", future.get().offset());
            //System.out.println(future.get().offset());
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        producer.close();
    }
}
