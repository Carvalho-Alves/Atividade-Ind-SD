package br.com.projeto.q2.proto.sender;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.63.0)",
    comments = "Source: sender.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class SenderServiceGrpc {

  private SenderServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "q2.sender.SenderService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<br.com.projeto.q2.proto.sender.SendMessageRequest,
      br.com.projeto.q2.proto.sender.SendMessageResponse> getSendMessageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SendMessage",
      requestType = br.com.projeto.q2.proto.sender.SendMessageRequest.class,
      responseType = br.com.projeto.q2.proto.sender.SendMessageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<br.com.projeto.q2.proto.sender.SendMessageRequest,
      br.com.projeto.q2.proto.sender.SendMessageResponse> getSendMessageMethod() {
    io.grpc.MethodDescriptor<br.com.projeto.q2.proto.sender.SendMessageRequest, br.com.projeto.q2.proto.sender.SendMessageResponse> getSendMessageMethod;
    if ((getSendMessageMethod = SenderServiceGrpc.getSendMessageMethod) == null) {
      synchronized (SenderServiceGrpc.class) {
        if ((getSendMessageMethod = SenderServiceGrpc.getSendMessageMethod) == null) {
          SenderServiceGrpc.getSendMessageMethod = getSendMessageMethod =
              io.grpc.MethodDescriptor.<br.com.projeto.q2.proto.sender.SendMessageRequest, br.com.projeto.q2.proto.sender.SendMessageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SendMessage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.projeto.q2.proto.sender.SendMessageRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.projeto.q2.proto.sender.SendMessageResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SenderServiceMethodDescriptorSupplier("SendMessage"))
              .build();
        }
      }
    }
    return getSendMessageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<br.com.projeto.q2.proto.sender.PullMessagesRequest,
      br.com.projeto.q2.proto.sender.PullMessagesResponse> getPullMessagesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PullMessages",
      requestType = br.com.projeto.q2.proto.sender.PullMessagesRequest.class,
      responseType = br.com.projeto.q2.proto.sender.PullMessagesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<br.com.projeto.q2.proto.sender.PullMessagesRequest,
      br.com.projeto.q2.proto.sender.PullMessagesResponse> getPullMessagesMethod() {
    io.grpc.MethodDescriptor<br.com.projeto.q2.proto.sender.PullMessagesRequest, br.com.projeto.q2.proto.sender.PullMessagesResponse> getPullMessagesMethod;
    if ((getPullMessagesMethod = SenderServiceGrpc.getPullMessagesMethod) == null) {
      synchronized (SenderServiceGrpc.class) {
        if ((getPullMessagesMethod = SenderServiceGrpc.getPullMessagesMethod) == null) {
          SenderServiceGrpc.getPullMessagesMethod = getPullMessagesMethod =
              io.grpc.MethodDescriptor.<br.com.projeto.q2.proto.sender.PullMessagesRequest, br.com.projeto.q2.proto.sender.PullMessagesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PullMessages"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.projeto.q2.proto.sender.PullMessagesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.projeto.q2.proto.sender.PullMessagesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SenderServiceMethodDescriptorSupplier("PullMessages"))
              .build();
        }
      }
    }
    return getPullMessagesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<br.com.projeto.q2.proto.sender.AckMessageRequest,
      br.com.projeto.q2.proto.sender.AckMessageResponse> getAckMessageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AckMessage",
      requestType = br.com.projeto.q2.proto.sender.AckMessageRequest.class,
      responseType = br.com.projeto.q2.proto.sender.AckMessageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<br.com.projeto.q2.proto.sender.AckMessageRequest,
      br.com.projeto.q2.proto.sender.AckMessageResponse> getAckMessageMethod() {
    io.grpc.MethodDescriptor<br.com.projeto.q2.proto.sender.AckMessageRequest, br.com.projeto.q2.proto.sender.AckMessageResponse> getAckMessageMethod;
    if ((getAckMessageMethod = SenderServiceGrpc.getAckMessageMethod) == null) {
      synchronized (SenderServiceGrpc.class) {
        if ((getAckMessageMethod = SenderServiceGrpc.getAckMessageMethod) == null) {
          SenderServiceGrpc.getAckMessageMethod = getAckMessageMethod =
              io.grpc.MethodDescriptor.<br.com.projeto.q2.proto.sender.AckMessageRequest, br.com.projeto.q2.proto.sender.AckMessageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AckMessage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.projeto.q2.proto.sender.AckMessageRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.projeto.q2.proto.sender.AckMessageResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SenderServiceMethodDescriptorSupplier("AckMessage"))
              .build();
        }
      }
    }
    return getAckMessageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<br.com.projeto.q2.proto.sender.NackMessageRequest,
      br.com.projeto.q2.proto.sender.NackMessageResponse> getNackMessageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "NackMessage",
      requestType = br.com.projeto.q2.proto.sender.NackMessageRequest.class,
      responseType = br.com.projeto.q2.proto.sender.NackMessageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<br.com.projeto.q2.proto.sender.NackMessageRequest,
      br.com.projeto.q2.proto.sender.NackMessageResponse> getNackMessageMethod() {
    io.grpc.MethodDescriptor<br.com.projeto.q2.proto.sender.NackMessageRequest, br.com.projeto.q2.proto.sender.NackMessageResponse> getNackMessageMethod;
    if ((getNackMessageMethod = SenderServiceGrpc.getNackMessageMethod) == null) {
      synchronized (SenderServiceGrpc.class) {
        if ((getNackMessageMethod = SenderServiceGrpc.getNackMessageMethod) == null) {
          SenderServiceGrpc.getNackMessageMethod = getNackMessageMethod =
              io.grpc.MethodDescriptor.<br.com.projeto.q2.proto.sender.NackMessageRequest, br.com.projeto.q2.proto.sender.NackMessageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "NackMessage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.projeto.q2.proto.sender.NackMessageRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.projeto.q2.proto.sender.NackMessageResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SenderServiceMethodDescriptorSupplier("NackMessage"))
              .build();
        }
      }
    }
    return getNackMessageMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SenderServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SenderServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SenderServiceStub>() {
        @java.lang.Override
        public SenderServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SenderServiceStub(channel, callOptions);
        }
      };
    return SenderServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SenderServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SenderServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SenderServiceBlockingStub>() {
        @java.lang.Override
        public SenderServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SenderServiceBlockingStub(channel, callOptions);
        }
      };
    return SenderServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SenderServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SenderServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SenderServiceFutureStub>() {
        @java.lang.Override
        public SenderServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SenderServiceFutureStub(channel, callOptions);
        }
      };
    return SenderServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     * <pre>
     * Cliente -&gt; Sender
     * </pre>
     */
    default void sendMessage(br.com.projeto.q2.proto.sender.SendMessageRequest request,
        io.grpc.stub.StreamObserver<br.com.projeto.q2.proto.sender.SendMessageResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSendMessageMethod(), responseObserver);
    }

    /**
     * <pre>
     * Receptor -&gt; Emissor (pulling / busca)
     * </pre>
     */
    default void pullMessages(br.com.projeto.q2.proto.sender.PullMessagesRequest request,
        io.grpc.stub.StreamObserver<br.com.projeto.q2.proto.sender.PullMessagesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPullMessagesMethod(), responseObserver);
    }

    /**
     * <pre>
     * Receptor -&gt; Emissor (ACK/NACK)
     * </pre>
     */
    default void ackMessage(br.com.projeto.q2.proto.sender.AckMessageRequest request,
        io.grpc.stub.StreamObserver<br.com.projeto.q2.proto.sender.AckMessageResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAckMessageMethod(), responseObserver);
    }

    /**
     */
    default void nackMessage(br.com.projeto.q2.proto.sender.NackMessageRequest request,
        io.grpc.stub.StreamObserver<br.com.projeto.q2.proto.sender.NackMessageResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getNackMessageMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service SenderService.
   */
  public static abstract class SenderServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return SenderServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service SenderService.
   */
  public static final class SenderServiceStub
      extends io.grpc.stub.AbstractAsyncStub<SenderServiceStub> {
    private SenderServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SenderServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SenderServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Cliente -&gt; Sender
     * </pre>
     */
    public void sendMessage(br.com.projeto.q2.proto.sender.SendMessageRequest request,
        io.grpc.stub.StreamObserver<br.com.projeto.q2.proto.sender.SendMessageResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSendMessageMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Receptor -&gt; Emissor (pulling / busca)
     * </pre>
     */
    public void pullMessages(br.com.projeto.q2.proto.sender.PullMessagesRequest request,
        io.grpc.stub.StreamObserver<br.com.projeto.q2.proto.sender.PullMessagesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPullMessagesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Receptor -&gt; Emissor (ACK/NACK)
     * </pre>
     */
    public void ackMessage(br.com.projeto.q2.proto.sender.AckMessageRequest request,
        io.grpc.stub.StreamObserver<br.com.projeto.q2.proto.sender.AckMessageResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAckMessageMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void nackMessage(br.com.projeto.q2.proto.sender.NackMessageRequest request,
        io.grpc.stub.StreamObserver<br.com.projeto.q2.proto.sender.NackMessageResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getNackMessageMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service SenderService.
   */
  public static final class SenderServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<SenderServiceBlockingStub> {
    private SenderServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SenderServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SenderServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Cliente -&gt; Sender
     * </pre>
     */
    public br.com.projeto.q2.proto.sender.SendMessageResponse sendMessage(br.com.projeto.q2.proto.sender.SendMessageRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSendMessageMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Receptor -&gt; Emissor (pulling / busca)
     * </pre>
     */
    public br.com.projeto.q2.proto.sender.PullMessagesResponse pullMessages(br.com.projeto.q2.proto.sender.PullMessagesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPullMessagesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Receptor -&gt; Emissor (ACK/NACK)
     * </pre>
     */
    public br.com.projeto.q2.proto.sender.AckMessageResponse ackMessage(br.com.projeto.q2.proto.sender.AckMessageRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAckMessageMethod(), getCallOptions(), request);
    }

    /**
     */
    public br.com.projeto.q2.proto.sender.NackMessageResponse nackMessage(br.com.projeto.q2.proto.sender.NackMessageRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getNackMessageMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service SenderService.
   */
  public static final class SenderServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<SenderServiceFutureStub> {
    private SenderServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SenderServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SenderServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Cliente -&gt; Sender
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<br.com.projeto.q2.proto.sender.SendMessageResponse> sendMessage(
        br.com.projeto.q2.proto.sender.SendMessageRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSendMessageMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Receptor -&gt; Emissor (pulling / busca)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<br.com.projeto.q2.proto.sender.PullMessagesResponse> pullMessages(
        br.com.projeto.q2.proto.sender.PullMessagesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPullMessagesMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Receptor -&gt; Emissor (ACK/NACK)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<br.com.projeto.q2.proto.sender.AckMessageResponse> ackMessage(
        br.com.projeto.q2.proto.sender.AckMessageRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAckMessageMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<br.com.projeto.q2.proto.sender.NackMessageResponse> nackMessage(
        br.com.projeto.q2.proto.sender.NackMessageRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getNackMessageMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SEND_MESSAGE = 0;
  private static final int METHODID_PULL_MESSAGES = 1;
  private static final int METHODID_ACK_MESSAGE = 2;
  private static final int METHODID_NACK_MESSAGE = 3;

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
        case METHODID_SEND_MESSAGE:
          serviceImpl.sendMessage((br.com.projeto.q2.proto.sender.SendMessageRequest) request,
              (io.grpc.stub.StreamObserver<br.com.projeto.q2.proto.sender.SendMessageResponse>) responseObserver);
          break;
        case METHODID_PULL_MESSAGES:
          serviceImpl.pullMessages((br.com.projeto.q2.proto.sender.PullMessagesRequest) request,
              (io.grpc.stub.StreamObserver<br.com.projeto.q2.proto.sender.PullMessagesResponse>) responseObserver);
          break;
        case METHODID_ACK_MESSAGE:
          serviceImpl.ackMessage((br.com.projeto.q2.proto.sender.AckMessageRequest) request,
              (io.grpc.stub.StreamObserver<br.com.projeto.q2.proto.sender.AckMessageResponse>) responseObserver);
          break;
        case METHODID_NACK_MESSAGE:
          serviceImpl.nackMessage((br.com.projeto.q2.proto.sender.NackMessageRequest) request,
              (io.grpc.stub.StreamObserver<br.com.projeto.q2.proto.sender.NackMessageResponse>) responseObserver);
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
          getSendMessageMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              br.com.projeto.q2.proto.sender.SendMessageRequest,
              br.com.projeto.q2.proto.sender.SendMessageResponse>(
                service, METHODID_SEND_MESSAGE)))
        .addMethod(
          getPullMessagesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              br.com.projeto.q2.proto.sender.PullMessagesRequest,
              br.com.projeto.q2.proto.sender.PullMessagesResponse>(
                service, METHODID_PULL_MESSAGES)))
        .addMethod(
          getAckMessageMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              br.com.projeto.q2.proto.sender.AckMessageRequest,
              br.com.projeto.q2.proto.sender.AckMessageResponse>(
                service, METHODID_ACK_MESSAGE)))
        .addMethod(
          getNackMessageMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              br.com.projeto.q2.proto.sender.NackMessageRequest,
              br.com.projeto.q2.proto.sender.NackMessageResponse>(
                service, METHODID_NACK_MESSAGE)))
        .build();
  }

  private static abstract class SenderServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SenderServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return br.com.projeto.q2.proto.sender.SenderProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SenderService");
    }
  }

  private static final class SenderServiceFileDescriptorSupplier
      extends SenderServiceBaseDescriptorSupplier {
    SenderServiceFileDescriptorSupplier() {}
  }

  private static final class SenderServiceMethodDescriptorSupplier
      extends SenderServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    SenderServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (SenderServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SenderServiceFileDescriptorSupplier())
              .addMethod(getSendMessageMethod())
              .addMethod(getPullMessagesMethod())
              .addMethod(getAckMessageMethod())
              .addMethod(getNackMessageMethod())
              .build();
        }
      }
    }
    return result;
  }
}
