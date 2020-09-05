package com.simon.credit.toolkit.ext.id;

import com.simon.credit.toolkit.lang.SnowFlake;
import com.simon.credit.toolkit.network.NetToolkits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 分布式ID生成器
 * snowflake算法做些调整，最后0 ~ 6bit作为自增序列号，从0 ~ 2^7，循环使用
 * 每秒并发数降低为10bit ,即最多1024/s
 * @author XUZIMING 2020-09-05
 */
@Service
public class IdGenerator {

    private static final String PREFIX = "distributed.id.service";

    /** distributed.id.service.id_generator */
    private static final String ID_GENERATOR_PREFIX = PREFIX + ".id_generator";

    /** distributed.id.service.id_generator.mac_num_incr */
    private static final String SNOWFLAKE_MAC_ID_INCR = ID_GENERATOR_PREFIX + ".mac_num_incr";

    /** distributed.id.service.id_generator.ip. */
    private static final String SNOWFLAKE_MAC_ID = ID_GENERATOR_PREFIX + ".ip.";

    private static final String LOCAL_IP   = "127.0.0.1";
    private static final String LOCAL_HOST = "localhost";

    @Autowired
    private RedisTemplate<String, Long> template;

    private ValueOperations<String, Long> operations;

    private SnowFlake snowFlake;

    private int macId = 0;

    @PostConstruct
    protected void init() {
        operations = template.opsForValue();

        String localIpAddress = NetToolkits.getLocalAddress().getHostAddress();
        if (LOCAL_IP.equals(localIpAddress) || LOCAL_HOST.equals(localIpAddress)) {
            throw new RuntimeException("snowflake can not use localhost as it's mac identity, init fail! ");
        }

        // distributed.id.service.id_generator.ip.192.168.67.128
        String macIdRedisKey = SNOWFLAKE_MAC_ID + localIpAddress;
        Long macId = operations.get(macIdRedisKey);
        if (macId == null) {
            macId = operations.increment(SNOWFLAKE_MAC_ID_INCR, 1);
            if (macId >= SnowFlake.MAX_MACHINE_NUM) {
                throw new RuntimeException("snowflake mac larger than " + SnowFlake.MAX_MACHINE_NUM + ", mac id exhausted, init fail!");
            }
            operations.set(macIdRedisKey, macId);
        }

        snowFlake = new SnowFlake(macId);
    }

    public Long generateId() {
        return snowFlake.nextId();
    }

}