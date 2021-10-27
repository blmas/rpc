package cn.blmas.rpc.client.discover;


import cn.blmas.rpc.client.discover.route.RpcLoadBalanceRoundRobin;
import cn.blmas.rpc.client.provider.ProvideConfigInfo;
import cn.blmas.rpc.client.redis.RedisRegistryCenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FileName: ProvideDiscoverService
 *
 * @author: zhanyigeng
 * Date:     2021/10/22
 * Description:
 */
public class ProvideDiscoverService {

    private Map<String, List<ProvideConfigInfo>> provideMap = new HashMap<>();

    private RpcLoadBalanceRoundRobin<ProvideConfigInfo> rpcLoadBalanceRoundRobin;


    public ProvideDiscoverService(RpcLoadBalanceRoundRobin<ProvideConfigInfo> rpcLoadBalanceRoundRobin, String host, Integer port) {
        this.rpcLoadBalanceRoundRobin = rpcLoadBalanceRoundRobin;
        RedisRegistryCenter.init(host, port);
        provideMap = RedisRegistryCenter.allProvideMap();
    }

    public ProvideConfigInfo get(String instance) {
        if (rpcLoadBalanceRoundRobin == null) {
            throw new RuntimeException("ProvideDiscoverService cannot find the RpcLoadBalanceRoundRobin, RpcLoadBalanceRoundRobin is null");
        }
        return rpcLoadBalanceRoundRobin.get(provideMap.get(instance));
    }

    public void refresh() {
        this.provideMap = RedisRegistryCenter.allProvideMap();

    }
}
