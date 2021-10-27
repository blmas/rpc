package cn.blma.rpc.server.netty.server;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * FileName: RedisServerProperties
 *
 * @author: zhanyigeng
 * Date:     2021/10/22
 * Description:
 */
@Data
@ConfigurationProperties(prefix = "rpc.server")
public class RedisServerProperties {

    private Boolean enable;

    private String addr;
}
