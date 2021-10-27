package cn.blma.rpc.server.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * FileName: ServerInfo
 *
 * @author: zhanyigeng
 * Date:     2021/10/15
 * Description: 服务信息
 */
@Data
@AllArgsConstructor
public class ServerInfo {

    private String host;

    private String port;
}
