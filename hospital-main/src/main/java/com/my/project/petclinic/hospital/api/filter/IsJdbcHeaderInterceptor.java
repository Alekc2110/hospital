package com.my.project.petclinic.hospital.api.filter;

import com.my.project.petclinic.hospital.persistence.config.RequestBackground;
import lombok.Data;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Data
public class IsJdbcHeaderInterceptor implements HandlerInterceptor {

    private final RequestBackground condition;

    public IsJdbcHeaderInterceptor(RequestBackground condition) {
        this.condition = condition;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String isJdbc = request.getHeader("isJdbc");
        condition.setJdbc(Boolean.parseBoolean(isJdbc));
        return true;
    }
}
