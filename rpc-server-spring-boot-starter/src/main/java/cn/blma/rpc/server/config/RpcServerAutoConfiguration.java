package cn.blma.rpc.server.config;

import cn.blma.rpc.server.netty.server.RedisServerProperties;
import cn.blma.rpc.server.registry.redis.RedisRegistryCenter;
import cn.blma.rpc.server.registry.redis.RedisRegistryProperities;
import cn.blma.rpc.server.netty.server.RpcServer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * FileName: RpcAutoConfiguration
 *
 * @author: zhanyigeng
 * Date:     2021/10/14
 * Description: 自动配置类
 */
@EnableConfigurationProperties({RedisRegistryProperities.class, RedisServerProperties.class})
@Configuration
@ComponentScan("cn.blma.rpc")
public class RpcServerAutoConfiguration implements ApplicationContextAware {

    @Value("${rpc.instance}")
    private String instance;

    @Resource
    private RedisRegistryProperities redisRegistryProperities;

    @Resource
    private RedisServerProperties redisServerProperties;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        RedisRegistryCenter.init(redisRegistryProperities.getHost(), redisRegistryProperities.getPort());
    }

    @Bean
    @ConditionalOnProperty(prefix = "rpc.server", name = "enable", havingValue = "true")
    public RpcServer rpcServer() {
        return new RpcServer(instance, redisServerProperties.getAddr());
    }


    @Bean
    @ConditionalOnProperty(prefix = "rpc.server", name = "enable", havingValue = "true")
    public RpcProvideBeanPostProcessor rpcProvideBeanPostProcessor() {

        return new RpcProvideBeanPostProcessor();
    }
}
