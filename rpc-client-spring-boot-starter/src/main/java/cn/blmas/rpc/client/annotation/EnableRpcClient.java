package cn.blmas.rpc.client.annotation;

import cn.blmas.rpc.client.config.RpcClientAutoConfiguration;
import cn.blmas.rpc.client.config.RpcConsumeScannerRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * FileName: EnableRpc
 *
 * @author: zhanyigeng
 * Date:     2021/10/14
 * Description: 支持rpc注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
@Import({RpcConsumeScannerRegistrar.class, RpcClientAutoConfiguration.class})
public @interface EnableRpcClient {

    /**
     * 扫描路径 rpc相关注解基础包路径
     *
     * @return
     */
    String[] basePackages() default {};
}
