package cn.blmas.rpc.client.netty.client;

import cn.blmas.rpc.client.netty.codec.RpcDecoder;
import cn.blmas.rpc.client.netty.codec.RpcEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import rpc.common.msg.Request;
import rpc.common.msg.Response;

import java.util.concurrent.SynchronousQueue;

/**
 * FileName: ClientSocket
 *
 * @author: zhanyigeng
 * Date:     2021/10/19
 * Description:
 */
@Slf4j
public class ClientSocket implements Runnable {

    private String serverAddress;

    private final SynchronousQueue<ChannelFuture> futureSynchronousQueue = new SynchronousQueue<>();

    public ClientSocket(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    private ChannelFuture getChannelFuture() {
        try {
            return futureSynchronousQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public SynchronousQueue<Response> sendRequest(Request request) {
        SynchronousQueue<Response> synchronousQueue = new SynchronousQueue<>();
        ClientChannelCache.CHANNEL_CACHE.put(request.getRequestId(), synchronousQueue);
        ChannelFuture channelFuture = getChannelFuture();
        channelFuture.channel().writeAndFlush(request);
        return synchronousQueue;
    }

    @Override
    public void run() {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.AUTO_READ, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) {
                    ch.pipeline().addLast(
                            new RpcDecoder(Response.class),
                            new RpcEncoder(Request.class),
                            new ClientHandler());
                }
            });
            String[] addressArr = serverAddress.split(":");
            String host = addressArr[0];
            int port = Integer.parseInt(addressArr[1]);
            ChannelFuture future = b.connect(host, port).sync();
            futureSynchronousQueue.put(future);
            future.channel().closeFuture().sync();
            log.info("启动client");
        } catch (Exception e) {
            log.error("rpc client start error", e);
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
