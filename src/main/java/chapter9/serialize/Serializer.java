package chapter9.serialize;

import chapter9.serialize.impl.JSONSerializer;

public interface Serializer {

    Serializer DAFAULT = new JSONSerializer();

    /**
     * 获取序列化算法
     * @return
     */
    byte getSerializerAlogrithm();

    /**
     * 序列化
     * @param object
     * @return
     */
    byte[] serialize(Object object);

    /**
     * 反序列化
     * @param clazz
     * @param bytes
     * @param <T>
     * @return
     */
    <T> T dserialize(Class<T> clazz, byte[] bytes);
}
