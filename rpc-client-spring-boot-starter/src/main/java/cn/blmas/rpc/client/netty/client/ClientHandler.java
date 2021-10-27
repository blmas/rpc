package cn.blmas.rpc.client.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import rpc.common.msg.Response;

import java.util.concurrent.SynchronousQueue;


/**
 * FileName: ClientHandler
 *
 * @author: zhanyigeng
 * Date:     2021/10/19
 * Description:
 */
@Slf4j
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("正在执行ClientHandler...");
        Response response = (Response) msg;
        String requestId = response.getRequestId();
        SynchronousQueue<Response> queue = ClientChannelCache.CHANNEL_CACHE.get(requestId);
        queue.put(response);
        ClientChannelCache.CHANNEL_CACHE.remove(requestId);
    }
}
