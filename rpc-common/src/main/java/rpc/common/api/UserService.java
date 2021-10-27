package rpc.common.api;

/**
 * FileName: UserService
 *
 * @author: zhanyigeng
 * Date:     2021/10/21
 * Description:
 */
public interface UserService {

    String getName();

    String doLogin(String name);
}
