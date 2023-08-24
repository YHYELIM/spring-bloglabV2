package shop.mtcoding.blogv2._core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import shop.mtcoding.blogv2._core.interceptor.LoginInterceptor;

//톰켓이 결국엔 자바 파일로 
//yml파일등 결국에는 자바파일로 바꾼다
//xml파일 : 톰캣이 들고 있는 설정파일 -> 개발자 니들은 xml로 해 내가 자바 파일로 바꿔줄게
//xml파일들이 난무하다보니까  스프링부트 포조가 나옴 야 xml 그만써란 얘기
//톰캣떄문에 web.xml 우리가 만질 필요가 없었는데 커스텀 마이징을 하고싶단 말임
//문서로 보고 외부에서 자원을 찾으려고 할때 
//식별자 요청을 여태껏 했으니 디스패쳐 서블릿 한테 니가 알아서 컨트롤러 찾아줘
//확장자 요청을 하면 디서 한테 안주고 web.문서를 보고 static으로 가기로 되어있음
//우리는 이 문서를 web문서를 재정의 할수있다 (오버라이딩) 문서 하나 더 준다고 생각하면 됨
//사진을 외부경로에서 접근할 
//문지기의 문서

@Configuration // 설정파일 컴포넌트 스캔
public class WebMvcConfig implements WebMvcConfigurer {// 오버라이드 되려면 타입 맞춰준다 web파일 오버라이드

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        WebMvcConfigurer.super.addResourceHandlers(registry);// 니 기존 하던거는 하고

        registry.addResourceHandler("/images/**")// 이렇게 요청하면
                .addResourceLocations("file:" + "./images/") // 이 경로로 이미지를 찾는다
                .setCachePeriod(10)// 사진을 다운받으면 10초 동안 캐싱
                .resourceChain(true)
                .addResolver(new PathResourceResolver());

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/user/update", "/user/updateForm")
                .addPathPatterns("/api/**")
                .addPathPatterns("/board/**")// 발동 조건
                .excludePathPatterns("/board/{id:[0-9]+}");// 발동 제외
    }

}
