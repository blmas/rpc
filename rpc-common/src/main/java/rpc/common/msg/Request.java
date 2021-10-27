package rpc.common.msg;

import lombok.Data;

import java.io.Serializable;

/**
 * FileName: Request
 *
 * @author: zhanyigeng
 * Date:     2021/10/18
 * Description:
 */
@Data
public class Request implements Serializable {

    /**
     * 请求id
     */
    private String requestId;

    /**
     * 接口名称
     */
    private String alias;

    /**
     * 接口类
     */
    private String interfaceName;

    /**
     * 接口方法
     */
    private String methodName;

    /**
     * 接口入参类型
     */
    private Class<?>[] paramTypes;

    /**
     * 接口入参
     */
    private Object[] params;


}
