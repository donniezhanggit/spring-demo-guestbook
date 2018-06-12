package gb.common.data;

import static gb.common.exceptions.Exceptions.notFound;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import gb.common.exceptions.NotFoundException;


/**
 * Interface for generic CRUD operations on a repository
 * for a specific type.
 * For all available methods visit
 * https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/support/SimpleJpaRepository.html
 *
 */
@NoRepositoryBean
public interface DataRepository<T, ID> extends Repository<T, ID> {
    /**
     * Saves a given entity. Use the returned instance for further
     * operations as the save operation might have changed the entity
     * instance completely.
     *
     * @param entity
     * @return the saved entity
     */
    <S extends T> S save(@Nonnull S entity);


    /**
     * Saves all given entities.
     *
     * @param entities
     * @return the saved entities
     * @throws IllegalArgumentException in case the given entity is
     * {@literal null}.
     */
    <S extends T> Iterable<S> saveAll(@Nonnull Iterable<S> entities);


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
     * Retrieves an entity by its id.
     *
     * @param id.
     * @return the Optional of entity with the given id or
     *         Optional.empty if none found
     */
    Optional<T> findOneById(ID id);


    /**
     * Retrieves an entity by its id. Throws {@link NotFoundException}
     * if object not found.
     * @param id a unique identifier of object.
     * @return the requested object.
     * @throws gb.common.exceptions.NotFoundException
     */
    default T findOneByIdOrThrow(ID id) {
        return findOneById(id).orElseThrow(() -> notFound(id));
    }


    /**
     * Retrieves an entity by its id and maps it to an specified type.
     * @param <S> DTO class for mapping.
     * @param id.
     * @param type is a class to mapping.
     * @return the Optional of entity with the given id or
     *         Optional.empty if none found.
     */
    <S> Optional<S> findOneById(ID id, Class<S> type);


    /**
     * Retrieves an entity by its id and version.
     *
     * @param id.
     * @param version of persisted object.
     * @return the Optional of entity with the given id and version
     *         or Optional.empty if none found.
     */
    Optional<T> findOneByIdAndVersion(ID id, short version);


    /**
     * Returns whether an entity with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return true if an entity with the given id exists, {@literal false}
     *         otherwise
     */
    boolean existsById(ID id);


    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    List<T> findAll();


    /**
     * Returns all instances of the type with the given IDs.
     *
     * @param ids
     * @return
     */
    List<T> findAllById(@Nonnull Iterable<ID> ids);


    /**
     * Returns the number of entities available.
     *
     * @return the number of entities
     */
    long count();


    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is
     *         {@literal null}
     */
    void deleteById(ID id);


    /**
     * Deletes a given entity.
     *
     * @param entity
     * @throws IllegalArgumentException in case the given entity is
     *         {@literal null}
     */
    void delete(@Nonnull T entity);


    /**
     * Deletes the given entities.
     *
     * @param entities
     * @throws IllegalArgumentException in case the given {@link Iterable}
     *         is {@literal null}
     */
    void deleteInBatch(@Nonnull Iterable<? extends T> entities);


    /**
     * Deletes all entities managed by the repository.
     */
    void deleteAll();


    /**
     * Returns all entities sorted by the given options.
     *
     * @param sort
     * @return all entities sorted by the given options
     */
    List<T> findAll(@Nonnull Sort sort);


    /**
     * Returns a {@link Page} of entities meeting the paging restriction
     * provided in the {@code Pageable} object.
     *
     * @param pageable
     * @return a page of entities
     */
    Page<T> findAll(@Nonnull Pageable pageable);
}
