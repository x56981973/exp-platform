package my.app.platform.service.UrlFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author 夏之阳
 * 创建时间：2016-04-17 14:57
 * 创建说明：url过滤
 */

@Service
public class FilterService extends OncePerRequestFilter {
    @Autowired
    HttpSession session;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String url = httpServletRequest.getRequestURI();
        if(url.contains("test")){    //测试接口不拦截
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        if(url.contains("login")){    //登陆页面不拦截
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }

        if (session.getAttribute("uid") == null || session.getAttribute("name") == null) {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login");
            return;
        }

        if(url.contains("admin")){
            if( "admin".equals(session.getAttribute("role")) ){
                filterChain.doFilter(httpServletRequest,httpServletResponse);
                return;
            } else {
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/error");
                return;
            }
        }

        if(url.contains("teacher")){
            if( "teacher".equals(session.getAttribute("role")) ){
                filterChain.doFilter(httpServletRequest,httpServletResponse);
                return;
            } else {
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/error");
                return;
            }
        }

        if(url.contains("student")){
            if( "student".equals(session.getAttribute("role")) ){
                filterChain.doFilter(httpServletRequest,httpServletResponse);
                return;
            } else {
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/error");
                return;
            }
        }


        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
