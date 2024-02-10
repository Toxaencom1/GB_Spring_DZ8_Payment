package com.taxah.springdz8_payment.aspects;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * TrackUserAspect Class
 * <p>
 * This class implements an aspect in Spring framework to track user actions.
 * It is annotated with @Aspect to indicate that it is an aspect.
 * <p>
 * Usage:
 * - Annotate methods that need to be tracked with @TrackUserAction from com.taxah.spring_dz5.aspects package.
 * - The aspect will log the execution of methods annotated with @TrackUserAction after their execution.
 * <p>
 * The above method execution will be logged by TrackUserAspect after its execution.
 * <p>
 * Methods:
 * - logExecution: A method annotated with @After and listens for methods annotated with @TrackUserAction.
 * It logs the execution of such methods after their execution.
 */
@Slf4j
@Aspect
@Component
public class TrackUserAspect {
    /**
     * logExecution Method
     * <p>
     * Listens for methods annotated with @TrackUserAction and logs their execution.
     *
     * @param joinPoint The join point representing the intercepted method execution.
     * @throws Throwable If an error occurs during execution.
     */
    @After("@annotation(com.taxah.springdz8_payment.aspects.TrackUserAction)")
    @Order(20)
    public void logExecution(JoinPoint joinPoint) throws Throwable {
        String method = joinPoint.getSignature().getName();
        log.info("User action: " + method + " executed!");
    }

    /**
     * Measures the execution time of methods within the package com.taxah.springdz8_payment.service.
     * Intercepts method execution using @Around annotation.
     * Logs method name and execution time using SLF4J.
     * <p>
     * * @Around annotation: execution(* com.taxah.springdz8_payment.service.*.*(..))
     *      * @Order annotation: 10
     * <p>
     * @param joinPoint The ProceedingJoinPoint representing the intercepted method execution.
     * @return The result of the intercepted method execution.
     * @throws Throwable If an error occurs during method execution.
     */
    @Around("execution(* com.taxah.springdz8_payment.facade.TransferFacade.*(..))")
    @Order(10)
    public Object measureMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long elapsedTime = System.currentTimeMillis() - start;
        log.info("Method: " + joinPoint.getSignature().getName() + " - " + elapsedTime + " millis"
                +"\n============================");
        return result;
    }
}
