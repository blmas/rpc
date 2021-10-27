package cn.blma.rpc.server.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;
import rpc.common.utils.SerializationUtil;


/**
 * FileName: RpcEncoder
 *
 * @author: zhanyigeng
 * Date:     2021/10/18
 * Description:
 */
@Slf4j
public class RpcEncoder extends MessageToByteEncoder {

    private final Class<?> aClass;

    public RpcEncoder(Class<?> aClass) {
        this.aClass = aClass;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object obj, ByteBuf out) {
        log.info("正在执行RpcEncoder....");
        if (aClass.isInstance(obj)) {
            byte[] bytes = SerializationUtil.serialize(obj);
            out.writeInt(bytes.length);
            out.writeBytes(bytes);
        }
    }
}
