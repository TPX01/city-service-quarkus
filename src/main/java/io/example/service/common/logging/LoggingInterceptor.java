package io.example.service.common.logging;

import org.slf4j.Logger;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

@Priority(0)
@Logged
@Interceptor
public class LoggingInterceptor {

    @Inject
    transient Logger log;

    @AroundInvoke
    public Object logMethodEntry(InvocationContext invocationContext)
            throws Exception {
        Method method = invocationContext.getMethod();

        log.info("Entering method: {}#{} with params: {}."
                ,method.getDeclaringClass().getName()
                ,method.getName(),
                buildParamString(invocationContext));
        return invocationContext.proceed();
    }

    String buildParamString(InvocationContext invocationContext) {
        return Arrays.stream(invocationContext.getParameters())
                .map(o -> String.format("%1$s (%2$s)",o.toString(), o.getClass().getName()))
                .collect(Collectors.joining(";"));
    }
}
