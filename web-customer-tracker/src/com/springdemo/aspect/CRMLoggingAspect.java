package com.springdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLoggingAspect {

	// setup logger
	private Logger myLogger = Logger.getLogger(getClass().getName());

	// setup pointcut declarations
	@Pointcut("execution(* com.springdemo.controller.*.*(..))")
	private void forControllerPackage() {
	}

	// same for service and dao
	@Pointcut("execution(* com.springdemo.service.*.*(..))")
	private void forServicePackage() {
	}

	@Pointcut("execution(* com.springdemo.dao.*.*(..))")
	private void forDaoServie() {
	}

	@Pointcut("forControllerPackage() || forDaoServie() || forServicePackage()")
	private void forAppFlow() {
	}

	// add @Before
	@Before("forAppFlow()")
	public void before(JoinPoint theJoinPoint) {

		// display method
		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("====>> in @Before: calling method: " + theMethod);

		// display arguments

		// get arguments

		Object[] args = theJoinPoint.getArgs();
		for (Object arg : args) {
			myLogger.info("====>> argument: " + arg);
		}

	}

	// add @AfterReturning
	@AfterReturning(
			pointcut="forAppFlow()",
			returning="theResult"
			)
	public void afterReturning(JoinPoint theJoinPoint, Object theResult){
		
		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("====>> in @AfterReturning: calling method: " + theMethod);
		
		//display the data returned
		myLogger.info("====>> Result: "+theResult);
	}
}
