package com.speakweb.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.speakweb.model.entity.BGroup;

@Repository
public interface GroupRepository extends JpaRepository<BGroup, Integer> {

}