package gb.common.data.jpa;

import javax.annotation.Nonnull;

import org.springframework.data.repository.NoRepositoryBean;

import gb.common.data.DataRepository;


@NoRepositoryBean
public interface JpaRepository<T, ID> extends DataRepository<T, ID> {
    /**
     * Flushed all pending changes to the database.
     */
    void flush();


    /**
     * Retrieves a reference for an entity by its id.
     *
     * @param id.
     * @return proxy reference.
     */
    T getOne(ID id);


    /**
     * Deletes the given entities.
     *
     * @param entities
     * @throws IllegalArgumentException in case the given {@link Iterable}
     *         is {@literal null}
     */
    void deleteInBatch(@Nonnull Iterable<? extends T> entities);
}
