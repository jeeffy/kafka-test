package com.jeeffy.test.streams.test;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;

import java.util.Arrays;
import java.util.Properties;

/**
 * Created by jeeffy on 12/8/16.
 */
public class StreamsTest {
    public static void main(String[] args) throws Exception {
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "app1");
        //config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "kfq1:9092");
        //config.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, "zkq1:2181");

        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, "localhost:2181");
        config.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        config.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        final Serde<String> stringSerde = Serdes.String();
        final Serde<Long> longSerde = Serdes.Long();

        KStreamBuilder builder = new KStreamBuilder();
        KStream<String, String> textLines = builder.stream(stringSerde, stringSerde, "test3");

        textLines.foreach((key,value)-> System.out.println(key+"-----"+value));

        KStream<String, Long> wordCounts = textLines
                .flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+")))
                .map((key, word) -> new KeyValue<>(word, word))
                // Required in Kafka 0.10.0 to re-partition the data because we re-keyed the stream in the `map` step.
                // Upcoming Kafka 0.10.1 does this automatically for you (no need for `through`).
                //.through("test")
                //.countByKey("Counts")
                .groupByKey()
                .count("counts")
                .toStream();

        wordCounts.print();
        //wordCounts.to(stringSerde, longSerde, "WordsWithCountsTopic");

        KafkaStreams streams = new KafkaStreams(builder, config);
        streams.start();
        streams.cleanUp();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
