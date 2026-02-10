# Questão 03 (gRPC Push via Server-Streaming)

Objetivo: trocar o paradigma de **Pulling** (Receiver faz polling) por **Push Notification**, usando **gRPC Server Streaming**.

Nesta Q3:
- O `client` envia mensagens para o `sender` via `SendMessage`.
- O `receiver` abre um stream em `sender.Subscribe()` e fica escutando.
- Assim que o `sender` recebe uma mensagem (ou identifica backlog no banco), ele **empurra** (`onNext`) para o `receiver` imediatamente.
- O `receiver` processa chamando o `server.Process()` e só então confirma no `sender` com **ACK**. Em falha, envia **NACK**.

## Por que Push reduz o atraso do Pulling?

No Pulling, o Receiver só “vê” mensagens em janelas de tempo (por exemplo, a cada 2s). Isso introduz **latência artificial**: mesmo que a mensagem chegue no Sender agora, ela só será processada no próximo ciclo.

No Push (streaming), o Receiver já está conectado e o Sender pode entregar **imediatamente** quando a mensagem entra, reduzindo o atraso para ~tempo de rede/execução (sem esperar o próximo poll).

## Análise de falhas (o que acontece se cair?)

### Se o Server cair (q3-node-server fora do ar)

- O Receiver tenta chamar `server.Process()`.
- Se der exceção gRPC, o Receiver faz algumas **retentativas locais** (backoff curto).
- Persistindo a falha, o Receiver envia **NACK** ao Sender e **não confirma** a mensagem.
- Como o mailbox é persistido em Postgres com leasing, a mensagem volta para `NEW` (ou expira o lease) e será entregue novamente quando o Server voltar.

Efeito: **entrega “at-least-once”** com retentativa e sem perda.

### Se o Client cair

- Não surgem novas mensagens.
- O Receiver continua com a inscrição aberta (ou tenta reconectar se o Sender cair).
- As mensagens já persistidas no banco continuam sendo entregues normalmente.

## Como rodar (Docker Compose)

Dentro da pasta `questao_03`:

```bash
docker compose up -d --build
```

Portas:
- Postgres exposto em `localhost:5434`
- Sender gRPC em `localhost:9092` (interno 9090)
- Server gRPC em `localhost:9093` (interno 9091)

O container `client` envia uma mensagem ao subir; o `receiver` assina (`Subscribe`) e processa via push.
