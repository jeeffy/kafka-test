package com.jeeffy.test;

import org.apache.kafka.clients.producer.*;

import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.Future;

/**
 * Created by jeeffy on 1/18/16.
 */
public class ProducerCallbackTest {

    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        InputStream inputStream = ProducerCallbackTest.class.getResourceAsStream("/producer.properties");
        props.load(inputStream);
        Producer<String, String> producer = new KafkaProducer(props);

        long start = System.currentTimeMillis();
        for(int i = 0; i < 100000000; i++){
            ProducerRecord record = new ProducerRecord<String, String>("test", Integer.toString(i), Integer.toString(i));
            Future<RecordMetadata> future = producer.send(record,new Callback() {
                public void onCompletion(RecordMetadata metadata, Exception e) {
                    if(e != null){
                        e.printStackTrace();
                    }
                    //metadata.offset();
                    System.out.println(metadata.offset());
                }
            });
        }
        long end = System.currentTimeMillis();
        System.out.println("------------"+(end - start));

        producer.close();
    }
}
