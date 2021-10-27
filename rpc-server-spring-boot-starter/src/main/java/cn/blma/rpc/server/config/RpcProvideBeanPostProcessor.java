package cn.blma.rpc.server.config;

import cn.blma.rpc.server.annotation.RpcProvide;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Type;
import java.util.Objects;

/**
 * FileName: RpcProvideBeanPostProcessor
 *
 * @author: zhanyigeng
 * Date:     2021/10/15
 * Description:
 */
@Slf4j
public class RpcProvideBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        this.cacheProvideBean(bean);
        return bean;
    }

    private void cacheProvideBean(Object bean) {

        Class<?> aClass = bean.getClass();
        RpcProvide rpcProvide = aClass.getAnnotation(RpcProvide.class);
        if (Objects.isNull(rpcProvide)) {
            return;
        }
        String name = rpcProvide.name();
        Type[] interfaces = aClass.getGenericInterfaces();
        if (interfaces.length == 0) {
            throw new RuntimeException("被provide注解的类上没有实现api接口");
        }
        String typeName = interfaces[0].getTypeName();
        if (!StringUtils.hasText(name)) {
            name = ClassUtils.getShortName(typeName);
        }
        ProviderCache.providers.put(name, bean);

    }
}
