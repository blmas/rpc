package cn.blmas.rpc.client.config;

import cn.blmas.rpc.client.discover.ProvideDiscoverService;
import cn.blmas.rpc.client.discover.route.RpcLoadBalanceRoundRobin;
import cn.blmas.rpc.client.redis.RedisRegistryCenter;
import cn.blmas.rpc.client.redis.RedisRegistryProperities;
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
@EnableConfigurationProperties({RedisRegistryProperities.class})
@Configuration
@ComponentScan("cn.blmas.rpc.client")
public class RpcClientAutoConfiguration implements ApplicationContextAware {

    @Resource
    private RedisRegistryProperities redisRegistryProperities;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        RedisRegistryCenter.init(redisRegistryProperities.getHost(), redisRegistryProperities.getPort());
    }

    @Bean
    @ConditionalOnProperty(prefix = "rpc.client", name = "enable", havingValue = "true")
    public ProvideDiscoverService provideDiscoverService() {

        return new ProvideDiscoverService(new RpcLoadBalanceRoundRobin<>(), redisRegistryProperities.getHost(), redisRegistryProperities.getPort());
    }

    @Bean
    @ConditionalOnProperty(prefix = "rpc.client", name = "enable", havingValue = "true")
    public RpcConsumeBeanPostProcessor rpcConsumeBeanPostProcessor() {

        return new RpcConsumeBeanPostProcessor();
    }

}
