package gb.repos;

import static lombok.AccessLevel.PRIVATE;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gb.domain.User;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;


@Transactional
@Repository
@FieldDefaults(level=PRIVATE, makeFinal=true)
public class UsersRepositoryImpl implements UsersRepositoryCustom {
    @NonNull EntityManager em;
    @NonNull ConversionService converter;


    public UsersRepositoryImpl(
            @NonNull final EntityManager em,
            @NonNull @Qualifier("conversionService")
            ConversionService converter) {

        this.em = em;
        this.converter = converter;
    }


    @Override
    public Optional<User> findByUserName(String userName) {
        return getSession()
                .bySimpleNaturalId(User.class)
                .loadOptional(userName);
    }


    @Override
    public <T> Optional<T> findByUserName(String userName, Class<T> type) {
        Optional<User> user = findByUserName(userName);

        return user.map(u -> converter.convert(u, type));
    }


    private Session getSession() {
        return em.unwrap(Session.class);
    }
}
