package cn.blma.rpc.server.netty.server;

import cn.blma.rpc.server.netty.codec.RpcDecoder;
import cn.blma.rpc.server.netty.codec.RpcEncoder;
import cn.blma.rpc.server.registry.redis.RedisRegistryCenter;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import rpc.common.msg.Request;
import rpc.common.msg.Response;

import javax.annotation.PostConstruct;

/**
 * FileName: ServerSocket
 *
 * @author: zhanyigeng
 * Date:     2021/10/19
 * Description:
 */
@Slf4j
public class RpcServer {

    private String serverAddr;

    private String rpcInstance;

    public RpcServer(String rpcInstance, String serverAddr) {
        this.rpcInstance = rpcInstance;
        this.serverAddr = serverAddr;
    }

    @PostConstruct
    public void start() {
        new Thread(() -> {
            NioEventLoopGroup boss = new NioEventLoopGroup(1);
            NioEventLoopGroup worker = new NioEventLoopGroup(5);

            try {
                ServerBootstrap serverBootstrap = new ServerBootstrap();
                serverBootstrap.group(boss, worker).channel(NioServerSocketChannel.class)
                        .option(ChannelOption.SO_BACKLOG, 128)
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            public void initChannel(SocketChannel ch) {
                                ch.pipeline().addLast(
                                        new RpcDecoder(Request.class),
                                        new RpcEncoder(Response.class),
                                        new ServerHandler());
                            }
                        });
                String[] serverAddrArr = serverAddr.split(":");
                String host = serverAddrArr[0];
                Integer port = Integer.parseInt(serverAddrArr[1]);
                ChannelFuture future = serverBootstrap.bind(host, port).sync();
                log.info("rpc server start success，ip: {}, port: {}", host, port);
                RedisRegistryCenter.exportProvider(rpcInstance, host, port);
                future.channel().closeFuture().sync();
            } catch (Exception e) {
                log.error("rpc server start error", e);
            } finally {
                boss.shutdownGracefully();
                worker.shutdownGracefully();
            }
        }).start();
    }
}
