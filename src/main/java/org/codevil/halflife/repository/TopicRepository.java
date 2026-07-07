package org.codevil.halflife.repository;

import org.codevil.halflife.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    List<Topic> findByUserIdOrderByNextRevisionDateAsc(Long userId);

    Optional<Topic> findByIdAndUserId(Long id, Long userId);
}