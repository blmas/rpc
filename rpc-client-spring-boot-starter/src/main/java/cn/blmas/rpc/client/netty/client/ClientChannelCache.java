package cn.blmas.rpc.client.netty.client;


import rpc.common.msg.Response;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;

/**
 * FileName: ClientChannelCache
 *
 * @author: zhanyigeng
 * Date:     2021/10/21
 * Description:
 */
public class ClientChannelCache {

    public static final Map<String, SynchronousQueue<Response>> CHANNEL_CACHE = new ConcurrentHashMap<>();
}
