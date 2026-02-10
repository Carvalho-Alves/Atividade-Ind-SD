package br.com.projeto.shared.messaging;

import java.time.Instant;
import java.util.UUID;

/**
 * Representa uma mensagem que foi "alugada" (leased) por um Receiver.
 */
public record LeasedMessage(
    UUID id,
    MessageChannel channel,
    String payload,
    int attemptCount,
    String lockOwner,
    Instant lockedUntil
) {}
