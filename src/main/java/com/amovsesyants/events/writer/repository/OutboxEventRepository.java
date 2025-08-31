package com.amovsesyants.events.writer.repository;

import com.amovsesyants.events.writer.entity.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, Long> {
}
