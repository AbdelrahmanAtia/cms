package org.javaworld.cmsbackend.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@EnableAspectJAutoProxy(proxyTargetClass=true)
@Component
public class LoggingAspect {
	
	@Pointcut("execution(* org.javaworld.cmsbackend.controller.*.*(..))")
	private void forControllerPackage() {}
	
	@Pointcut("execution(* org.javaworld.cmsbackend.service.*.*(..))")
	private void forServicePackage() {}
	
	@Pointcut("execution(* org.javaworld.cmsbackend.exception.*.*(..))")
	private void forExceptionPackage() {}

	
	@Pointcut("execution(* org.javaworld.cmsbackend.util.*.*(..))")
	private void forUtilPackage() {}
	
	@Pointcut("forControllerPackage() || forServicePackage() || forExceptionPackage() || forUtilPackage()")
	private void forAppFlow() {}
	
	@Before("forAppFlow()")
	public void before(JoinPoint joinPoint) {
		
		CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();

		String methodName = codeSignature.toShortString();
		System.out.println(">> rest-api: inside method: " + methodName);

		
		String[] parameterNames = codeSignature.getParameterNames();
		Object[] parameterValues = joinPoint.getArgs();
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(">> arguments >> ");
		
		for(int i = 0; i < parameterValues.length; i++) {
			stringBuilder.append(parameterNames[i] + ":" + parameterValues[i]);
			if(i < parameterValues.length - 1)
				stringBuilder.append(" , ");
		}
		
		if(parameterValues.length > 0)
			System.out.println(stringBuilder);
		
	}
	
	@AfterReturning(pointcut="forAppFlow()", returning="result")
	public void after(JoinPoint joinPoint, Object result) {
		String methodName = joinPoint.getSignature().toShortString();
		System.out.println(">> rest-api: returning from method: " + methodName);
		System.out.println(">> rest-api: result: " + result);
	}
	
	
}
