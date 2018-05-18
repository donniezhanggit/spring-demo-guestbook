package gb.common.it;

import java.util.function.Supplier;

import javax.transaction.Transactional;

import gb.common.annotations.Api;


/**
 * Dumb API wrapper.
 * Needed for emulation of standard API work flow.
 * Mainly used for doing statements with opened hibernate session.
 * @author whitesquall
 *
 */
@Api
@Transactional
public class DelegateApi {
    /**
     * Run statements without closing hibernate session for
     * preventing {@link LazyInitializationException}.
     *
     * @param <T> type of supplier's result.
     * @param supplier A function which will run with transaction.
     * @return result from supplier.
     */
    public <T> T doWithTransaction(final Supplier<T> supplier) {
        return supplier.get();
    }
}
