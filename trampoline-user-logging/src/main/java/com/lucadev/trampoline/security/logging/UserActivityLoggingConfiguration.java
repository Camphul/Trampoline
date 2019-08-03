package com.lucadev.trampoline.security.logging;

import com.lucadev.trampoline.security.logging.aop.UserActivityMethodInterceptor;
import com.lucadev.trampoline.security.logging.handler.UserActivityHandler;
import com.lucadev.trampoline.service.time.TimeProvider;
import lombok.RequiredArgsConstructor;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;

/**
 * Configures user activity logging by registering aop advices.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 8/3/19
 */
@RequiredArgsConstructor
class UserActivityLoggingConfiguration {

	private final UserActivityHandler userActivityHandler;

	private final TimeProvider timeProvider;

	@Bean
	public Advisor advisor() {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(
				"@annotation(com.lucadev.trampoline.security.logging.LogUserActivity)");
		return new DefaultPointcutAdvisor(pointcut, userActivityMethodInterceptor());
	}

	@Bean
	private MethodInterceptor userActivityMethodInterceptor() {
		return new UserActivityMethodInterceptor(this.userActivityHandler,
				this.timeProvider);
	}

}
