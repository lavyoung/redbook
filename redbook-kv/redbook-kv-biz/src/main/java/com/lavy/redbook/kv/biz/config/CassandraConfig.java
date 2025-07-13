//package com.lavy.redbook.kv.biz.config;
//
//import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
//
//import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
//
//import jakarta.annotation.Resource;
//
/// **
// * @author <a href="lavyoung1325@outlook.com">lavy</a>
// * @date: 2025/7/13
// * @version: v1.0.0
// * @description: cassandra 配置
// */
//@Configuration
//public class CassandraConfig extends AbstractCassandraConfiguration  {
//
//    @Resource
//    private CassandraProperties cassandraProperties;
//    @Resource
//    private DriverConfigLoader driverConfigLoader;
//
//    @Override
//    protected String getKeyspaceName() {
//
//        return cassandraProperties.getKeyspaceName();
//    }
//
//
//}
