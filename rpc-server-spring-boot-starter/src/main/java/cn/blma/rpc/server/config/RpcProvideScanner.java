package cn.blma.rpc.server.config;

import cn.blma.rpc.server.annotation.RpcProvide;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

/**
 * FileName: RpcScanner
 *
 * @author: zhanyigeng
 * Date:     2021/10/14
 * Description: 注解扫描器
 */
public class RpcProvideScanner extends ClassPathBeanDefinitionScanner {
    public RpcProvideScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        addIncludeFilter(new AnnotationTypeFilter(RpcProvide.class));
        return super.doScan(basePackages);
    }
}
