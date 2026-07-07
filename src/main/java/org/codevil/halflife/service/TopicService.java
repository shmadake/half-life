package org.codevil.halflife.service;

import lombok.RequiredArgsConstructor;
import org.codevil.halflife.dto.CreateTopicRequest;
import org.codevil.halflife.dto.TopicResponse;
import org.codevil.halflife.entity.Topic;
import org.codevil.halflife.exception
        .TopicNotFoundException;
import org.codevil.halflife.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;

    private static final int DEFAULT_INTERVAL_DAYS = 1;

    public TopicResponse createTopic(Long userId, CreateTopicRequest request) {

        Topic topic = new Topic();
        topic.setUserId(userId);
        topic.setTitle(request.getTitle());
        topic.setCategory(request.getCategory());
        topic.setResourceLink(request.getResourceLink());

        LocalDate dateLearned = request.getDateLearned() != null
                ? request.getDateLearned()
                : LocalDate.now();

        topic.setDateLearned(dateLearned);
        topic.setLastRevisedDate(dateLearned);
        topic.setIntervalDays(DEFAULT_INTERVAL_DAYS);
        topic.setNextRevisionDate(dateLearned.plusDays(DEFAULT_INTERVAL_DAYS));

        topicRepository.save(topic);

        return toResponse(topic);
    }

    public List<TopicResponse> getAllTopics(Long userId) {
        return topicRepository.findByUserIdOrderByNextRevisionDateAsc(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public TopicResponse reviseTopic(Long userId, Long topicId) {

        Topic topic = topicRepository.findByIdAndUserId(topicId, userId)
                .orElseThrow(() -> new TopicNotFoundException("Topic not found"));

        int newInterval = topic.getIntervalDays() * 2;

        topic.setLastRevisedDate(LocalDate.now());
        topic.setIntervalDays(newInterval);
        topic.setNextRevisionDate(LocalDate.now().plusDays(newInterval));

        topicRepository.save(topic);

        return toResponse(topic);
    }

    public void deleteTopic(Long userId, Long topicId) {
        Topic topic = topicRepository.findByIdAndUserId(topicId, userId)
                .orElseThrow(() -> new TopicNotFoundException("Topic not found"));

        topicRepository.delete(topic);
    }

    private TopicResponse toResponse(Topic topic) {
        return new TopicResponse(
                topic.getId(),
                topic.getTitle(),
                topic.getCategory(),
                topic.getResourceLink(),
                topic.getDateLearned(),
                topic.getLastRevisedDate(),
                topic.getIntervalDays(),
                topic.getNextRevisionDate()
        );
    }
}