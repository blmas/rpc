package cn.blmas.rpc.client.consumer;

import lombok.Data;

/**
 * FileName: ConsumeConfigInfo
 *
 * @author: zhanyigeng
 * Date:     2021/10/18
 * Description:
 */
@Data
public class ConsumeConfigInfo {

    /**
     * 服务别名
     */
    private String provideName;

    /**
     * 接口
     */
    private String api;

}
