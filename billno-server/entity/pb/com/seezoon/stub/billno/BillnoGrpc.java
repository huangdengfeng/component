package com.seezoon.stub.billno;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.64.0)",
    comments = "Source: billno.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class BillnoGrpc {

  private BillnoGrpc() {}

  public static final java.lang.String SERVICE_NAME = "com.seezoon.billno.Billno";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.seezoon.stub.billno.BillnoPb.GetBillnoReq,
      com.seezoon.stub.billno.BillnoPb.GetBillnoResp> getGetBillnoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetBillno",
      requestType = com.seezoon.stub.billno.BillnoPb.GetBillnoReq.class,
      responseType = com.seezoon.stub.billno.BillnoPb.GetBillnoResp.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.seezoon.stub.billno.BillnoPb.GetBillnoReq,
      com.seezoon.stub.billno.BillnoPb.GetBillnoResp> getGetBillnoMethod() {
    io.grpc.MethodDescriptor<com.seezoon.stub.billno.BillnoPb.GetBillnoReq, com.seezoon.stub.billno.BillnoPb.GetBillnoResp> getGetBillnoMethod;
    if ((getGetBillnoMethod = BillnoGrpc.getGetBillnoMethod) == null) {
      synchronized (BillnoGrpc.class) {
        if ((getGetBillnoMethod = BillnoGrpc.getGetBillnoMethod) == null) {
          BillnoGrpc.getGetBillnoMethod = getGetBillnoMethod =
              io.grpc.MethodDescriptor.<com.seezoon.stub.billno.BillnoPb.GetBillnoReq, com.seezoon.stub.billno.BillnoPb.GetBillnoResp>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetBillno"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.seezoon.stub.billno.BillnoPb.GetBillnoReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.seezoon.stub.billno.BillnoPb.GetBillnoResp.getDefaultInstance()))
              .setSchemaDescriptor(new BillnoMethodDescriptorSupplier("GetBillno"))
              .build();
        }
      }
    }
    return getGetBillnoMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static BillnoStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BillnoStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BillnoStub>() {
        @java.lang.Override
        public BillnoStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BillnoStub(channel, callOptions);
        }
      };
    return BillnoStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static BillnoBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BillnoBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BillnoBlockingStub>() {
        @java.lang.Override
        public BillnoBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BillnoBlockingStub(channel, callOptions);
        }
      };
    return BillnoBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static BillnoFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BillnoFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BillnoFutureStub>() {
        @java.lang.Override
        public BillnoFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BillnoFutureStub(channel, callOptions);
        }
      };
    return BillnoFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     * <pre>
     * 获取单号
     * </pre>
     */
    default void getBillno(com.seezoon.stub.billno.BillnoPb.GetBillnoReq request,
        io.grpc.stub.StreamObserver<com.seezoon.stub.billno.BillnoPb.GetBillnoResp> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetBillnoMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service Billno.
   */
  public static abstract class BillnoImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return BillnoGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service Billno.
   */
  public static final class BillnoStub
      extends io.grpc.stub.AbstractAsyncStub<BillnoStub> {
    private BillnoStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BillnoStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BillnoStub(channel, callOptions);
    }

    /**
     * <pre>
     * 获取单号
     * </pre>
     */
    public void getBillno(com.seezoon.stub.billno.BillnoPb.GetBillnoReq request,
        io.grpc.stub.StreamObserver<com.seezoon.stub.billno.BillnoPb.GetBillnoResp> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetBillnoMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service Billno.
   */
  public static final class BillnoBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<BillnoBlockingStub> {
    private BillnoBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BillnoBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BillnoBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 获取单号
     * </pre>
     */
    public com.seezoon.stub.billno.BillnoPb.GetBillnoResp getBillno(com.seezoon.stub.billno.BillnoPb.GetBillnoReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetBillnoMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service Billno.
   */
  public static final class BillnoFutureStub
      extends io.grpc.stub.AbstractFutureStub<BillnoFutureStub> {
    private BillnoFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BillnoFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BillnoFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 获取单号
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.seezoon.stub.billno.BillnoPb.GetBillnoResp> getBillno(
        com.seezoon.stub.billno.BillnoPb.GetBillnoReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetBillnoMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_BILLNO = 0;

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
        case METHODID_GET_BILLNO:
          serviceImpl.getBillno((com.seezoon.stub.billno.BillnoPb.GetBillnoReq) request,
              (io.grpc.stub.StreamObserver<com.seezoon.stub.billno.BillnoPb.GetBillnoResp>) responseObserver);
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
          getGetBillnoMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.seezoon.stub.billno.BillnoPb.GetBillnoReq,
              com.seezoon.stub.billno.BillnoPb.GetBillnoResp>(
                service, METHODID_GET_BILLNO)))
        .build();
  }

  private static abstract class BillnoBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    BillnoBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.seezoon.stub.billno.BillnoPb.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Billno");
    }
  }

  private static final class BillnoFileDescriptorSupplier
      extends BillnoBaseDescriptorSupplier {
    BillnoFileDescriptorSupplier() {}
  }

  private static final class BillnoMethodDescriptorSupplier
      extends BillnoBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    BillnoMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (BillnoGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new BillnoFileDescriptorSupplier())
              .addMethod(getGetBillnoMethod())
              .build();
        }
      }
    }
    return result;
  }
}
