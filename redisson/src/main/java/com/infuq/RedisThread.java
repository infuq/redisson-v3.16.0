package com.infuq;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

public class RedisThread {


    public static void main(String[] args) throws Exception {

        Config config = createConfig();
        RedissonClient redissonClient = Redisson.create(config);

        System.out.println(redissonClient);


        String key = "address:001";
        RLock rLock = redissonClient.getLock(key);
        if (!rLock.tryLock(10, 60, TimeUnit.SECONDS)) {
            System.out.println("加锁失败");
        }
        try {
            System.out.println("加锁成功.");
        } finally {
            if (rLock.isLocked() && rLock.isHeldByCurrentThread()) {
                rLock.unlock();
            }
        }



    }

    public static Config createConfig() {
        Config config = new Config();

        config.useSingleServer()
                .setAddress("redis://172.31.3.199:6379")
                .setConnectTimeout(10000)
                .setDatabase(2)
                .setPassword("9527");

        return config;
    }




}
