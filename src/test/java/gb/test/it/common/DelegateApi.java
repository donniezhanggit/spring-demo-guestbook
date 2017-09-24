package gb.test.it.common;

import java.util.function.Supplier;

import javax.transaction.Transactional;

import gb.common.annotations.Api;


@Api
@Transactional
public class DelegateApi {
    public <T> T doWithTransaction(final Supplier<T> supplier) {
        return supplier.get();
    }
}
