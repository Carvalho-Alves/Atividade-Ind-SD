# Atividade-Ind-SD

Atividade de Sistemas Distribuídos com foco em **comunicação indireta**, **assincronicidade** e **garantia de entrega**.

Este repositório contém **três entregas independentes**, cada uma com seu próprio Docker Compose:

- **Questão 01**: REST + Pulling (Receiver faz polling)
- **Questão 02**: gRPC + Protobuf (pipeline assíncrono)
- **Questão 03**: gRPC + Server-Streaming (push) + idempotência (deduplicação)

## Pré-requisitos

- Docker Desktop instalado e rodando
- Docker Compose disponível via `docker compose`

Observação (Windows/PowerShell): prefira `Invoke-RestMethod` (ou `curl.exe`).

## Como testar tudo (roteiro completo)

Recomendação: **execute uma questão por vez** (para evitar conflito de portas) e ao final derrube os containers com `down`.

### 1) Questão 01 (REST)

Subir:

```powershell
docker compose -f questao_01/docker-compose.yml up -d --build
```

Checar saúde (Actuator):

```powershell
$urls = @(
  'http://localhost:8080/actuator/health',
  'http://localhost:8081/actuator/health',
  'http://localhost:8082/actuator/health',
  'http://localhost:8083/actuator/health'
)
foreach ($u in $urls) {
  $ok = $false
  for ($i = 1; $i -le 30 -and -not $ok; $i++) {
    try {
      (Invoke-RestMethod -TimeoutSec 2 -Uri $u).status | ForEach-Object { "$u -> $_" }
      $ok = $true
    } catch {
      Start-Sleep -Seconds 1
    }
  }
  if (-not $ok) { "$u -> FALHA" }
}
```

Enviar mensagem (retorna **202** imediatamente):

```powershell
Invoke-RestMethod -Method Post -Uri "http://localhost:8080/q1/client/send" -ContentType "application/json" -Body '{"payload":"hello-q1"}'
```

Ver evidência (logs do receiver):

```powershell
docker compose -f questao_01/docker-compose.yml logs --tail=200 receiver
```

Parar:

```powershell
docker compose -f questao_01/docker-compose.yml down
```

Reset total (remove volume do banco da Q1):

```powershell
docker compose -f questao_01/docker-compose.yml down -v
```

Portas (host):

- client: http://localhost:8080
- sender: http://localhost:8081
- receiver: http://localhost:8082
- server: http://localhost:8083
- postgres: localhost:5432

### 2) Questão 02 (gRPC)

Subir:

```powershell
docker compose -f questao_02/docker-compose.yml up -d --build
```

O serviço `client` envia uma mensagem ao subir. Para enviar uma mensagem customizada sem editar arquivos, rode o client sob demanda:

```powershell
docker compose -f questao_02/docker-compose.yml run --rm -e APP_CLIENT_PAYLOAD="hello-q2" client
```

Ver evidência (logs do receiver):

```powershell
docker compose -f questao_02/docker-compose.yml logs --tail=200 receiver
```

Parar:

```powershell
docker compose -f questao_02/docker-compose.yml down
```

Reset total:

```powershell
docker compose -f questao_02/docker-compose.yml down -v
```

Portas (host):

- postgres: localhost:5433
- sender gRPC: localhost:9090
- server gRPC: localhost:9091

### 3) Questão 03 (gRPC push + deduplicação)

Subir:

```powershell
docker compose -f questao_03/docker-compose.yml up -d --build
```

Enviar mensagem normal (client sob demanda):

```powershell
docker compose -f questao_03/docker-compose.yml run --rm -e APP_CLIENT_PAYLOAD="hello-q3" client
```

Demonstrar deduplicação (falha após persistir resultado; depois sucesso com dedup):

```powershell
docker compose -f questao_03/docker-compose.yml run --rm -e APP_CLIENT_PAYLOAD="demo CRASH_AFTER_SAVE" client
```

Demonstrar retentativas (falha antes de persistir; tende a repetir até exceder tentativas):

```powershell
docker compose -f questao_03/docker-compose.yml run --rm -e APP_CLIENT_PAYLOAD="demo CRASH" client
```

Ver evidência (logs do receiver):

```powershell
docker compose -f questao_03/docker-compose.yml logs -f receiver
```

Parar:

```powershell
docker compose -f questao_03/docker-compose.yml down
```

Reset total:

```powershell
docker compose -f questao_03/docker-compose.yml down -v
```

Portas (host):

- postgres: localhost:5434
- sender gRPC: localhost:9092
- server gRPC: localhost:9093

## Consultar o banco (opcional, para prova)

Cada questão usa um banco diferente:

- Q1: `atividade_ind_sd` (porta 5432)
- Q2: `atividade_ind_sd_q2` (porta 5433)
- Q3: `atividade_ind_sd_q3` (porta 5434)

Tabelas:

- `messages`: mailbox com lease, tentativas e estado
- `processing_results`: resultado de processamento (idempotência)

Exemplo (Q1):

```powershell
docker compose -f questao_01/docker-compose.yml exec postgres psql -U atividade -d atividade_ind_sd -c "select id, channel, status, attempt_count, lock_owner, locked_until, last_error, created_at from messages order by created_at desc limit 20;"
docker compose -f questao_01/docker-compose.yml exec postgres psql -U atividade -d atividade_ind_sd -c "select message_id, channel, processed_at, result from processing_results order by processed_at desc limit 20;"
```
curl -sS -X POST "http://localhost:8084/q1/client/send" \
	-H "Content-Type: application/json" \
	-d '{"payload":"hello-q1"}'
```

Q2:

```bash
curl -sS -X POST "http://localhost:8084/q2/client/send" \
	-H "Content-Type: application/json" \
	-d '{"payload":"hello-q2"}'
```

Q3:

```bash
curl -sS -X POST "http://localhost:8084/q3/client/send" \
	-H "Content-Type: application/json" \
	-d '{"payload":"hello-q3"}'
```

Q3 (dedup real):

```bash
curl -sS -X POST "http://localhost:8084/q3/client/send" \
	-H "Content-Type: application/json" \
	-d '{"payload":"demo CRASH_AFTER_SAVE"}'
```

## Estrutura (packages)

- `br.com.projeto.q1`: REST (Client/Sender/Receiver/ServerApp)
- `br.com.projeto.q2`: gRPC (Client/Sender/Receiver/ServerApp)
- `br.com.projeto.q3`: Q3 (push subscriber + receiver poller no canal `Q3_PUSH`)
- `br.com.projeto.shared`: mailbox (Postgres), leasing/retries, config e idempotência

## Troubleshooting

- **Health não sobe:** rode `docker compose logs --tail=200 sender` (ou `receiver/serverapp/clientapp`) e verifique erro de conexão com Postgres.
- **Porta em uso:** altere o mapeamento de portas no `docker-compose.yml` (ou feche o processo que está usando a porta).
- **Erros gRPC UNAVAILABLE no startup:** podem ocorrer enquanto Sender/ServerApp ainda estão iniciando; após os health endpoints ficarem UP, o sistema estabiliza.