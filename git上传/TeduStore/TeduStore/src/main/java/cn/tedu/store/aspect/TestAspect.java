package cn.tedu.store.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TestAspect {

	@Around("bean(*Service)")
	public Object test(ProceedingJoinPoint p) throws Throwable {
		try {
			
			long t1 = System.currentTimeMillis();
			Object obj = p.proceed();
			long t2 = System.currentTimeMillis();
			Signature m = p.getSignature();
			System.out.println((t2-t1)+":"+m);
			return obj;
			
		} catch (Throwable e) {
			//继续抛出业务异常
			throw e;
		}
	}
}
