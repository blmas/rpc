package cn.blma.rpc.server.provider;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

/**
 * FileName: ProvideConfigBean
 *
 * @author: zhanyigeng
 * Date:     2021/10/14
 * Description: 暴漏的服务的配置信息
 */
@Data
@AllArgsConstructor
public class ProvideConfigInfo {

    /**
     * 实例
     */
    private String instance;

    /**
     * ip
     */
    private String host;

    /**
     * 端口
     */
    private Integer port;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProvideConfigInfo that = (ProvideConfigInfo) o;
        return Objects.equals(instance, that.instance) &&
                Objects.equals(host, that.host) &&
                Objects.equals(port, that.port);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instance, host, port);
    }
}
