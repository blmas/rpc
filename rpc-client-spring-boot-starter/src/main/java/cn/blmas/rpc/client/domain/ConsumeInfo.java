package cn.blmas.rpc.client.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * FileName: ConsumeInfo
 *
 * @author: zhanyigeng
 * Date:     2021/10/22
 * Description:
 */
@Data
@AllArgsConstructor
public class ConsumeInfo {

    private String instance;

    private String provideName;

    private Class<?> interfaceClass;
}
