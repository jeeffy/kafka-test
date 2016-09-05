package com.jeeffy.test;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;

/**
 * Created by jeeffy on 1/18/16.
 */
public class SASLProducerTest {

    public static void main(String[] args) throws Exception {
    	//-Djava.security.auth.login.config=/etc/kafka/kafka_client_jaas.conf
    	System.setProperty("java.security.auth.login.config", "/etc/kafka/kafka_client_jaas.conf");

        Properties props = new Properties();
        InputStream inputStream = SASLProducerTest.class.getResourceAsStream("/sasl-producer.properties");
        props.load(inputStream);
        Producer<byte[], byte[]> producer = new KafkaProducer<byte[], byte[]>(props);
        long start = System.currentTimeMillis();
        //producer.send(new ProducerRecord<String, String>("test", Integer.toString(1), Integer.toString(21)));

        Map<MetricName, ? extends Metric> metrics = producer.metrics();
    	System.out.println(metrics);
    	
        for(int i = 0; i < 10000; i++){
            //long start = System.currentTimeMillis();
            Future<RecordMetadata> future = producer.send(new ProducerRecord<byte[], byte[]>("acl-test", ("k" + i).getBytes(), ("v" + i).getBytes()));
            //future.get().offset();
            //long end = System.currentTimeMillis();
            //System.out.println(end - start);
            //future.get().offset();
            Thread.sleep(1000);
            System.out.println(future.get().offset());
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        producer.close();
    }
}
