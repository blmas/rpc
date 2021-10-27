package cn.blma.rpc.server.registry.redis;

import cn.blma.rpc.server.provider.ProvideConfigInfo;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import rpc.common.utils.JsonUtil;

import java.util.*;

/**
 * FileName: RedisRegistryCenter
 *
 * @author: zhanyigeng
 * Date:     2021/10/15
 * Description:
 */
public class RedisRegistryCenter {

    private static Jedis jedis = null;

    private static final String DEFAULT_KEY = "RedisRegistryCenter";

    public static void init(String host, int port) {
        if (jedis == null) {
            // 池基本配置
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxIdle(5);
            config.setTestOnBorrow(false);
            JedisPool jedisPool = new JedisPool(config, host, port);
            jedis = jedisPool.getResource();
        }
    }

    public static void exportProvider(String instance, String host, Integer port) {
        ProvideConfigInfo provideConfigInfo = new ProvideConfigInfo(instance, host, port);
        String provides = jedis.hget(DEFAULT_KEY, instance);
        List<ProvideConfigInfo> provideList;
        if (!StringUtils.hasText(provides)) {
            provideList = Collections.singletonList(provideConfigInfo);
            jedis.hset(DEFAULT_KEY, instance, JsonUtil.objToStr(provideList));
            return;
        }
        provideList = JsonUtil.strToList(provides, ProvideConfigInfo.class);
        assert provideList != null;
        Set<ProvideConfigInfo> provideSet = new HashSet<>(provideList);
        provideSet.add(provideConfigInfo);
        jedis.hset(DEFAULT_KEY, instance, JsonUtil.objToStr(new ArrayList<>(provideSet)));
    }

    public static Map<String, List<ProvideConfigInfo>> allProvideMap() {
        Map<String, String> configMap = jedis.hgetAll(DEFAULT_KEY);
        if (CollectionUtils.isEmpty(configMap)) {
            return Collections.emptyMap();
        }
        Map<String, List<ProvideConfigInfo>> provideMap = new HashMap<>(configMap.size());
        configMap.forEach((instance, val) -> {
            List<ProvideConfigInfo> provideSet = JsonUtil.strToList(val, ProvideConfigInfo.class);
            provideMap.put(instance, provideSet);
        });
        return provideMap;
    }

}
