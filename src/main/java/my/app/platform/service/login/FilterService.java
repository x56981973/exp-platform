package my.app.platform.service.login;

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
        String uri = httpServletRequest.getRequestURI();
        if(uri.contains("login")){    //登陆页面不拦截
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }

        if (session.getAttribute("t_name") == null) {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login");
            return;
        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
