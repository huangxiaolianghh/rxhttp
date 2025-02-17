package rxhttp.wrapper.exception;


import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Response;
import okhttp3.ResponseBody;
import rxhttp.wrapper.OkHttpCompat;

/**
 * 异常处理帮助类
 * User: ljx
 * Date: 2018/11/21
 * Time: 09:30
 */
public class ExceptionHelper {

    /**
     * 根据Http执行结果过滤异常
     *
     * @param response Http响应体
     * @return ResponseBody
     * @throws IOException 请求失败异常、网络不可用异常
     */
    @NotNull
    public static ResponseBody throwIfFatal(Response response) throws IOException {
        ResponseBody body = response.body();
        assert body != null;
        if (!response.isSuccessful()) {
            response = response.newBuilder().body(OkHttpCompat.buffer(body)).build();
            throw new HttpStatusCodeException(response);
        }
        return body;
    }

    /**
     * If the provided Throwable is an Error this method
     * throws it, otherwise returns a RuntimeException wrapping the error
     * if that error is a checked exception.
     *
     * @param error the error to wrap or throw
     * @return the (wrapped) error
     */
    public static RuntimeException wrapOrThrow(Throwable error) {
        if (error instanceof Error) {
            throw (Error) error;
        }
        if (error instanceof RuntimeException) {
            return (RuntimeException) error;
        }
        return new RuntimeException(error);
    }
}
