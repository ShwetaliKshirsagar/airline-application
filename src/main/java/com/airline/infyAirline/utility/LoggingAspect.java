package com.airline.infyAirline.utility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class LoggingAspect {
	private static Log logger = LogFactory.getLog(LoggingAspect.class);
	
	@AfterThrowing(pointcut = "execution(* com.airline.infyAirline.service.*Impl.*(..))", throwing = "exception")
	public void afterThrowing(Exception exception) throws Exception {
		logger.error(exception.getMessage(), exception);
	}
	
//	@Around("execution(* com.infy.Employee.service.*Impl.*(..))")
//	public void around(Exception exception) throws Exception {
//		logger.error(exception.getMessage(), exception);
//		
//	}
	
}
