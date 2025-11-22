package com.speakweb.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.speakweb.model.entity.BGroup;
import com.speakweb.model.entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

	List<Event> findByGroup(BGroup group);
	
}
