package demo.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;


@MappedSuperclass
public class DomainEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String ID_PROPERTY = "id";
    public static final String VERSION_PROPERTY = "version";

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hbn_seq")
    @SequenceGenerator(name="hbn_seq", 
        sequenceName="hibernate_sequence",
        allocationSize=1)
    protected Long id;

    @Version
    protected Short version;


    protected DomainEntity() {}

    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public Short getVersion() {
        return version;
    }

    protected void setVersion(Short version) {
        this.version = version;
    }
}
