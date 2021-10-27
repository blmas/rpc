package cn.blma.rpc.server.annotation;

import cn.blma.rpc.server.config.RpcServerAutoConfiguration;
import cn.blma.rpc.server.config.RpcProvideScannerRegistrar;
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
@Import({RpcProvideScannerRegistrar.class,  RpcServerAutoConfiguration.class})
public @interface EnableRpcServer {

    /**
     * 扫描路径 rpc相关注解基础包路径
     *
     * @return
     */
    String[] basePackages() default {};
}
