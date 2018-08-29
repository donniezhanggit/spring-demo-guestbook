package gb.common.it;

import org.springframework.stereotype.Component;

import lombok.Data;
import net.ttddyy.dsproxy.asserts.ProxyTestDataSource;


@Data
@Component
public class ProxyTestDataSourceHolder {
    ProxyTestDataSource testDataSource;
}
