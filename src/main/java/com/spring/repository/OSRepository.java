package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.domain.OS;

@Repository
public interface OSRepository extends JpaRepository<OS, Long>{

}
