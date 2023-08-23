package shop.mtcoding.blogv2._core.error.ex;

public class MyApiException extends RuntimeException {
    // 마이익셉션이기도 하고 런타임 익셉션이기도 하다
    // 제이슨으로 하는건 전부다 얘로 터트릴거다
    public MyApiException(String message) {
        super(message);
        // 컴포넌트스캔 필요 없다
        // ioc컨테이너한테 안맡김
    }

}
