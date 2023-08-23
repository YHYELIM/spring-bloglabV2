package shop.mtcoding.blogv2._core.error;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import shop.mtcoding.blogv2._core.error.ex.MyApiException;
import shop.mtcoding.blogv2._core.util.ApiUtil;
import shop.mtcoding.blogv2._core.util.Script;

@RestControllerAdvice
// 데이터를 응답할거라서
public class MyExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public String error(RuntimeException e) {
        return Script.back(e.getMessage());
        // 자스 응답
        // 우리는 제이슨으로 응답해야하는데? 제이슨으로 통신할거니까 -> 익셉션 종류를 추가해야한다
    }

    @ExceptionHandler(MyApiException.class)
    public ApiUtil<String> error(MyApiException e) {
        return new ApiUtil<String>(false, e.getMessage());
    }
}
