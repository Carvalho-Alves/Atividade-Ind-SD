package br.com.projeto.q2.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * ServerApp gRPC: processa mensagens encaminhadas pelo Receiver.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.68.1)",
    comments = "Source: serverapp.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ServerAppGrpcGrpc {

  private ServerAppGrpcGrpc() {}

  public static final java.lang.String SERVICE_NAME = "atividade.ServerAppGrpc";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<br.com.projeto.q2.grpc.ProcessRequest,
      br.com.projeto.q2.grpc.ProcessResponse> getProcessMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Process",
      requestType = br.com.projeto.q2.grpc.ProcessRequest.class,
      responseType = br.com.projeto.q2.grpc.ProcessResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<br.com.projeto.q2.grpc.ProcessRequest,
      br.com.projeto.q2.grpc.ProcessResponse> getProcessMethod() {
    io.grpc.MethodDescriptor<br.com.projeto.q2.grpc.ProcessRequest, br.com.projeto.q2.grpc.ProcessResponse> getProcessMethod;
    if ((getProcessMethod = ServerAppGrpcGrpc.getProcessMethod) == null) {
      synchronized (ServerAppGrpcGrpc.class) {
        if ((getProcessMethod = ServerAppGrpcGrpc.getProcessMethod) == null) {
          ServerAppGrpcGrpc.getProcessMethod = getProcessMethod =
              io.grpc.MethodDescriptor.<br.com.projeto.q2.grpc.ProcessRequest, br.com.projeto.q2.grpc.ProcessResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Process"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.projeto.q2.grpc.ProcessRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.projeto.q2.grpc.ProcessResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ServerAppGrpcMethodDescriptorSupplier("Process"))
              .build();
        }
      }
    }
    return getProcessMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ServerAppGrpcStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ServerAppGrpcStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ServerAppGrpcStub>() {
        @java.lang.Override
        public ServerAppGrpcStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ServerAppGrpcStub(channel, callOptions);
        }
      };
    return ServerAppGrpcStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ServerAppGrpcBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ServerAppGrpcBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ServerAppGrpcBlockingStub>() {
        @java.lang.Override
        public ServerAppGrpcBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ServerAppGrpcBlockingStub(channel, callOptions);
        }
      };
    return ServerAppGrpcBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ServerAppGrpcFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ServerAppGrpcFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ServerAppGrpcFutureStub>() {
        @java.lang.Override
        public ServerAppGrpcFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ServerAppGrpcFutureStub(channel, callOptions);
        }
      };
    return ServerAppGrpcFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * ServerApp gRPC: processa mensagens encaminhadas pelo Receiver.
   * </pre>
   */
  public interface AsyncService {

    /**
     */
    default void process(br.com.projeto.q2.grpc.ProcessRequest request,
        io.grpc.stub.StreamObserver<br.com.projeto.q2.grpc.ProcessResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getProcessMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service ServerAppGrpc.
   * <pre>
   * ServerApp gRPC: processa mensagens encaminhadas pelo Receiver.
   * </pre>
   */
  public static abstract class ServerAppGrpcImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return ServerAppGrpcGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service ServerAppGrpc.
   * <pre>
   * ServerApp gRPC: processa mensagens encaminhadas pelo Receiver.
   * </pre>
   */
  public static final class ServerAppGrpcStub
      extends io.grpc.stub.AbstractAsyncStub<ServerAppGrpcStub> {
    private ServerAppGrpcStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServerAppGrpcStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ServerAppGrpcStub(channel, callOptions);
    }

    /**
     */
    public void process(br.com.projeto.q2.grpc.ProcessRequest request,
        io.grpc.stub.StreamObserver<br.com.projeto.q2.grpc.ProcessResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getProcessMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service ServerAppGrpc.
   * <pre>
   * ServerApp gRPC: processa mensagens encaminhadas pelo Receiver.
   * </pre>
   */
  public static final class ServerAppGrpcBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<ServerAppGrpcBlockingStub> {
    private ServerAppGrpcBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServerAppGrpcBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ServerAppGrpcBlockingStub(channel, callOptions);
    }

    /**
     */
    public br.com.projeto.q2.grpc.ProcessResponse process(br.com.projeto.q2.grpc.ProcessRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getProcessMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service ServerAppGrpc.
   * <pre>
   * ServerApp gRPC: processa mensagens encaminhadas pelo Receiver.
   * </pre>
   */
  public static final class ServerAppGrpcFutureStub
      extends io.grpc.stub.AbstractFutureStub<ServerAppGrpcFutureStub> {
    private ServerAppGrpcFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServerAppGrpcFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ServerAppGrpcFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<br.com.projeto.q2.grpc.ProcessResponse> process(
        br.com.projeto.q2.grpc.ProcessRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getProcessMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_PROCESS = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_PROCESS:
          serviceImpl.process((br.com.projeto.q2.grpc.ProcessRequest) request,
              (io.grpc.stub.StreamObserver<br.com.projeto.q2.grpc.ProcessResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getProcessMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              br.com.projeto.q2.grpc.ProcessRequest,
              br.com.projeto.q2.grpc.ProcessResponse>(
                service, METHODID_PROCESS)))
        .build();
  }

  private static abstract class ServerAppGrpcBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ServerAppGrpcBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return br.com.projeto.q2.grpc.Serverapp.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ServerAppGrpc");
    }
  }

  private static final class ServerAppGrpcFileDescriptorSupplier
      extends ServerAppGrpcBaseDescriptorSupplier {
    ServerAppGrpcFileDescriptorSupplier() {}
  }

  private static final class ServerAppGrpcMethodDescriptorSupplier
      extends ServerAppGrpcBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    ServerAppGrpcMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ServerAppGrpcGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ServerAppGrpcFileDescriptorSupplier())
              .addMethod(getProcessMethod())
              .build();
        }
      }
    }
    return result;
  }
}
