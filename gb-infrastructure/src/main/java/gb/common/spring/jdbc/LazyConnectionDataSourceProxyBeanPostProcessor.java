package gb.common.spring.jdbc;

import static lombok.AccessLevel.PRIVATE;

import java.lang.reflect.Method;

import javax.sql.DataSource;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import lombok.val;
import lombok.experimental.FieldDefaults;


@Component
public class LazyConnectionDataSourceProxyBeanPostProcessor
implements BeanPostProcessor {
    @Override
    public Object
    postProcessAfterInitialization(final Object bean, final String beanName) {
        if(!(bean instanceof DataSource)) {
            return bean;
        }

        val factory = new ProxyFactory(bean);
        val interceptor = buildInterceptorFor((DataSource) bean);

        factory.setProxyTargetClass(true);
        factory.addAdvice(interceptor);

        return factory.getProxy();
    }


    private static
    DataSourceInterceptor buildInterceptorFor(final DataSource ds) {
        return new DataSourceInterceptor(ds);
    }


    @FieldDefaults(level=PRIVATE, makeFinal=true)
    private static class DataSourceInterceptor implements MethodInterceptor {
        DataSource dataSource;


        public DataSourceInterceptor(final DataSource realDataSource) {
            dataSource = new LazyConnectionDataSourceProxy(realDataSource);
        }


        @Override
        public Object
        invoke(final MethodInvocation invocation) throws Throwable {
            final Method proxyMethod = ReflectionUtils.findMethod(
                    dataSource.getClass(),
                    invocation.getMethod().getName()
            );

            if(proxyMethod != null) {
                return proxyMethod.invoke(dataSource,
                        invocation.getArguments());
            }

            return invocation.proceed();
        }
    }
}
