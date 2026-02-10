-- Tabela unica de mensagens para Q1/Q2/Q3.
-- A coluna channel separa logicamente os fluxos (ex: Q1_REST, Q2_GRPC, Q3_PUSH).

CREATE TABLE IF NOT EXISTS messages (
  id UUID PRIMARY KEY,
  channel VARCHAR(32) NOT NULL,
  payload TEXT NOT NULL,
  status VARCHAR(16) NOT NULL,
  attempt_count INT NOT NULL DEFAULT 0,
  lock_owner VARCHAR(64),
  locked_until TIMESTAMPTZ,
  last_error TEXT,
  created_at TIMESTAMPTZ NOT NULL,
  updated_at TIMESTAMPTZ NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_messages_channel_status_created
  ON messages (channel, status, created_at);

CREATE INDEX IF NOT EXISTS idx_messages_channel_locked_until
  ON messages (channel, locked_until);

-- Resultados/idempotencia do processamento (Q3): garante que retries nao gerem efeito duplicado.
CREATE TABLE IF NOT EXISTS processing_results (
  message_id UUID PRIMARY KEY,
  channel VARCHAR(32) NOT NULL,
  processed_at TIMESTAMPTZ NOT NULL,
  result TEXT NOT NULL
);
