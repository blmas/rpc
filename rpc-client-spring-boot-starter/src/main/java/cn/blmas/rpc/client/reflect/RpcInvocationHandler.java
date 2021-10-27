package cn.blmas.rpc.client.reflect;

import cn.blmas.rpc.client.discover.ProvideDiscoverService;
import cn.blmas.rpc.client.domain.ConsumeInfo;
import cn.blmas.rpc.client.netty.client.ClientSocket;
import cn.blmas.rpc.client.provider.ProvideConfigInfo;
import lombok.extern.slf4j.Slf4j;
import rpc.common.msg.Request;
import rpc.common.msg.Response;
import rpc.common.utils.JsonUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.SynchronousQueue;

/**
 * FileName: RpcInvocationHandler
 *
 * @author: zhanyigeng
 * Date:     2021/10/20
 * Description:
 */
@Slf4j
public class RpcInvocationHandler implements InvocationHandler {

    private ConsumeInfo consumeInfo;
    private ProvideDiscoverService provideDiscoverService;

    public RpcInvocationHandler(ProvideDiscoverService provideDiscoverService, ConsumeInfo consumeInfo) {
        this.consumeInfo = consumeInfo;
        this.provideDiscoverService = provideDiscoverService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Request request = new Request();
        request.setRequestId(UUID.randomUUID().toString());
        request.setInterfaceName(consumeInfo.getInterfaceClass().getName());
        request.setAlias(consumeInfo.getProvideName());
        request.setMethodName(method.getName());
        request.setParams(args);
        request.setParamTypes(method.getParameterTypes());
        ProvideConfigInfo provideConfigInfo = provideDiscoverService.get(consumeInfo.getInstance());
        ClientSocket clientSocket = new ClientSocket(provideConfigInfo.getAddr());
        Thread thread = new Thread(clientSocket);
        thread.start();
        SynchronousQueue<Response> synchronousQueue = clientSocket.sendRequest(request);
        Response response = synchronousQueue.take();
        log.info("rpc 调用返回结果: {}", JsonUtil.objToStr(response));
        if (response.isSuccess()) {
            return response.getData();
        }
        throw new RuntimeException(response.getErrorMsg());

    }
}
