package com.jf;

import com.alibaba.fastjson.JSON;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.*;

public class ProducerTest {

    public static void main(String[] args) throws Exception {


        Properties props = new Properties();
        props.put("metadata.broker.list", "localhost:9092");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("key.serializer.class", "kafka.serializer.StringEncoder");
        kafka.javaapi.producer.Producer<String, String> producer = new kafka.javaapi.producer.Producer<String, String>(new ProducerConfig(props));
        /*
        Map<String, String> obj = new HashMap<String, String>();
        obj.put("name", "zs");
        obj.put("id", "001");
        obj.put("age", "20");
        producer.send(new KeyedMessage<String, String>("test", "key", JSON.toJSONString(obj)));
        */

        List<KeyedMessage<String, String>> list = new ArrayList<KeyedMessage<String, String>>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> obj = new HashMap<String, Object>();
            obj.put("name", "zs");
            obj.put("id", i);
            obj.put("age", "20");
            list.add(new KeyedMessage<String, String>("test", "key", JSON.toJSONString(obj)));
        }
        producer.send(list);

        producer.close();
    }
}
