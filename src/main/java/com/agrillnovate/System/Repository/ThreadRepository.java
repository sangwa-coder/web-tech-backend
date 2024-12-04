package com.agrillnovate.System.Repository;

import com.agrillnovate.System.model.Thread;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThreadRepository extends JpaRepository<Thread, Long> {
    List<Thread> findAll();
}