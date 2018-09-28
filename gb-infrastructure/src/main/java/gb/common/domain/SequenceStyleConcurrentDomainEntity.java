package gb.common.domain;

import static lombok.AccessLevel.PROTECTED;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Getter;
import lombok.val;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;


@Getter
@FieldNameConstants
@MappedSuperclass
@FieldDefaults(level=PROTECTED)
public abstract class
SequenceStyleConcurrentDomainEntity<A extends BaseAggregateRoot<A>>
extends BaseAggregateRoot<A> {
    private static final long serialVersionUID = 1L;


    @Id
    @GenericGenerator(
            name="hbn_seq",
            strategy="org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters={
                    @Parameter(name="sequence_name",
                            value="hibernate_sequence"),
                    @Parameter(name="increment_size", value="30")
            }
    )
    @GeneratedValue(generator="hbn_seq")
    Long id;

    @Version
    Short version;


    @Override
    public boolean equals(final Object o) {
        if(this == o) {
            return true;
        }

        if(!(o instanceof SequenceStyleConcurrentDomainEntity)) { // NOSONAR
            return false;
        }

        @SuppressWarnings("unchecked")
        val other = (SequenceStyleConcurrentDomainEntity<A>) o;

        return id != null && id.equals(other.id);
    }


    @Override
    public int hashCode() {
        return 31;
    }
}
