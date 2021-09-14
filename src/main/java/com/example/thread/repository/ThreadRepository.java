package com.example.thread.repository;

import java.util.List;
import java.util.Optional;

import com.example.thread.model.Thread;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThreadRepository extends JpaRepository<Thread, Long> {
    Optional<Thread> findById(long id);
    List<Thread> findByCardId(long cardId);
    boolean existsById(long id);
}
