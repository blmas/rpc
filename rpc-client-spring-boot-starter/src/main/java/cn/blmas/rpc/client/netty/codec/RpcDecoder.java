package cn.blmas.rpc.client.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import rpc.common.utils.SerializationUtil;

import java.util.List;

/**
 * FileName: RpcDecoder
 *
 * @author: zhanyigeng
 * Date:     2021/10/18
 * Description:
 */
@Slf4j
public class RpcDecoder extends ByteToMessageDecoder {

    private Class<?> aClass;

    public RpcDecoder(Class<?> aClass) {
        this.aClass = aClass;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) {
        log.info("正在执行RpcDecoder....");
        if (in.readableBytes() < 4) {
            return;
        }
        in.markReaderIndex();
        int dataLength = in.readInt();
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }
        byte[] data = new byte[dataLength];
        in.readBytes(data);
        out.add(SerializationUtil.deserialize(data, aClass));
    }
}
