package com.jf;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.Future;

/**
 * Created by jeeffy on 1/18/16.
 */
public class ProducerTest {

    public static void main(String[] args) throws Exception {
    	//-Djava.security.auth.login.config=/etc/kafka/kafka_client_jaas.conf
    	System.setProperty("java.security.auth.login.config", "/etc/kafka/kafka_client_jaas.conf");

        Properties props = new Properties();
        InputStream inputStream = ProducerTest.class.getResourceAsStream("/producer.properties");
        props.load(inputStream);
        Producer<String, String> producer = new KafkaProducer(props);
        long start = System.currentTimeMillis();
        //producer.send(new ProducerRecord<String, String>("test", Integer.toString(1), Integer.toString(21)));


        for(int i = 0; i < 10; i++){
            //long start = System.currentTimeMillis();
            Future<RecordMetadata> future = producer.send(new ProducerRecord<String, String>("test", Integer.toString(i), Integer.toString(i)));
            //future.get().offset();
            //long end = System.currentTimeMillis();
            //System.out.println(end - start);
            //future.get().offset();
            System.out.println(future.get().offset());
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        producer.close();
    }
}
