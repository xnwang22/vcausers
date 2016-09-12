package org.nbme.vca.users.sec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Date: 1/14/15
 * Time: 1:31 PM
 */
public class VcaAuthenticationFilter extends GenericFilterBean {
    private static final Logger logger = LoggerFactory.getLogger(VcaAuthenticationFilter.class);

    private RequestMatcher ignored;

    public VcaAuthenticationFilter(RequestMatcher ignored) {
        this.ignored = ignored;
    }

    public VcaAuthenticationFilter() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (ignored != null && ignored.matches(httpServletRequest)) {
            chain.doFilter(request, response);
        } else {
            String authHeader = httpServletRequest.getHeader("Authorization");
            if ( httpServletRequest.getUserPrincipal() == null) { //// TODO: 8/30/2016 authenticate header here
                logger.warn("User principal is null in request");
                HttpServletResponse resp = (HttpServletResponse)response;
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            } else {
                chain.doFilter(request, response);
            }
        }
    }

 }
