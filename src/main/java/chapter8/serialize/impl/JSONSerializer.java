package chapter8.serialize.impl;

import chapter8.serialize.Serializer;
import chapter8.serialize.SerializerAlogrithm;
import com.alibaba.fastjson.JSON;


public class JSONSerializer implements Serializer {
    @Override
    public byte getSerializerAlogrithm() {
        return SerializerAlogrithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T dserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes,clazz);
    }
}
