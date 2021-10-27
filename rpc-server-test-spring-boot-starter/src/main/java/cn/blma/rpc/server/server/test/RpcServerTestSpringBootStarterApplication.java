package cn.blma.rpc.server.server.test;

import cn.blma.rpc.server.annotation.EnableRpcServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRpcServer(basePackages = {"cn.blma.rpc"})
@SpringBootApplication
public class RpcServerTestSpringBootStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(RpcServerTestSpringBootStarterApplication.class, args);
    }

}
