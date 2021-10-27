package cn.blmas.rpc.client.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;

/**
 * FileName: ConsumeConfigInfo
 *
 * @author: zhanyigeng
 * Date:     2021/10/18
 * Description:
 */
@Slf4j
public class ConsumeBean extends ConsumeConfigInfo implements FactoryBean<ConsumeConfigInfo> {

    @Override
    public ConsumeConfigInfo getObject() throws Exception {
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }
}
