package br.com.projeto.q2.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * Sender gRPC: armazena mensagens e suporta pulling/ack.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.68.1)",
    comments = "Source: sender.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class SenderGrpcGrpc {

  private SenderGrpcGrpc() {}

  public static final java.lang.String SERVICE_NAME = "atividade.SenderGrpc";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<br.com.projeto.q2.grpc.EnqueueRequest,
      br.com.projeto.q2.grpc.EnqueueResponse> getEnqueueMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Enqueue",
      requestType = br.com.projeto.q2.grpc.EnqueueRequest.class,
      responseType = br.com.projeto.q2.grpc.EnqueueResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<br.com.projeto.q2.grpc.EnqueueRequest,
      br.com.projeto.q2.grpc.EnqueueResponse> getEnqueueMethod() {
    io.grpc.MethodDescriptor<br.com.projeto.q2.grpc.EnqueueRequest, br.com.projeto.q2.grpc.EnqueueResponse> getEnqueueMethod;
    if ((getEnqueueMethod = SenderGrpcGrpc.getEnqueueMethod) == null) {
      synchronized (SenderGrpcGrpc.class) {
        if ((getEnqueueMethod = SenderGrpcGrpc.getEnqueueMethod) == null) {
          SenderGrpcGrpc.getEnqueueMethod = getEnqueueMethod =
              io.grpc.MethodDescriptor.<br.com.projeto.q2.grpc.EnqueueRequest, br.com.projeto.q2.grpc.EnqueueResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Enqueue"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.projeto.q2.grpc.EnqueueRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.projeto.q2.grpc.EnqueueResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SenderGrpcMethodDescriptorSupplier("Enqueue"))
              .build();
        }
      }
    }
    return getEnqueueMethod;
  }

  private static volatile io.grpc.MethodDescriptor<br.com.projeto.q2.grpc.TryDeliverRequest,
      br.com.projeto.q2.grpc.TryDeliverResponse> getTryDeliverMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "TryDeliver",
      requestType = br.com.projeto.q2.grpc.TryDeliverRequest.class,
      responseType = br.com.projeto.q2.grpc.TryDeliverResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<br.com.projeto.q2.grpc.TryDeliverRequest,
      br.com.projeto.q2.grpc.TryDeliverResponse> getTryDeliverMethod() {
    io.grpc.MethodDescriptor<br.com.projeto.q2.grpc.TryDeliverRequest, br.com.projeto.q2.grpc.TryDeliverResponse> getTryDeliverMethod;
    if ((getTryDeliverMethod = SenderGrpcGrpc.getTryDeliverMethod) == null) {
      synchronized (SenderGrpcGrpc.class) {
        if ((getTryDeliverMethod = SenderGrpcGrpc.getTryDeliverMethod) == null) {
          SenderGrpcGrpc.getTryDeliverMethod = getTryDeliverMethod =
              io.grpc.MethodDescriptor.<br.com.projeto.q2.grpc.TryDeliverRequest, br.com.projeto.q2.grpc.TryDeliverResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "TryDeliver"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.projeto.q2.grpc.TryDeliverRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.projeto.q2.grpc.TryDeliverResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SenderGrpcMethodDescriptorSupplier("TryDeliver"))
              .build();
        }
      }
    }
    return getTryDeliverMethod;
  }

  private static volatile io.grpc.MethodDescriptor<br.com.projeto.q2.grpc.AckRequest,
      br.com.projeto.q2.grpc.AckResponse> getAckMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Ack",
      requestType = br.com.projeto.q2.grpc.AckRequest.class,
      responseType = br.com.projeto.q2.grpc.AckResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<br.com.projeto.q2.grpc.AckRequest,
      br.com.projeto.q2.grpc.AckResponse> getAckMethod() {
    io.grpc.MethodDescriptor<br.com.projeto.q2.grpc.AckRequest, br.com.projeto.q2.grpc.AckResponse> getAckMethod;
    if ((getAckMethod = SenderGrpcGrpc.getAckMethod) == null) {
      synchronized (SenderGrpcGrpc.class) {
        if ((getAckMethod = SenderGrpcGrpc.getAckMethod) == null) {
          SenderGrpcGrpc.getAckMethod = getAckMethod =
              io.grpc.MethodDescriptor.<br.com.projeto.q2.grpc.AckRequest, br.com.projeto.q2.grpc.AckResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Ack"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.projeto.q2.grpc.AckRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.projeto.q2.grpc.AckResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SenderGrpcMethodDescriptorSupplier("Ack"))
              .build();
        }
      }
    }
    return getAckMethod;
  }

  private static volatile io.grpc.MethodDescriptor<br.com.projeto.q2.grpc.NackRequest,
      br.com.projeto.q2.grpc.NackResponse> getNackMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Nack",
      requestType = br.com.projeto.q2.grpc.NackRequest.class,
      responseType = br.com.projeto.q2.grpc.NackResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<br.com.projeto.q2.grpc.NackRequest,
      br.com.projeto.q2.grpc.NackResponse> getNackMethod() {
    io.grpc.MethodDescriptor<br.com.projeto.q2.grpc.NackRequest, br.com.projeto.q2.grpc.NackResponse> getNackMethod;
    if ((getNackMethod = SenderGrpcGrpc.getNackMethod) == null) {
      synchronized (SenderGrpcGrpc.class) {
        if ((getNackMethod = SenderGrpcGrpc.getNackMethod) == null) {
          SenderGrpcGrpc.getNackMethod = getNackMethod =
              io.grpc.MethodDescriptor.<br.com.projeto.q2.grpc.NackRequest, br.com.projeto.q2.grpc.NackResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Nack"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.projeto.q2.grpc.NackRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.projeto.q2.grpc.NackResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SenderGrpcMethodDescriptorSupplier("Nack"))
              .build();
        }
      }
    }
    return getNackMethod;
  }

  private static volatile io.grpc.MethodDescriptor<br.com.projeto.q2.grpc.SubscribeRequest,
      br.com.projeto.q2.grpc.DeliveryHint> getSubscribeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Subscribe",
      requestType = br.com.projeto.q2.grpc.SubscribeRequest.class,
      responseType = br.com.projeto.q2.grpc.DeliveryHint.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<br.com.projeto.q2.grpc.SubscribeRequest,
      br.com.projeto.q2.grpc.DeliveryHint> getSubscribeMethod() {
    io.grpc.MethodDescriptor<br.com.projeto.q2.grpc.SubscribeRequest, br.com.projeto.q2.grpc.DeliveryHint> getSubscribeMethod;
    if ((getSubscribeMethod = SenderGrpcGrpc.getSubscribeMethod) == null) {
      synchronized (SenderGrpcGrpc.class) {
        if ((getSubscribeMethod = SenderGrpcGrpc.getSubscribeMethod) == null) {
          SenderGrpcGrpc.getSubscribeMethod = getSubscribeMethod =
              io.grpc.MethodDescriptor.<br.com.projeto.q2.grpc.SubscribeRequest, br.com.projeto.q2.grpc.DeliveryHint>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Subscribe"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.projeto.q2.grpc.SubscribeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.com.projeto.q2.grpc.DeliveryHint.getDefaultInstance()))
              .setSchemaDescriptor(new SenderGrpcMethodDescriptorSupplier("Subscribe"))
              .build();
        }
      }
    }
    return getSubscribeMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SenderGrpcStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SenderGrpcStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SenderGrpcStub>() {
        @java.lang.Override
        public SenderGrpcStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SenderGrpcStub(channel, callOptions);
        }
      };
    return SenderGrpcStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SenderGrpcBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SenderGrpcBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SenderGrpcBlockingStub>() {
        @java.lang.Override
        public SenderGrpcBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SenderGrpcBlockingStub(channel, callOptions);
        }
      };
    return SenderGrpcBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SenderGrpcFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SenderGrpcFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SenderGrpcFutureStub>() {
        @java.lang.Override
        public SenderGrpcFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SenderGrpcFutureStub(channel, callOptions);
        }
      };
    return SenderGrpcFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Sender gRPC: armazena mensagens e suporta pulling/ack.
   * </pre>
   */
  public interface AsyncService {

    /**
     */
    default void enqueue(br.com.projeto.q2.grpc.EnqueueRequest request,
        io.grpc.stub.StreamObserver<br.com.projeto.q2.grpc.EnqueueResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getEnqueueMethod(), responseObserver);
    }

    /**
     */
    default void tryDeliver(br.com.projeto.q2.grpc.TryDeliverRequest request,
        io.grpc.stub.StreamObserver<br.com.projeto.q2.grpc.TryDeliverResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getTryDeliverMethod(), responseObserver);
    }

    /**
     */
    default void ack(br.com.projeto.q2.grpc.AckRequest request,
        io.grpc.stub.StreamObserver<br.com.projeto.q2.grpc.AckResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAckMethod(), responseObserver);
    }

    /**
     */
    default void nack(br.com.projeto.q2.grpc.NackRequest request,
        io.grpc.stub.StreamObserver<br.com.projeto.q2.grpc.NackResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getNackMethod(), responseObserver);
    }

    /**
     * <pre>
     * Q3: push notifications via stream.
     * </pre>
     */
    default void subscribe(br.com.projeto.q2.grpc.SubscribeRequest request,
        io.grpc.stub.StreamObserver<br.com.projeto.q2.grpc.DeliveryHint> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSubscribeMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service SenderGrpc.
   * <pre>
   * Sender gRPC: armazena mensagens e suporta pulling/ack.
   * </pre>
   */
  public static abstract class SenderGrpcImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return SenderGrpcGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service SenderGrpc.
   * <pre>
   * Sender gRPC: armazena mensagens e suporta pulling/ack.
   * </pre>
   */
  public static final class SenderGrpcStub
      extends io.grpc.stub.AbstractAsyncStub<SenderGrpcStub> {
    private SenderGrpcStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SenderGrpcStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SenderGrpcStub(channel, callOptions);
    }

    /**
     */
    public void enqueue(br.com.projeto.q2.grpc.EnqueueRequest request,
        io.grpc.stub.StreamObserver<br.com.projeto.q2.grpc.EnqueueResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getEnqueueMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void tryDeliver(br.com.projeto.q2.grpc.TryDeliverRequest request,
        io.grpc.stub.StreamObserver<br.com.projeto.q2.grpc.TryDeliverResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getTryDeliverMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void ack(br.com.projeto.q2.grpc.AckRequest request,
        io.grpc.stub.StreamObserver<br.com.projeto.q2.grpc.AckResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAckMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void nack(br.com.projeto.q2.grpc.NackRequest request,
        io.grpc.stub.StreamObserver<br.com.projeto.q2.grpc.NackResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getNackMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Q3: push notifications via stream.
     * </pre>
     */
    public void subscribe(br.com.projeto.q2.grpc.SubscribeRequest request,
        io.grpc.stub.StreamObserver<br.com.projeto.q2.grpc.DeliveryHint> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getSubscribeMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service SenderGrpc.
   * <pre>
   * Sender gRPC: armazena mensagens e suporta pulling/ack.
   * </pre>
   */
  public static final class SenderGrpcBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<SenderGrpcBlockingStub> {
    private SenderGrpcBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SenderGrpcBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SenderGrpcBlockingStub(channel, callOptions);
    }

    /**
     */
    public br.com.projeto.q2.grpc.EnqueueResponse enqueue(br.com.projeto.q2.grpc.EnqueueRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getEnqueueMethod(), getCallOptions(), request);
    }

    /**
     */
    public br.com.projeto.q2.grpc.TryDeliverResponse tryDeliver(br.com.projeto.q2.grpc.TryDeliverRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getTryDeliverMethod(), getCallOptions(), request);
    }

    /**
     */
    public br.com.projeto.q2.grpc.AckResponse ack(br.com.projeto.q2.grpc.AckRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAckMethod(), getCallOptions(), request);
    }

    /**
     */
    public br.com.projeto.q2.grpc.NackResponse nack(br.com.projeto.q2.grpc.NackRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getNackMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Q3: push notifications via stream.
     * </pre>
     */
    public java.util.Iterator<br.com.projeto.q2.grpc.DeliveryHint> subscribe(
        br.com.projeto.q2.grpc.SubscribeRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getSubscribeMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service SenderGrpc.
   * <pre>
   * Sender gRPC: armazena mensagens e suporta pulling/ack.
   * </pre>
   */
  public static final class SenderGrpcFutureStub
      extends io.grpc.stub.AbstractFutureStub<SenderGrpcFutureStub> {
    private SenderGrpcFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SenderGrpcFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SenderGrpcFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<br.com.projeto.q2.grpc.EnqueueResponse> enqueue(
        br.com.projeto.q2.grpc.EnqueueRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getEnqueueMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<br.com.projeto.q2.grpc.TryDeliverResponse> tryDeliver(
        br.com.projeto.q2.grpc.TryDeliverRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getTryDeliverMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<br.com.projeto.q2.grpc.AckResponse> ack(
        br.com.projeto.q2.grpc.AckRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAckMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<br.com.projeto.q2.grpc.NackResponse> nack(
        br.com.projeto.q2.grpc.NackRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getNackMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_ENQUEUE = 0;
  private static final int METHODID_TRY_DELIVER = 1;
  private static final int METHODID_ACK = 2;
  private static final int METHODID_NACK = 3;
  private static final int METHODID_SUBSCRIBE = 4;

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
        case METHODID_ENQUEUE:
          serviceImpl.enqueue((br.com.projeto.q2.grpc.EnqueueRequest) request,
              (io.grpc.stub.StreamObserver<br.com.projeto.q2.grpc.EnqueueResponse>) responseObserver);
          break;
        case METHODID_TRY_DELIVER:
          serviceImpl.tryDeliver((br.com.projeto.q2.grpc.TryDeliverRequest) request,
              (io.grpc.stub.StreamObserver<br.com.projeto.q2.grpc.TryDeliverResponse>) responseObserver);
          break;
        case METHODID_ACK:
          serviceImpl.ack((br.com.projeto.q2.grpc.AckRequest) request,
              (io.grpc.stub.StreamObserver<br.com.projeto.q2.grpc.AckResponse>) responseObserver);
          break;
        case METHODID_NACK:
          serviceImpl.nack((br.com.projeto.q2.grpc.NackRequest) request,
              (io.grpc.stub.StreamObserver<br.com.projeto.q2.grpc.NackResponse>) responseObserver);
          break;
        case METHODID_SUBSCRIBE:
          serviceImpl.subscribe((br.com.projeto.q2.grpc.SubscribeRequest) request,
              (io.grpc.stub.StreamObserver<br.com.projeto.q2.grpc.DeliveryHint>) responseObserver);
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
          getEnqueueMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              br.com.projeto.q2.grpc.EnqueueRequest,
              br.com.projeto.q2.grpc.EnqueueResponse>(
                service, METHODID_ENQUEUE)))
        .addMethod(
          getTryDeliverMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              br.com.projeto.q2.grpc.TryDeliverRequest,
              br.com.projeto.q2.grpc.TryDeliverResponse>(
                service, METHODID_TRY_DELIVER)))
        .addMethod(
          getAckMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              br.com.projeto.q2.grpc.AckRequest,
              br.com.projeto.q2.grpc.AckResponse>(
                service, METHODID_ACK)))
        .addMethod(
          getNackMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              br.com.projeto.q2.grpc.NackRequest,
              br.com.projeto.q2.grpc.NackResponse>(
                service, METHODID_NACK)))
        .addMethod(
          getSubscribeMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              br.com.projeto.q2.grpc.SubscribeRequest,
              br.com.projeto.q2.grpc.DeliveryHint>(
                service, METHODID_SUBSCRIBE)))
        .build();
  }

  private static abstract class SenderGrpcBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SenderGrpcBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return br.com.projeto.q2.grpc.Sender.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SenderGrpc");
    }
  }

  private static final class SenderGrpcFileDescriptorSupplier
      extends SenderGrpcBaseDescriptorSupplier {
    SenderGrpcFileDescriptorSupplier() {}
  }

  private static final class SenderGrpcMethodDescriptorSupplier
      extends SenderGrpcBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    SenderGrpcMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (SenderGrpcGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SenderGrpcFileDescriptorSupplier())
              .addMethod(getEnqueueMethod())
              .addMethod(getTryDeliverMethod())
              .addMethod(getAckMethod())
              .addMethod(getNackMethod())
              .addMethod(getSubscribeMethod())
              .build();
        }
      }
    }
    return result;
  }
}
