package app.handong.feed.config;


import app.handong.feed.interceptor.DefaultInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    //인터셉터 설정을 위함
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new DefaultInterceptor())
                .addPathPatterns("/api/**") //인터셉터가 실행되야 하는 url 패턴 설정
                .excludePathPatterns("/resources/**", "/api/tbuser/login/google", "/api/lab/**"); //인터셉터가 실행되지 않아야 하는 url 패턴
    }

}