package br.com.projeto.shared.messaging;

import java.time.Instant;
import java.util.UUID;

public record LeasedMessage(
    UUID id,
    MessageChannel channel,
    String payload,
    int attemptCount,
    String lockOwner,
    Instant lockedUntil
) {}
