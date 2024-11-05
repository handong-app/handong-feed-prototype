package app.handong.feed.interceptor;


import app.handong.feed.exception.NoAuthenticatedException;
import app.handong.feed.util.TokenFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class DefaultInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //컨트롤러 진입 전에 호출되는 메서드
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getHeader("Authorization") != null) {
            TokenFactory tokenFactory = new TokenFactory();
            String reqUserId = tokenFactory.verifyToken(request.getHeader("Authorization").replaceAll("Bearer ", ""));
            request.setAttribute("reqUserId", reqUserId);
        } else {
            throw new NoAuthenticatedException("Not Authenticated User");
        }
        //logger.info("preHandle / refreshToken [{}]", response.getHeader("refreshToken"));

        String requestURI = request.getRequestURI();
        String requestMethod = request.getMethod();
        String ipAddr = request.getHeader("X-Forwarded-For");
        if (ipAddr == null || ipAddr.isEmpty()) {
            ipAddr = request.getRemoteAddr();
        }
        logger.info("preHandle [{} {}] : {} / {}", requestMethod, requestURI, ipAddr, request.getAttribute("reqUserId"));
        return true;
    }

    //컨트롤러 실행 후에 호출되는 메서드
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        logger.info("postHandle / request [{}]", request);
    }

    //모든것을 마친 후 실행되는 메서드
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
