# Questão 02 (gRPC + Protobuf)

Esta questão implementa o mesmo fluxo lógico da Q1 (Client -> Sender -> Receiver -> Server), porém usando **gRPC**.

## Diferença principal (REST vs gRPC)

- **REST (Q1)**: comunicação via HTTP/JSON, tipagem fraca no contrato (campos em JSON), maior overhead de payload e de parsing, e normalmente mais fricção para chamadas internas de baixa latência.
- **gRPC (Q2)**: contrato **fortemente tipado** via `.proto`, serialização binária (Protobuf) e chamadas RPC com stubs gerados (menos boilerplate e payload menor). Em contrapartida, exige geração de stubs e configuração de canais/ports, e costuma ser mais “opinionated” para debugging com ferramentas HTTP tradicionais.

## Como rodar (Docker Compose)

Dentro da pasta `questao_02`:

```bash
docker compose up --build
```

Portas:
- Postgres exposto em `localhost:5433`
- Sender gRPC em `localhost:9090`
- Server gRPC em `localhost:9091`

O container `client` envia uma mensagem ao subir e o `receiver` faz polling via gRPC.
