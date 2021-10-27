package cn.blma.rpc.server.config;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * FileName: ProviderCache
 *
 * @author: zhanyigeng
 * Date:     2021/10/19
 * Description: 服务接口缓存
 */
public class ProviderCache {

    public static Map<String, Object> providers = new ConcurrentHashMap<>();

}
