package cn.blma.rpc.server.server.test;

import cn.blma.rpc.server.annotation.RpcProvide;
import rpc.common.api.UserService;

/**
 * FileName: UserServiceImpl
 *
 * @author: zhanyigeng
 * Date:     2021/10/21
 * Description:
 */
@RpcProvide(name = "userService")
public class UserServiceImpl implements UserService {
    @Override
    public String getName() {
            return "小白龙";
    }

    @Override
    public String doLogin(String name) {
        return name + "正在执行登录操作";
    }
}
