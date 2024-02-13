package com.sparta.duopleaseduo.repository;

import com.sparta.duopleaseduo.entity.RiotUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiotUserRepository extends JpaRepository<RiotUser, Long> {
}
