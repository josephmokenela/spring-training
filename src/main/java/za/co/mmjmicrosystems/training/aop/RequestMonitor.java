package za.co.mmjmicrosystems.training.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class RequestMonitor {
	
	private static final Logger logger = LoggerFactory.getLogger(RequestMonitor.class);
	
	@Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public Object wrap(ProceedingJoinPoint proceesingJoinPoint) throws Throwable {
		
		logger.info("Before controller method " + proceesingJoinPoint.getSignature().getName() + ". Thread " + Thread.currentThread().getName()); // toString gives more info    	
    	Object retVal = proceesingJoinPoint.proceed();
	    logger.info("Controller method " + proceesingJoinPoint.getSignature().getName() + " execution successful");
	    
	    return retVal;
		
	}

}
