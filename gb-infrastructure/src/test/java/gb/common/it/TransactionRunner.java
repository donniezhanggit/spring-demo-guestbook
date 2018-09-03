package gb.common.it;

import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.hibernate.LazyInitializationException;
import org.springframework.stereotype.Service;


/**
 * Transactional wrapper.
 *
 * Mainly used for doing statements with opened hibernate session.
 *
 * @author whitesquall
 *
 */
@Service
@Transactional
public class TransactionRunner {
    /**
     * Run statements without closing hibernate session for
     * preventing {@link LazyInitializationException}.
     *
     * @param <T> type of supplier's result.
     * @param supplier A function which will run with transaction.
     * @return result from supplier.
     */
    public <T> T doInTransaction(final Supplier<T> supplier) {
        return supplier.get();
    }
}
