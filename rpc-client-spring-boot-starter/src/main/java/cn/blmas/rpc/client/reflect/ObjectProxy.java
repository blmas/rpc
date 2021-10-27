package cn.blmas.rpc.client.reflect;

import cn.blmas.rpc.client.discover.ProvideDiscoverService;
import cn.blmas.rpc.client.domain.ConsumeInfo;

import java.lang.reflect.Proxy;

/**
 * FileName: ObjectProxy
 *
 * @author: zhanyigeng
 * Date:     2021/10/21
 * Description:
 */
public class ObjectProxy {

    public static <T> T getProxy(ProvideDiscoverService provideDiscoverService, ConsumeInfo consumeInfo) {
        RpcInvocationHandler rpcInvocationHandler = new RpcInvocationHandler(provideDiscoverService, consumeInfo);
        return (T) Proxy.newProxyInstance(consumeInfo.getInterfaceClass().getClassLoader(), new Class[]{consumeInfo.getInterfaceClass()}, rpcInvocationHandler);
    }
}
