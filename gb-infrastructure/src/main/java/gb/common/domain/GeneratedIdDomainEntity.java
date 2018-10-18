package gb.common.domain;

import static javax.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.PROTECTED;

import javax.persistence.GeneratedValue;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import lombok.Getter;
import lombok.val;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;


@Getter
@FieldNameConstants
@MappedSuperclass
@FieldDefaults(level=PROTECTED)
public abstract class
GeneratedIdDomainEntity<A extends BaseAggregateRoot<A>>
extends BaseAggregateRoot<A> {
    private static final long serialVersionUID = 1L;


    @GeneratedValue(strategy=SEQUENCE, generator="hbn_seq")
    @SequenceGenerator(name="hbn_seq", allocationSize=30)
    Long id;

    @Version
    Short version;


    @Override
    public boolean equals(final Object o) {
        if(this == o) {
            return true;
        }

        if(!(o instanceof GeneratedIdDomainEntity)) { // NOSONAR
            return false;
        }

        @SuppressWarnings("unchecked")
        val other = (GeneratedIdDomainEntity<A>) o;

        return id != null && id.equals(other.id);
    }


    @Override
    public int hashCode() {
        return 31;
    }
}
