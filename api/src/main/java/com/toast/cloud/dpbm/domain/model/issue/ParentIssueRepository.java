package com.toast.cloud.dpbm.domain.model.issue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ParentIssueRepository
    extends JpaRepository<ParentIssue, String>, QuerydslPredicateExecutor<ParentIssue>, ParentIssueRepositoryCustom {

}
