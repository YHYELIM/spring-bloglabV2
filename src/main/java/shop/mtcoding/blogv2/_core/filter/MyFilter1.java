package shop.mtcoding.blogv2._core.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.jersey.JerseyProperties.Servlet;

public class MyFilter1 implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("필터 1 실행됨");
        // 서블릿리퀘스트가 부모, http서블릿리퀘스트가 자식인데 자식이 메소드를 더 마니 가지고 있어서 다운캐스팅해주는거
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // 1. IP 로그 남기기
        System.out.println("접속자 ip : " + req.getRemoteAddr());
        System.out.println("접속자 user agent : " + req.getHeader("User-Agent"));

        // 2. 블랙리스트 추방
        // -> 직접 응답 버퍼를 달아서 아웃 함
        // flush 안붙여도 되는 이유: printwriter가 알아서 해줌
        // 내가 설정한 글자가 포함되어있는건 다 쫓음
        // 리턴해버리면 코드가 더이상 밑으로 진행 안됨
        // 필터에서는 모든걸 직접 해줘야함 charset-utf8 이런거 여태껏은 우리가 yml설정을 해놔서 한글 안깨졌지만
        // 컨트롤러의 도움을 못받으면 직접 해줘야함
        // res.setHeader : 헤더에 마임타입 입력
        // html 사이에다가 자바 변수를 바인딩하는것도 가능함
        // 여기에 html 작성해서 바인딩할수있지만 번거롭고 어렵다
        // 그래서 머스태치가 나온거다

        // 2. 블랙리스트 추방
        if (req.getHeader("User-Agent").contains("XBox")) {
            // resp.setContentType("text/html; charset=utf-8");
            resp.setHeader("Content-Type", "text/html; charset=utf-8");
            PrintWriter out = resp.getWriter();

            req.setAttribute("name", "홍길동");
            out.println("<h1>나가세요 크롬이면 : " + req.getAttribute("name") + "</h1>");
            return;
        }

        // 3. session값 저장 (가공)도 가능

        // 4. 인증체크 가능
        // 근데 모든 주소가 인증이 필요한게 아니니까 필터에 걸지 않는다
        // 만약에 우리 사이트가 모든게 인증
        chain.doFilter(request, response);// 다음 필터로 request, response 전달.. 필터없으면 DS로 잔딜

    }
}
