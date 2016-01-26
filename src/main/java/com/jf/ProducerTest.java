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
        Properties props = new Properties();
        InputStream inputStream = ProducerTest.class.getResourceAsStream("/producer.properties");
        props.load(inputStream);
        Producer<String, String> producer = new KafkaProducer(props);

        for(int i = 0; i < 3; i++){
            Future<RecordMetadata> future = producer.send(new ProducerRecord<String, String>("test", Integer.toString(i), Integer.toString(i)));
            System.out.println(future.get().offset());
        }

        producer.close();
    }
}
