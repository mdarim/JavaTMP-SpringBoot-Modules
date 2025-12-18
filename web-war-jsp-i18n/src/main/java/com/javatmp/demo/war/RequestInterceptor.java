package com.javatmp.demo.war;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;



@Component
@Slf4j
public class RequestInterceptor implements HandlerInterceptor {

    @Autowired
    MessageSource messageSource;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object object)
            throws Exception {
        log.info(">> Before Handler execution");
        HttpSession session = request.getSession();
        session.setAttribute("locale", LocaleContextHolder.getLocale());
        session.setAttribute("labels", messageSource);
//        this.messageSource.
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object object,
                           ModelAndView model)
            throws Exception {
        log.info("Handler execution is complete");
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object object,
                                Exception exception)
            throws Exception {
        log.info("Request is complete");
    }

}
