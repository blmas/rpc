package cn.blmas.rpc.client.config;

import cn.blmas.rpc.client.annotation.RpcConsume;
import cn.blmas.rpc.client.discover.ProvideDiscoverService;
import cn.blmas.rpc.client.domain.ConsumeInfo;
import cn.blmas.rpc.client.reflect.ObjectProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.annotation.Resource;
import java.lang.reflect.Field;

/**
 * FileName: RpcProvideBeanPostProcessor
 *
 * @author: zhanyigeng
 * Date:     2021/10/15
 * Description:
 */
@Slf4j
public class RpcConsumeBeanPostProcessor implements BeanPostProcessor {

    @Resource
    private ProvideDiscoverService provideDiscoverService;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        dealConsumeAnnotation(bean);
        return bean;
    }

    private void dealConsumeAnnotation(Object bean) {
        Class<?> aClass = bean.getClass();
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            RpcConsume annotation = field.getAnnotation(RpcConsume.class);
            if (annotation == null) {
                return;
            }
            log.info("正在处理RpcConsume注解");
            String provideName = annotation.provideName();
            String instance = annotation.instance();
            Class<?> type = field.getType();
            ConsumeInfo consumeInfo = new ConsumeInfo(instance, provideName, type);
            field.setAccessible(true);
            try {
                field.set(bean, ObjectProxy.getProxy(provideDiscoverService, consumeInfo));
            } catch (IllegalAccessException e) {
                log.error(e.toString());
            }
        }
        return;
    }

}
