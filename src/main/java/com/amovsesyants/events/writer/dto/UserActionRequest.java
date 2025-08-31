package com.amovsesyants.events.writer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserActionRequest {
    @NotBlank
    private String idempotencyKey;
    @NotNull
    private Long userId;
    @NotBlank
    private String eventType;
    @NotNull
    private Instant eventTimestamp;
    private String payload;
}