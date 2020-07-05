package com.toast.cloud.common.jpa.enitty;

import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;

@EqualsAndHashCode(of = "seq")
@MappedSuperclass
public abstract class AbstractBaseSeqEntity implements Serializable, Persistable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;
    @Transient
    private boolean isNew = false;

    @Override
    public Long getId() {
        return seq;
    }

    @Transient
    @Override
    public boolean isNew() {
        return isNew;
    }

    protected void generate() {
        this.isNew = true;
    }

}
