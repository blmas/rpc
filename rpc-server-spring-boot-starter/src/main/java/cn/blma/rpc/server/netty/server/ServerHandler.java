package cn.blma.rpc.server.netty.server;

import cn.blma.rpc.server.config.ProviderCache;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import rpc.common.msg.Request;
import rpc.common.msg.Response;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * FileName: ServerHandler
 *
 * @author: zhanyigeng
 * Date:     2021/10/19
 * Description:
 */
@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        log.info("正在执行 ServerHandler...");
        Request request = (Request) msg;
        Response response;
        try {
            Object result = this.handleRequest(request);
            response = Response.success(request.getRequestId(), result);

        } catch (Exception e) {
            response = Response.fail(request.getRequestId(), e.getMessage());
            log.error("Rpc ServerHandler handle request error", e);
        }
        ctx.writeAndFlush(response);
    }

    private Object handleRequest(Request request) throws Exception {
        String alias = request.getAlias();
        Object bean = ProviderCache.providers.get(alias);
        if (Objects.isNull(bean)) {
            throw new RuntimeException("can not find the provider， alias: " + alias);
        }
        Class<?> beanClass = bean.getClass();
        String interfaceName = request.getInterfaceName();

        Class<?> interfaceClass = Class.forName(interfaceName);
        boolean instance = interfaceClass.isInstance(bean);
        if (!instance) {
            throw new RuntimeException("provider interface error， alias: " + alias);
        }
        String methodName = request.getMethodName();
        Method method = beanClass.getMethod(methodName, request.getParamTypes());
        method.setAccessible(true);
        return method.invoke(bean, request.getParams());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

}
