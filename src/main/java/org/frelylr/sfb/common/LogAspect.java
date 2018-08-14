package org.frelylr.sfb.common;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class LogAspect {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);

    /**
     * pointcut of the controller log
     */
    @Pointcut("execution(public * org.frelylr.sfb.controller.*.*(..))")
    public void controllerLog() {
    }

    /**
     * pointcut of the service log
     */
    @Pointcut("execution(public * org.frelylr.sfb.service.*.*(..))")
    public void serviceLog() {
    }

    /**
     * pointcut of the dao log
     */
    @Pointcut("execution(public * org.frelylr.sfb.dao.*.*(..))")
    public void daoLog() {
    }

    /**
     * log of controller start
     */
    @Before("controllerLog()")
    public void controllerBefore(JoinPoint jp) throws Throwable {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        LOGGER.info("【URL】: " + "[" + request.getMethod() + "]" + request.getRequestURL().toString());
        LOGGER.info("【CONTROLLER_START】: " + getExecuteMethod(jp));
    }

    /**
     * log of controller end
     */
    @AfterReturning(returning = "jp", pointcut = "controllerLog()")
    public void controllerAfterReturning(JoinPoint jp) throws Throwable {

        LOGGER.info("【CONTROLLER_END】: " + getExecuteMethod(jp));
    }

    /**
     * log of service start
     */
    @Before("serviceLog()")
    public void serviceBefore(JoinPoint jp) throws Throwable {

        LOGGER.info("【SERVICE_START】: " + getExecuteMethod(jp));
    }

    /**
     * log of service end
     */
    @AfterReturning(returning = "jp", pointcut = "serviceLog()")
    public void serviceAfterReturning(JoinPoint jp) throws Throwable {

        LOGGER.info("【SERVICE_END】: " + getExecuteMethod(jp));
    }

    /**
     * log of dao start
     */
    @Before("daoLog()")
    public void daoBefore(JoinPoint jp) throws Throwable {

        LOGGER.info("【DAO_START】: " + getExecuteMethod(jp));
    }

    /**
     * log of dao end
     */
    @AfterReturning(returning = "jp", pointcut = "daoLog()")
    public void daoAfterReturning(JoinPoint jp) throws Throwable {

        LOGGER.info("【DAO_END】: " + getExecuteMethod(jp));
    }

    /**
     * get running method
     */
    private String getExecuteMethod(JoinPoint jp) {

        return jp.getSignature().getDeclaringTypeName() + ": " + jp.getSignature().getName();
    }

}
