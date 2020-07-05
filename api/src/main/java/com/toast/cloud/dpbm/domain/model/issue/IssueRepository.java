package com.toast.cloud.dpbm.domain.model.issue;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, String> {
}
