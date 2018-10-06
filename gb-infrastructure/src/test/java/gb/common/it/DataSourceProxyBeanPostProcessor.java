package gb.common.it;

import static lombok.AccessLevel.PRIVATE;

import java.lang.reflect.Method;

import javax.sql.DataSource;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import lombok.val;
import lombok.experimental.FieldDefaults;
import net.ttddyy.dsproxy.asserts.ProxyTestDataSource;
import net.ttddyy.dsproxy.listener.logging.SLF4JLogLevel;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;


@Component
@FieldDefaults(level=PRIVATE)
public class DataSourceProxyBeanPostProcessor
implements BeanPostProcessor, ApplicationContextAware {
    final ProxyTestDataSource testDataSource =
            new ProxyTestDataSource();

    ApplicationContext context;


    /**
     * Instead of directly returning a less specific datasource bean
     * (e.g.: HikariDataSource -> DataSource), return a proxy object.
     * See following links for why:
     * https://stackoverflow.com/questions/44237787/how-to-use-user-defined-database-proxy-in-datajpatest
     * https://gitter.im/spring-projects/spring-boot?at=5983602d2723db8d5e70a904
     * http://blog.arnoldgalovics.com/2017/06/26/configuring-a-datasource-proxy-in-spring-boot/
     */
    @Override
    public Object
    postProcessAfterInitialization(final Object bean, final String beanName) {
        if(!(bean instanceof DataSource)) {
            return bean;
        }

        initTestProxyHolderBean();

        val factory = new ProxyFactory(bean);
        val interceptor = buildInterceptorFor((DataSource) bean);

        factory.setProxyTargetClass(true);
        factory.addAdvice(interceptor);

        return factory.getProxy();
    }


    private ProxyDataSourceInterceptor
    buildInterceptorFor(final DataSource ds) {
        return new ProxyDataSourceInterceptor(ds, testDataSource);
    }


    private void initTestProxyHolderBean() {
        final ProxyTestDataSourceHolder holder = context
                .getBean(ProxyTestDataSourceHolder.class);

        holder.setTestDataSource(testDataSource);
    }


    @FieldDefaults(level=PRIVATE, makeFinal=true)
    private static class ProxyDataSourceInterceptor
    implements MethodInterceptor {
        DataSource dataSource;


        public ProxyDataSourceInterceptor(
                final DataSource realDataSource,
                final ProxyTestDataSource testWrapper) {
            val proxied = buildProxyDataSource(realDataSource);

            // ProxyTestDataSource allows us to make assumptions about
            // database queries.
            testWrapper.setDataSource(proxied);

            this.dataSource = testWrapper;
        }


        private static DataSource
        buildProxyDataSource(final DataSource ds) {
            return ProxyDataSourceBuilder.create(ds)
                    .name("myDS")
                    .multiline()
                    .logQueryBySlf4j(SLF4JLogLevel.INFO)
                    .build();
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


    @Override
    public void setApplicationContext(final ApplicationContext context) {
        this.context = context;
    }
}
