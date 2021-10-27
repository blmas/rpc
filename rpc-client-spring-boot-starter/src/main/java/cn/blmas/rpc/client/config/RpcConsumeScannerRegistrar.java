package cn.blmas.rpc.client.config;

import cn.blmas.rpc.client.annotation.EnableRpcClient;
import cn.blmas.rpc.client.annotation.RpcConsume;
import cn.blmas.rpc.client.consumer.ConsumeBean;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * FileName: RpcConsumeScannerRegistrar
 *
 * @author: zhanyigeng
 * Date:     2021/10/20
 * Description:
 */
public class RpcConsumeScannerRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(EnableRpcClient.class.getName()));
        if (Objects.isNull(annotationAttributes)) {
            return;
        }

        List<String> basePackages = Arrays.stream(annotationAttributes.getStringArray("basePackages")).filter(StringUtils::hasText).collect(Collectors.toList());
        ClassPathBeanDefinitionScanner classPathBeanDefinitionScanner = new ClassPathBeanDefinitionScanner(registry, false);
        classPathBeanDefinitionScanner.addIncludeFilter(new AnnotationTypeFilter(RpcConsume.class));
        // 注入ConsumeBean
        for (String basepackage : basePackages) {
            Set<BeanDefinition> candidateComponents = classPathBeanDefinitionScanner.findCandidateComponents(basepackage);
            for (BeanDefinition candidate : candidateComponents) {
                AnnotationMetadata annotationMetadata = ((AnnotatedBeanDefinition) candidate).getMetadata();
                String className = annotationMetadata.getClassName();
                BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(ConsumeBean.class);
                AbstractBeanDefinition beanDefinition = definition.getBeanDefinition();
                BeanDefinitionHolder holder = new BeanDefinitionHolder(beanDefinition, className);
                BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
            }
        }
    }
}
