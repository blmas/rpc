package cn.blma.rpc.server.registry.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * FileName: RedisRegistryConfigInfo
 *
 * @author: zhanyigeng
 * Date:     2021/10/15
 * Description:
 */
@Data
@ConfigurationProperties(prefix = "registry.redis")
public class RedisRegistryProperities {

    private String host;

    private Integer port;
}
