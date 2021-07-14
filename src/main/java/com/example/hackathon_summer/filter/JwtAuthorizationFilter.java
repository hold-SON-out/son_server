package com.example.hackathon_summer.filter;

import com.example.hackathon_summer.domain.entity.User;
import com.example.hackathon_summer.lib.ConfirmToken;
import com.example.hackathon_summer.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@Order(2)
public class JwtAuthorizationFilter implements Filter {

    private final HandlerExceptionResolver handlerExceptionResolver;
    private AuthService authService;
    private ConfirmToken confirmToken;

    public JwtAuthorizationFilter(HandlerExceptionResolver handlerExceptionResolver) {
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    public void init(FilterConfig filterConfig) {
        ApplicationContext ctx = WebApplicationContextUtils
                .getRequiredWebApplicationContext(filterConfig.getServletContext());

        this.authService = ctx.getBean(AuthService.class);
        this.confirmToken = ctx.getBean(ConfirmToken.class);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request1 = (HttpServletRequest) request;
        if (!request1.getMethod().equals("OPTIONS")) {
            try {
                log.info("jwtFilter");
                String token = confirmToken.removeStartString(request1, "Bearer");
                log.info(token);
                if (token == null) {
                    request1.setAttribute("user", null);
                    chain.doFilter(request, response);
                    return;
                }
                User user = authService.accessTokenDecoding(token);
                request1.setAttribute("user", user);
                chain.doFilter(request, response);
            } catch (Exception e) {
                handlerExceptionResolver.resolveException((HttpServletRequest) request, (HttpServletResponse) response, null, e);
            }
        }
    }
}
