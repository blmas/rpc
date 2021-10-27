package rpc.common.msg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * FileName: Response
 *
 * @author: zhanyigeng
 * Date:     2021/10/18
 * Description:
 */
@Data
@NoArgsConstructor
public class Response implements Serializable {

    /**
     * 请求id
     */
    private String requestId;

    /**
     * 结果
     */
    private Object data;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String errorMsg;

    public Boolean isSuccess() {
        return CodeEnum.SUCCESS.getCode().equals(this.code);
    }

    private Response(String requestId, Integer code, Object data, String errorMsg) {
        this.requestId = requestId;
        this.code = code;
        this.data = data;
        this.errorMsg = errorMsg;
    }

    public static Response success(String requestId, Object data) {

        return new Response(requestId, CodeEnum.SUCCESS.getCode(), data, null);
    }

    public static Response fail(String requestId, String errorMsg) {
        return new Response(requestId, CodeEnum.FAIL.getCode(), null, errorMsg);
    }

    @Getter
    @AllArgsConstructor
    public enum CodeEnum {

        // 200: 成功 500: 失败
        SUCCESS(200), FAIL(500);

        private final Integer code;

    }

}
