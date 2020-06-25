package com.example.memcached.config;

import org.springframework.context.annotation.Configuration;
import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@Configuration
public class MemCachedConfig {
    @Value("${memcache.server}")
    private String[] servers;
    @Value("${memcache.failover}")
    private boolean failover;
    @Value("${memcache.initConn}")
    private int initConn;
    @Value("${memcache.minConn}")
    private int minConn;
    @Value("${memcache.maxConn}")
    private int maxConn;
    @Value("${memcache.maintSleep}")
    private int maintSleep;
    @Value("${memcache.nagel}")
    private boolean nagel;
    @Value("${memcache.socketTO}")
    private int socketTO;
    @Value("${memcache.aliveCheck}")
    private boolean aliveCheck;

    @Bean
    public SockIOPool sockIOPool () {
        /*
         * SockIOPool建立通信的连接池
         * 这个类用来创建管理客户端和服务器通讯连接池，客户端主要的工作包括数据通讯、服务器定位、hash码生成等都是由这个类完成的。
         * 获得连接池的单态方法。这个方法有一个重载方法getInstance( String poolName )，每个poolName只构造一个SockIOPool实例。
         * 缺省构造的poolName是default。
         */
        SockIOPool pool = SockIOPool.getInstance();
        //设置连接池可用cache服务器列表，服务器构成形式：ip地址+端口号:{39.105.147.99:11211,39.105.147.99:11222}
        pool.setServers(servers);
        //设置容错开关设置为TRUE，当当前socket不可用时，程序会自动查找可用连接并返回，否则返回NULL，默认状态是true，建议保持默认
        pool.setFailover(failover);
        //设置初始连接数
        pool.setInitConn(initConn);
        //设置最小连接数
        pool.setMinConn(minConn);
        //设置最大连接数
        pool.setMaxConn(maxConn);
        //设置可用连接的最长等待时间
        pool.setMaxIdle(1000 * 30 * 30);
        //设置连接池维护线程的睡眠时间，设置为0，维护线程不启动
        pool.setMaintSleep(maintSleep);
        //设置Nagle算法，设置为false，因为通讯数据量比较大要求相应及时
        pool.setNagle(nagel);
        //设置socket读取等待超时时间
        pool.setSocketTO(socketTO);
        //开启心跳检查
        //设为true则每次通信都要进行连接是否有效的监测，造成通信次数倍增，加大网络负载，
        //因此该参数应该在对HA要求比较高的场合设为TRUE，默认状态是false。
        pool.setAliveCheck(aliveCheck);
        //设置完参数后，启动pool
        pool.initialize();
        return pool;
    }

    @Bean
    public MemCachedClient memCachedClient(){
        return new MemCachedClient();
    }
}