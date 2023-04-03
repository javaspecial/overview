package com.shadath.overview.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserAspect {

    @Before(value = "execution(* com.shadath.overview.service.UserService.*(..)) && args(username,password)")
    public void authenticate(JoinPoint joinPoint, String username, String password) {
        System.out.println("Before method:" + joinPoint.getSignature());
        System.out.println("Creating user: username-" + username + "password-" + password);
    }

    @Before(value = "execution(* com.shadath.overview.sampledata.UserData.*(..)) && args(applicationReadyEvent)")
    public void appReady(JoinPoint joinPoint, ApplicationReadyEvent applicationReadyEvent) {
        System.out.println("Before method:" + joinPoint.getSignature());
        System.out.println("Creating user: applicationReadyEvent-" + applicationReadyEvent.getTimestamp());
    }
}
