package cn.blmas.rpc.client.discover.route;

import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * FileName: RpcLoadBalanceRoundRobin
 *
 * @author: zhanyigeng
 * Date:     2021/10/22
 * Description: 轮询方式
 */
public class RpcLoadBalanceRoundRobin<T>{


    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public synchronized T get(List<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            atomicInteger.set(0);
            throw new RuntimeException("RpcLoadBalanceRoundRobin can not find the valid rpc instance");
        }
        int i = atomicInteger.getAndIncrement();
        int len = list.size();
        if (i >= len) {
            i = 0;
            atomicInteger.set(0);
        }
        if (i < len) {
            return list.get(i);
        }
        throw new RuntimeException("RpcLoadBalanceRoundRobin can not find the valid rpc instance");
    }
}
