package br.com.erudio.security.jwt;

import br.com.erudio.exception.IvalidJWTAuthenticationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class JwtFilterToken extends GenericFilterBean {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public JwtFilterToken(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filter)
                    throws IOException, ServletException {
        try {
            var token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
            if(StringUtils.isNotBlank(token) && jwtTokenProvider.validarToken(token)){
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                if(authentication != null){
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            filter.doFilter(request,response);
        } catch (IvalidJWTAuthenticationException e) {
            throw new RuntimeException(e);
        }
    }
}
