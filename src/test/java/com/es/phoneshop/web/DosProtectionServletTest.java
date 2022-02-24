package com.es.phoneshop.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

public class DosProtectionServletTest {
    @Mock
    ServletRequest servletRequest;
    @Mock
    HttpServletResponse servletResponse;
    @Mock
    FilterChain filterChain;
    @Mock
    FilterConfig config;
    @Test
    public void testFilter() throws ServletException, IOException {
        DosFilterServlet filter = new DosFilterServlet();
        filter.init(config);
        String ip = "1.1.1.1";
        when(servletRequest.getRemoteAddr()).thenReturn(ip);
        filter.doFilter(servletRequest, servletResponse, filterChain);
        verify(filterChain).doFilter(servletRequest, servletResponse);
        filter.doFilter(servletRequest, servletResponse, filterChain);
        filter.doFilter(servletRequest, servletResponse, filterChain);
        filter.doFilter(servletRequest, servletResponse, filterChain);
        filter.doFilter(servletRequest, servletResponse, filterChain);
        filter.doFilter(servletRequest, servletResponse, filterChain);
        filter.doFilter(servletRequest, servletResponse, filterChain);
        filter.doFilter(servletRequest, servletResponse, filterChain);
        filter.doFilter(servletRequest, servletResponse, filterChain);
        filter.doFilter(servletRequest, servletResponse, filterChain);
        filter.doFilter(servletRequest, servletResponse, filterChain);
        filter.doFilter(servletRequest, servletResponse, filterChain);
        filter.doFilter(servletRequest, servletResponse, filterChain);
        filter.doFilter(servletRequest, servletResponse, filterChain);
        filter.doFilter(servletRequest, servletResponse, filterChain);
        filter.doFilter(servletRequest, servletResponse, filterChain);
        filter.doFilter(servletRequest, servletResponse, filterChain);
        filter.doFilter(servletRequest, servletResponse, filterChain);
        filter.doFilter(servletRequest, servletResponse, filterChain);
        filter.doFilter(servletRequest, servletResponse, filterChain);
        verify(servletResponse).setStatus(429);
    }
}
