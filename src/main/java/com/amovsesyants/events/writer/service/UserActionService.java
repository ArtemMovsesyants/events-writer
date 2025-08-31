package com.amovsesyants.events.writer.service;

import com.amovsesyants.events.writer.dto.UserActionRequest;
import com.amovsesyants.events.writer.entity.OutboxEvent;
import com.amovsesyants.events.writer.entity.RewardLevel;
import com.amovsesyants.events.writer.entity.User;
import com.amovsesyants.events.writer.repository.OutboxEventRepository;
import com.amovsesyants.events.writer.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserActionService {

    private static final int ACTIVITY_POINTS_INCREMENT = 1;
    private static final int SILVER_THRESHOLD = 50;
    private static final int GOLD_THRESHOLD = 100;
    private static final int INITIAL_ACTIVITY_POINTS = 0;
    private static final RewardLevel INITIAL_REWARD_LEVEL = RewardLevel.BRONZE;

    private final UserRepository userRepository;
    private final OutboxEventRepository outboxEventRepository;

    @Transactional
    public void save(final List<UserActionRequest> requests) {
        List<User> usersToSave = new ArrayList<>();
        List<OutboxEvent> outboxEventsToSave = new ArrayList<>();

        try {
            List<User> users = requests.stream()
                    .map(this::buildUser)
                    .toList();
            List<OutboxEvent> outboxEvents = requests.stream()
                    .map(this::buildOutboxEvent)
                    .toList();

            usersToSave.addAll(users);
            outboxEventsToSave.addAll(outboxEvents);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save user action", e);
        }

        userRepository.saveAll(usersToSave);
        outboxEventRepository.saveAll(outboxEventsToSave);
    }

    private User buildUser(UserActionRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElse(User.builder()
                        .id(request.getUserId())
                        .activityPoints(INITIAL_ACTIVITY_POINTS)
                        .rewardLevel(INITIAL_REWARD_LEVEL)
                        .build());

        user.setActivityPoints(user.getActivityPoints() + ACTIVITY_POINTS_INCREMENT);

        if (user.getActivityPoints() >= GOLD_THRESHOLD) {
            user.setRewardLevel(RewardLevel.GOLD);
        } else if (user.getActivityPoints() >= SILVER_THRESHOLD) {
            user.setRewardLevel(RewardLevel.SILVER);
        }

        return user;
    }

    private OutboxEvent buildOutboxEvent(UserActionRequest request) {
        return OutboxEvent.builder()
                .idempotencyKey(UUID.fromString(request.getIdempotencyKey()))
                .userId(request.getUserId())
                .eventType(request.getEventType())
                .eventTimestamp(request.getEventTimestamp())
                .payloadJson(request.getPayload())
                .processed(false)
                .build();
    }
}
