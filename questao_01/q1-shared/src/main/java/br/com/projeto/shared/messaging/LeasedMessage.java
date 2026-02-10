package br.com.projeto.shared.messaging;

import java.time.Instant;
import java.util.UUID;

/**
 * Representa uma mensagem que foi "alugada" (posse temporária / lease) por um Receiver.
 *
 * Papel: carregar os dados necessarios para o Receiver processar e depois dar ACK.
 */
public record LeasedMessage(
    /** Identificador único da mensagem no mailbox (chave primária no Postgres). */
    UUID id,

    /** Canal lógico ao qual a mensagem pertence (separa Q1/Q2/Q3). */
    MessageChannel channel,

    /** Corpo da mensagem (payload) tal como publicado pelo produtor. */
    String payload,

    /** Quantidade de tentativas já realizadas (usado para retry/DLQ). */
    int attemptCount,

    /** Identificador do Receiver que detém o lease atual (dono do lock). */
    String lockOwner,

    /** Instante até o qual o lease é válido (após isso, volta a ficar elegível). */
    Instant lockedUntil
) {}
