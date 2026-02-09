package cms.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class UriInterceptor implements HandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                          Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            String requestUri = request.getRequestURI();
            String contextPath = request.getContextPath();
            String queryString = request.getQueryString();

            // URI 정보를 model에 추가
            modelAndView.addObject("currentUri", requestUri);
            modelAndView.addObject("contextPath", contextPath);

            // 전체 URL (query string 포함)
            if (queryString != null && !queryString.isEmpty()) {
                modelAndView.addObject("fullUrl", requestUri + "?" + queryString);
            } else {
                modelAndView.addObject("fullUrl", requestUri);
            }

            // context path를 제외한 URI
            String uri = requestUri.substring(contextPath.length());
            modelAndView.addObject("uri", uri);
        }
    }
}
