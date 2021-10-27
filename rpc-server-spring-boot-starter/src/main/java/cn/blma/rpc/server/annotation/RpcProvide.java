package cn.blma.rpc.server.annotation;

import java.lang.annotation.*;

/**
 * FileName: Provide
 *
 * @author: zhanyigeng
 * Date:     2021/10/14
 * Description: 服务提供者注解, 被扫描到的类将被注册到注册中心
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RpcProvide {

    /**
     * 服务名称
     *
     * @return
     */
    String name() default "";
}
