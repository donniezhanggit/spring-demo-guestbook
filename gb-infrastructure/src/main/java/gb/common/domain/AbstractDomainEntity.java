package gb.common.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.hibernate.annotations.*;
//import org.springframework.data.domain.AbstractAggregateRoot;


@MappedSuperclass
public abstract class AbstractDomainEntity
implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String ID_PROPERTY = "id";
    public static final String VERSION_PROPERTY = "version";

    @Id
    @GenericGenerator(
            name="hbn_seq",
            strategy="org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters={
                    @Parameter(name="sequence_name",
                            value="hibernate_sequence"),
                    @Parameter(name="increment_size", value="1")
            }
    )
    @GeneratedValue(generator="hbn_seq")
    protected Long id;

    @Version
    protected Short version;


    protected AbstractDomainEntity() {}

    public Long getId() {
        return id;
    }

    public Short getVersion() {
        return version;
    }
}
