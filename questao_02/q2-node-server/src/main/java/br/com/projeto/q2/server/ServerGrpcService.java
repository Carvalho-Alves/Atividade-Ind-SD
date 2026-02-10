package br.com.projeto.q2.server;

import br.com.projeto.q2.proto.server.ProcessRequest;
import br.com.projeto.q2.proto.server.ProcessResponse;
import br.com.projeto.q2.proto.server.ServerServiceGrpc;
import io.grpc.stub.StreamObserver;
import java.time.Instant;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * Implementação do serviço gRPC do nó Server (Questão 02).
 *
 * <p>Papel: processar mensagens encaminhadas pelo Receiver e devolver um resultado textual.
 */
@GrpcService
public class ServerGrpcService extends ServerServiceGrpc.ServerServiceImplBase {

  /**
   * Processa a mensagem e devolve um resultado determinístico/explicativo.
   *
   * @param request request contendo id e payload.
   * @param responseObserver observer para retornar o resultado.
   */
  @Override
  public void process(ProcessRequest request, StreamObserver<ProcessResponse> responseObserver) {
    String result = "processado=" + request.getPayload() + ", em=" + Instant.now();
    responseObserver.onNext(ProcessResponse.newBuilder().setResult(result).build());
    responseObserver.onCompleted();
  }
}
