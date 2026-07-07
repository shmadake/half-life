package org.codevil.halflife.controller;

import lombok.RequiredArgsConstructor;
import org.codevil.halflife.dto.CreateTopicRequest;
import org.codevil.halflife.dto.TopicResponse;
import org.codevil.halflife.service.TopicService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @PostMapping
    public TopicResponse createTopic(@RequestBody CreateTopicRequest request) {
        Long userId = getCurrentUserId();
        return topicService.createTopic(userId, request);
    }

    @GetMapping
    public List<TopicResponse> getAllTopics() {
        Long userId = getCurrentUserId();
        return topicService.getAllTopics(userId);
    }

    @PutMapping("/{id}/revise")
    public TopicResponse reviseTopic(@PathVariable Long id) {
        Long userId = getCurrentUserId();
        return topicService.reviseTopic(userId, id);
    }

    @DeleteMapping("/{id}")
    public void deleteTopic(@PathVariable Long id) {
        Long userId = getCurrentUserId();
        topicService.deleteTopic(userId, id);
    }

    private Long getCurrentUserId() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new RuntimeException("No authenticated user found");
        }
        return (Long) authentication.getPrincipal();
    }
}