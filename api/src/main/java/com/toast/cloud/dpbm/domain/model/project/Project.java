package com.toast.cloud.dpbm.domain.model.project;

import com.toast.cloud.common.jpa.enitty.AbstractBaseEntity;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Table(name = "dpbm_project")
@Entity
public class Project extends AbstractBaseEntity<String> {

    private String doorayModuleTagPrefixId;
    private String doorayWorkingTypeTagPrefixId;
    private String doorayMandaysTagPrefixId;
    private String department;

}
