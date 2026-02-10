# Questão 01 — Nós em Docker (multi-módulo)

Este diretório contém a entrega da **Questão 01** em arquitetura **Maven multi-módulo**, com **4 nós executáveis** isolados (client/sender/receiver/server) e um módulo `q1-shared` contendo apenas contratos/DTOs.

## Pré-requisitos

- Docker + Docker Compose

## Como executar

A partir da pasta `questao_01`:

```bash
docker compose up --build
```

Para rodar em background:

```bash
docker compose up --build -d
```

Para derrubar:

```bash
docker compose down
```

Para derrubar e remover volume do banco (reset total do estado):

```bash
docker compose down -v
```

Se a porta `5432` já estiver em uso na sua máquina (Postgres local ou outro compose), altere o mapeamento em `docker-compose.yml` (ex.: `"5433:5432"`).

## Portas e serviços

- **client**: http://localhost:8080
- **sender**: http://localhost:8081
- **receiver**: http://localhost:8082
- **server**: http://localhost:8083
- **postgres**: localhost:5432 (imagem `postgres:15-alpine`; DB `atividade_ind_sd`, user/pass `atividade`)

Health-check (quando disponível pelo Actuator):

- http://localhost:8080/actuator/health
- http://localhost:8081/actuator/health
- http://localhost:8082/actuator/health
- http://localhost:8083/actuator/health

## Smoke test (Q1)

1) Envie uma mensagem via ClientApp (retorna **202** imediatamente):

```bash
curl -i -X POST "http://localhost:8080/q1/client/send" \
  -H "Content-Type: application/json" \
  -d '{"payload":"ola mundo"}'
```

PowerShell (Windows):

```powershell
$uri = "http://localhost:8080/q1/client/send"
$body = '{"payload":"ola mundo"}'
Invoke-WebRequest -Method Post -Uri $uri -ContentType "application/json" -Body $body
```

2) O Receiver faz **pulling** periódico no Sender e encaminha para o ServerApp.

Logs úteis:

```bash
docker compose logs -f receiver
```

## Decisões Arquiteturais

### Duplicação intencional de `PostgresMessageStore`

A classe `PostgresMessageStore` (e o conjunto mínimo de classes de infraestrutura relacionadas, como `MessageStore` e configuração de datasource/Flyway) foi **duplicada intencionalmente** dentro dos módulos de nó (ex.: `q1-node-sender` e `q1-node-receiver`).

Motivo:

- O enunciado exige **isolamento estrito** entre os nós.
- O módulo `q1-shared` foi mantido **estritamente** com contratos/DTOs (sem infraestrutura de persistência, sem wiring Spring, sem implementação de store), evitando o acoplamento indevido.
- Com isso, cada nó mantém sua própria infraestrutura local, mesmo que haja repetição de código — o que é aceitável neste contexto acadêmico por atender explicitamente a restrição do enunciado.
