package com.jhh.core.domain.user.repository;

import com.jhh.core.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserRepository extends JpaRepository<User, Integer>, QuerydslPredicateExecutor<User> {
}
