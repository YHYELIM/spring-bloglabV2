package shop.mtcoding.blogv2._core.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import shop.mtcoding.blogv2._core.util.Script;
import shop.mtcoding.blogv2.user.User;

//인터셉터 생성
public class LoginInterceptor implements HandlerInterceptor {

    // return true이면 컨트롤러 메서드 진입
    // return false이면 요청이 종료됨
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 핸들러 인터셉터 앞에 디폴트 붙어 있으면 강제성이 없다
        // 니가 구현하고 싶은것만 재정의 해서 구현해
        System.out.println("LoginIntercepter preHandle");

        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null) {
            // return false;// 이렇게 하지말고 자스로 응답
            PrintWriter out = response.getWriter();
            out.println(Script.href("/loginForm", "인증이 필요합니다"));
            return false;
        }
        return true;// 상세보기는 인증이 필요하니까 true로 뽑아줘야함
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            @Nullable ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
        System.out.println();
    }

}
