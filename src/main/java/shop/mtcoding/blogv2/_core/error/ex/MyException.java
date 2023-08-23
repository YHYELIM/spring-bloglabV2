package shop.mtcoding.blogv2._core.error.ex;

public class MyException extends RuntimeException {
    // 마이익셉션이기도 하고 런타임 익셉션이기도 하다
    public MyException(String message) {
        super(message);
        // 컴포넌트스캔 필요 없다

        // ioc컨테이너한테 안맡김
    }
    // 왜이렇게 만들까 자바는 익셉션 많은데 어떻게 다 기ㅑ억하고 하냐고
    // 우리는 일반적 요청 익셉션 제이ㅅ,ㄴ 요ㅕ청 익셉션

}
