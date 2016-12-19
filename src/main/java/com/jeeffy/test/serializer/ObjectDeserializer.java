package com.jeeffy.test.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class ObjectDeserializer<T> implements Deserializer<T> {
    private ObjectMapper objectMapper = new ObjectMapper();

    private Class<T> clazz;

    @Override
    public void configure(Map<String, ?> props, boolean isKey) {
        String classStr = (String) props.get("value.deserializer.class");
        try {
            clazz = (Class<T>) Class.forName(classStr);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public T deserialize(String topic, byte[] bytes) {
        if (bytes == null)
            return null;

        T data;
        try {
            data = objectMapper.readValue(bytes, clazz);
        } catch (Exception e) {
            throw new SerializationException(e);
        }

        return data;
    }

    @Override
    public void close() {
    }

}
