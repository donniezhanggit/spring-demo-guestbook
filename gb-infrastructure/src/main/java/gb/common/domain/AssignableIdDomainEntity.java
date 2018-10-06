package gb.common.domain;

import static lombok.AccessLevel.PROTECTED;

import java.util.UUID;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;


@Getter
@FieldNameConstants
@MappedSuperclass
@FieldDefaults(level=PROTECTED)
@EqualsAndHashCode(of="id", callSuper=false)
public abstract class
AssignableIdDomainEntity<A extends BaseAggregateRoot<A>>
extends BaseAggregateRoot<A> {
    private static final long serialVersionUID = 1L;


    @Id
    UUID id;

    @Version
    Short version;
}
