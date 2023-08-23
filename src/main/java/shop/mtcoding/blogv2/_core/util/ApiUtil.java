package shop.mtcoding.blogv2._core.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiUtil<T> {
    private boolean sucuess; // true
    private T data;// 제네릭 사용할거다 //댓글쓰기 성공
    // 언제는 문자열로 응답할수도 숫자를 응답할수도
    // 어떤 타입으로 응답할지 모를때는 제네릭 사용
    // 서세스 : 트루-> 댓글쓰기 성공

    // 무조건 이 형태로만 응답 하는걸 정해줌
    // -> 공통디티오
    public ApiUtil(boolean sucuess, T data) {
        this.sucuess = sucuess;
        this.data = data;
    }

}
