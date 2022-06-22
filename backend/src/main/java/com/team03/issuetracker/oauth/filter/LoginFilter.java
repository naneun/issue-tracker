package com.team03.issuetracker.oauth.filter;

import com.team03.issuetracker.oauth.properties.AuthProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class LoginFilter implements Filter {

    private final AuthProperties authProperties;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestUri = request.getRequestURI();
        response.sendRedirect((authProperties.getVendorProperties(requestUri.substring(requestUri.lastIndexOf("/") + 1))
                .getCodeRequestUri()));
    }
}
