package cn.blmas.rpc.client.annotation;

import java.lang.annotation.*;

/**
 * FileName: comsumer
 *
 * @author: zhanyigeng
 * Date:     2021/10/18
 * Description:
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RpcConsume {
    /**
     * rpc服务实例名称
     */
    String instance() default "";
    /**
     * 服务名称
     *
     * @return
     */
    String provideName() default "";
}
