package com.toast.cloud.common.jpa.enitty;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;

@EqualsAndHashCode(of = "id")
@MappedSuperclass
public abstract class AbstractBaseEntity<PK extends Serializable> implements Persistable<PK> {

    @Setter(AccessLevel.PROTECTED)
    @Id
    private PK id;
    @Transient
    private boolean isNew = false;

    @Override
    public PK getId() {
        return id;
    }

    @Transient
    @Override
    public boolean isNew() {
        return isNew;
    }

    protected void generateId(PK id) {
        this.id = id;
        this.isNew = true;
    }

}
