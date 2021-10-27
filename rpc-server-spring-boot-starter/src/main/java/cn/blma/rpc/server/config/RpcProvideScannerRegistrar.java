package cn.blma.rpc.server.config;

import cn.blma.rpc.server.annotation.EnableRpcServer;
import cn.blma.rpc.server.annotation.RpcProvide;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * FileName: RpcScanner
 *
 * @author: zhanyigeng
 * Date:     2021/10/14
 * Description: 将包路径下扫描到的bean加入到beanFactory
 */
@Slf4j
public class RpcProvideScannerRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(EnableRpcServer.class.getName()));
        if (Objects.isNull(annotationAttributes)) {
            return;
        }

        List<String> basePackages = Arrays.stream(annotationAttributes.getStringArray("basePackages")).filter(StringUtils::hasText).collect(Collectors.toList());
        RpcProvideScanner rpcScanner = new RpcProvideScanner(registry);

        rpcScanner.setBeanNameGenerator((beanDefinition, beanDefinitionRegistry) -> {
            String beanClassName = beanDefinition.getBeanClassName();
            try {
                Class<?> aClass = Class.forName(beanClassName);
                RpcProvide annotation = aClass.getAnnotation(RpcProvide.class);
                if (Objects.isNull(annotation)) {
                    return null;
                }
                String name = annotation.name();
                if (StringUtils.hasText(name)) {
                    return name;
                }
                return ClassUtils.getShortName(aClass);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        });
        rpcScanner.doScan(StringUtils.toStringArray(basePackages));

    }


}
