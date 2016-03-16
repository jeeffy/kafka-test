package com.jf;

import com.alibaba.fastjson.JSON;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConsumerTest {

    public static void main(String[] args) throws Exception {


        Properties props = new Properties();
        //zookeeper连接信息
        props.put("zookeeper.connect", "localhost:2181");
        props.put("zookeeper.session.timeout.ms", "30000");
        props.put("zookeeper.connection.timeout.ms", "40000");
        props.put("group.id", "g1");
        //props.put("auto.commit.enable", "false");
        ConsumerConnector consumer = Consumer.createJavaConsumerConnector(new ConsumerConfig(props));
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("test", 1);
        KafkaStream<byte[], byte[]> stream = consumer.createMessageStreams(map).get("test").get(0);

        for (MessageAndMetadata<byte[], byte[]> msgAndMetadata : stream) {
            byte[] msg = msgAndMetadata.message();
            byte[] key = msgAndMetadata.key();
            Object obj = JSON.parse(new String(msg, "utf8"));
            System.out.println(obj);


        }

        consumer.shutdown();
    }
}
