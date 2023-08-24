package shop.mtcoding.blogv2._core.config;

import javax.servlet.FilterRegistration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import shop.mtcoding.blogv2._core.filter.MyFilter1;

@Configuration
// 얘도 안에 컴포넌트가 들어가있음 어차피 다 컴포넌트 스캔이 돼서 ioc에 올라감
// 근데 보기엔 특정한 설정이 잇으니까 볼때 구분짓기편하라고
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<MyFilter1> myFilter1() {
        FilterRegistrationBean<MyFilter1> bean = new FilterRegistrationBean<>(new MyFilter1());
        bean.addUrlPatterns("/*");// 슬래시로 시작하는 모든 주소에 발동됨 /* : 모든 주소
        // * 한개적었다가 안되면 두개적으면 됨 - url 패턴
        // ds앞에 설정 -필터레지스트레이션빈
        // 스프링의 버전이 바뀌면 안먹힘 그럴땐 문서 찾아봐야한다

        bean.setOrder(0);// 낮은 번호부터 실행됨
        return bean;

    }
}
// 빈을 붙이면
// 메서드의 리턴값을 ioc컨테이너에 띄운다
// new를 지가 한게 아닌데 new는 메서드가 하지만 리턴 값을 빈이 ioc에 띄운다