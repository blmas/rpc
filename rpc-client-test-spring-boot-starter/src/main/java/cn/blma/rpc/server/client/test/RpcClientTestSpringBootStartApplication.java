package cn.blma.rpc.server.client.test;

import cn.blmas.rpc.client.annotation.EnableRpcClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRpcClient(basePackages = {"cn.blma.rpc"})
public class RpcClientTestSpringBootStartApplication {

    public static void main(String[] args) {
        SpringApplication.run(RpcClientTestSpringBootStartApplication.class, args);
    }

}
