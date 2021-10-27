package cn.blma.rpc.server.client.test;

import cn.blmas.rpc.client.annotation.RpcConsume;
import org.springframework.web.bind.annotation.*;
import rpc.common.api.UserService;

/**
 * FileName: UserServiceClient
 *
 * @author: zhanyigeng
 * Date:     2021/10/21
 * Description:
 */
@RestController
@RequestMapping("user")
public class UserServiceController {

    @RpcConsume(instance = "rpc-server-1", provideName = "userService")
    private UserService userService;

    @GetMapping("name")
    public String getName() {
        return userService.getName();
    }

    @PostMapping("login")
    public String login(@RequestParam("name") String name) {
        return userService.doLogin(name);
    }
}
