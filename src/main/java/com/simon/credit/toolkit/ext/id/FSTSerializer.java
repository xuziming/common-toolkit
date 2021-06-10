package com.simon.credit.toolkit.ext.id;

import org.nustaq.serialization.FSTConfiguration;
import org.springframework.data.redis.serializer.RedisSerializer;

public class FSTSerializer implements RedisSerializer<Object> {

    private FSTConfiguration fstConfiguration ;

    public FSTSerializer() {
        fstConfiguration = FSTConfiguration.getDefaultConfiguration();
        fstConfiguration.setClassLoader(Thread.currentThread().getContextClassLoader());
    }

    @Override
    public byte[] serialize(Object obj) {
        if (obj==null) {
            return null;
        }
        return fstConfiguration.asByteArray(obj);
    }

    @Override
    public Object deserialize(byte[] bytes) {
        if (bytes==null || bytes.length==0) {
            return null;
        }
        return fstConfiguration.asObject(bytes);
    }
}