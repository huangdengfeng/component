package com.seezoon.stub.session;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.64.0)",
    comments = "Source: session.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class SessionGrpc {

  private SessionGrpc() {}

  public static final java.lang.String SERVICE_NAME = "com.seezoon.session.Session";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.seezoon.stub.session.SessionPb.CreateReq,
      com.seezoon.stub.session.SessionPb.CreateResp> getCreateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Create",
      requestType = com.seezoon.stub.session.SessionPb.CreateReq.class,
      responseType = com.seezoon.stub.session.SessionPb.CreateResp.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.seezoon.stub.session.SessionPb.CreateReq,
      com.seezoon.stub.session.SessionPb.CreateResp> getCreateMethod() {
    io.grpc.MethodDescriptor<com.seezoon.stub.session.SessionPb.CreateReq, com.seezoon.stub.session.SessionPb.CreateResp> getCreateMethod;
    if ((getCreateMethod = SessionGrpc.getCreateMethod) == null) {
      synchronized (SessionGrpc.class) {
        if ((getCreateMethod = SessionGrpc.getCreateMethod) == null) {
          SessionGrpc.getCreateMethod = getCreateMethod =
              io.grpc.MethodDescriptor.<com.seezoon.stub.session.SessionPb.CreateReq, com.seezoon.stub.session.SessionPb.CreateResp>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Create"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.seezoon.stub.session.SessionPb.CreateReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.seezoon.stub.session.SessionPb.CreateResp.getDefaultInstance()))
              .setSchemaDescriptor(new SessionMethodDescriptorSupplier("Create"))
              .build();
        }
      }
    }
    return getCreateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.seezoon.stub.session.SessionPb.SetAttributeReq,
      com.seezoon.stub.session.SessionPb.SetAttributeResp> getSetAttributeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SetAttribute",
      requestType = com.seezoon.stub.session.SessionPb.SetAttributeReq.class,
      responseType = com.seezoon.stub.session.SessionPb.SetAttributeResp.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.seezoon.stub.session.SessionPb.SetAttributeReq,
      com.seezoon.stub.session.SessionPb.SetAttributeResp> getSetAttributeMethod() {
    io.grpc.MethodDescriptor<com.seezoon.stub.session.SessionPb.SetAttributeReq, com.seezoon.stub.session.SessionPb.SetAttributeResp> getSetAttributeMethod;
    if ((getSetAttributeMethod = SessionGrpc.getSetAttributeMethod) == null) {
      synchronized (SessionGrpc.class) {
        if ((getSetAttributeMethod = SessionGrpc.getSetAttributeMethod) == null) {
          SessionGrpc.getSetAttributeMethod = getSetAttributeMethod =
              io.grpc.MethodDescriptor.<com.seezoon.stub.session.SessionPb.SetAttributeReq, com.seezoon.stub.session.SessionPb.SetAttributeResp>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SetAttribute"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.seezoon.stub.session.SessionPb.SetAttributeReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.seezoon.stub.session.SessionPb.SetAttributeResp.getDefaultInstance()))
              .setSchemaDescriptor(new SessionMethodDescriptorSupplier("SetAttribute"))
              .build();
        }
      }
    }
    return getSetAttributeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.seezoon.stub.session.SessionPb.GetAttributeReq,
      com.seezoon.stub.session.SessionPb.GetAttributeResp> getGetAttributeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetAttribute",
      requestType = com.seezoon.stub.session.SessionPb.GetAttributeReq.class,
      responseType = com.seezoon.stub.session.SessionPb.GetAttributeResp.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.seezoon.stub.session.SessionPb.GetAttributeReq,
      com.seezoon.stub.session.SessionPb.GetAttributeResp> getGetAttributeMethod() {
    io.grpc.MethodDescriptor<com.seezoon.stub.session.SessionPb.GetAttributeReq, com.seezoon.stub.session.SessionPb.GetAttributeResp> getGetAttributeMethod;
    if ((getGetAttributeMethod = SessionGrpc.getGetAttributeMethod) == null) {
      synchronized (SessionGrpc.class) {
        if ((getGetAttributeMethod = SessionGrpc.getGetAttributeMethod) == null) {
          SessionGrpc.getGetAttributeMethod = getGetAttributeMethod =
              io.grpc.MethodDescriptor.<com.seezoon.stub.session.SessionPb.GetAttributeReq, com.seezoon.stub.session.SessionPb.GetAttributeResp>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetAttribute"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.seezoon.stub.session.SessionPb.GetAttributeReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.seezoon.stub.session.SessionPb.GetAttributeResp.getDefaultInstance()))
              .setSchemaDescriptor(new SessionMethodDescriptorSupplier("GetAttribute"))
              .build();
        }
      }
    }
    return getGetAttributeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.seezoon.stub.session.SessionPb.GetAllAttributeReq,
      com.seezoon.stub.session.SessionPb.GetAllAttributeResp> getGetAllAttributeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetAllAttribute",
      requestType = com.seezoon.stub.session.SessionPb.GetAllAttributeReq.class,
      responseType = com.seezoon.stub.session.SessionPb.GetAllAttributeResp.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.seezoon.stub.session.SessionPb.GetAllAttributeReq,
      com.seezoon.stub.session.SessionPb.GetAllAttributeResp> getGetAllAttributeMethod() {
    io.grpc.MethodDescriptor<com.seezoon.stub.session.SessionPb.GetAllAttributeReq, com.seezoon.stub.session.SessionPb.GetAllAttributeResp> getGetAllAttributeMethod;
    if ((getGetAllAttributeMethod = SessionGrpc.getGetAllAttributeMethod) == null) {
      synchronized (SessionGrpc.class) {
        if ((getGetAllAttributeMethod = SessionGrpc.getGetAllAttributeMethod) == null) {
          SessionGrpc.getGetAllAttributeMethod = getGetAllAttributeMethod =
              io.grpc.MethodDescriptor.<com.seezoon.stub.session.SessionPb.GetAllAttributeReq, com.seezoon.stub.session.SessionPb.GetAllAttributeResp>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetAllAttribute"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.seezoon.stub.session.SessionPb.GetAllAttributeReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.seezoon.stub.session.SessionPb.GetAllAttributeResp.getDefaultInstance()))
              .setSchemaDescriptor(new SessionMethodDescriptorSupplier("GetAllAttribute"))
              .build();
        }
      }
    }
    return getGetAllAttributeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.seezoon.stub.session.SessionPb.RemoveAttributeReq,
      com.seezoon.stub.session.SessionPb.RemoveAttributeResp> getRemoveAttributeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RemoveAttribute",
      requestType = com.seezoon.stub.session.SessionPb.RemoveAttributeReq.class,
      responseType = com.seezoon.stub.session.SessionPb.RemoveAttributeResp.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.seezoon.stub.session.SessionPb.RemoveAttributeReq,
      com.seezoon.stub.session.SessionPb.RemoveAttributeResp> getRemoveAttributeMethod() {
    io.grpc.MethodDescriptor<com.seezoon.stub.session.SessionPb.RemoveAttributeReq, com.seezoon.stub.session.SessionPb.RemoveAttributeResp> getRemoveAttributeMethod;
    if ((getRemoveAttributeMethod = SessionGrpc.getRemoveAttributeMethod) == null) {
      synchronized (SessionGrpc.class) {
        if ((getRemoveAttributeMethod = SessionGrpc.getRemoveAttributeMethod) == null) {
          SessionGrpc.getRemoveAttributeMethod = getRemoveAttributeMethod =
              io.grpc.MethodDescriptor.<com.seezoon.stub.session.SessionPb.RemoveAttributeReq, com.seezoon.stub.session.SessionPb.RemoveAttributeResp>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RemoveAttribute"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.seezoon.stub.session.SessionPb.RemoveAttributeReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.seezoon.stub.session.SessionPb.RemoveAttributeResp.getDefaultInstance()))
              .setSchemaDescriptor(new SessionMethodDescriptorSupplier("RemoveAttribute"))
              .build();
        }
      }
    }
    return getRemoveAttributeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.seezoon.stub.session.SessionPb.InvalidateReq,
      com.seezoon.stub.session.SessionPb.InvalidateResp> getInvalidateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Invalidate",
      requestType = com.seezoon.stub.session.SessionPb.InvalidateReq.class,
      responseType = com.seezoon.stub.session.SessionPb.InvalidateResp.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.seezoon.stub.session.SessionPb.InvalidateReq,
      com.seezoon.stub.session.SessionPb.InvalidateResp> getInvalidateMethod() {
    io.grpc.MethodDescriptor<com.seezoon.stub.session.SessionPb.InvalidateReq, com.seezoon.stub.session.SessionPb.InvalidateResp> getInvalidateMethod;
    if ((getInvalidateMethod = SessionGrpc.getInvalidateMethod) == null) {
      synchronized (SessionGrpc.class) {
        if ((getInvalidateMethod = SessionGrpc.getInvalidateMethod) == null) {
          SessionGrpc.getInvalidateMethod = getInvalidateMethod =
              io.grpc.MethodDescriptor.<com.seezoon.stub.session.SessionPb.InvalidateReq, com.seezoon.stub.session.SessionPb.InvalidateResp>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Invalidate"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.seezoon.stub.session.SessionPb.InvalidateReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.seezoon.stub.session.SessionPb.InvalidateResp.getDefaultInstance()))
              .setSchemaDescriptor(new SessionMethodDescriptorSupplier("Invalidate"))
              .build();
        }
      }
    }
    return getInvalidateMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SessionStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SessionStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SessionStub>() {
        @java.lang.Override
        public SessionStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SessionStub(channel, callOptions);
        }
      };
    return SessionStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SessionBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SessionBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SessionBlockingStub>() {
        @java.lang.Override
        public SessionBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SessionBlockingStub(channel, callOptions);
        }
      };
    return SessionBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SessionFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SessionFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SessionFutureStub>() {
        @java.lang.Override
        public SessionFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SessionFutureStub(channel, callOptions);
        }
      };
    return SessionFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     * <pre>
     * 创建session
     * </pre>
     */
    default void create(com.seezoon.stub.session.SessionPb.CreateReq request,
        io.grpc.stub.StreamObserver<com.seezoon.stub.session.SessionPb.CreateResp> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateMethod(), responseObserver);
    }

    /**
     * <pre>
     * 设置属性
     * </pre>
     */
    default void setAttribute(com.seezoon.stub.session.SessionPb.SetAttributeReq request,
        io.grpc.stub.StreamObserver<com.seezoon.stub.session.SessionPb.SetAttributeResp> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSetAttributeMethod(), responseObserver);
    }

    /**
     * <pre>
     * 获取属性
     * </pre>
     */
    default void getAttribute(com.seezoon.stub.session.SessionPb.GetAttributeReq request,
        io.grpc.stub.StreamObserver<com.seezoon.stub.session.SessionPb.GetAttributeResp> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAttributeMethod(), responseObserver);
    }

    /**
     * <pre>
     * 获取全部属性
     * </pre>
     */
    default void getAllAttribute(com.seezoon.stub.session.SessionPb.GetAllAttributeReq request,
        io.grpc.stub.StreamObserver<com.seezoon.stub.session.SessionPb.GetAllAttributeResp> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAllAttributeMethod(), responseObserver);
    }

    /**
     * <pre>
     * 删除属性
     * </pre>
     */
    default void removeAttribute(com.seezoon.stub.session.SessionPb.RemoveAttributeReq request,
        io.grpc.stub.StreamObserver<com.seezoon.stub.session.SessionPb.RemoveAttributeResp> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRemoveAttributeMethod(), responseObserver);
    }

    /**
     * <pre>
     * 销毁
     * </pre>
     */
    default void invalidate(com.seezoon.stub.session.SessionPb.InvalidateReq request,
        io.grpc.stub.StreamObserver<com.seezoon.stub.session.SessionPb.InvalidateResp> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getInvalidateMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service Session.
   */
  public static abstract class SessionImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return SessionGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service Session.
   */
  public static final class SessionStub
      extends io.grpc.stub.AbstractAsyncStub<SessionStub> {
    private SessionStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SessionStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SessionStub(channel, callOptions);
    }

    /**
     * <pre>
     * 创建session
     * </pre>
     */
    public void create(com.seezoon.stub.session.SessionPb.CreateReq request,
        io.grpc.stub.StreamObserver<com.seezoon.stub.session.SessionPb.CreateResp> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 设置属性
     * </pre>
     */
    public void setAttribute(com.seezoon.stub.session.SessionPb.SetAttributeReq request,
        io.grpc.stub.StreamObserver<com.seezoon.stub.session.SessionPb.SetAttributeResp> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSetAttributeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 获取属性
     * </pre>
     */
    public void getAttribute(com.seezoon.stub.session.SessionPb.GetAttributeReq request,
        io.grpc.stub.StreamObserver<com.seezoon.stub.session.SessionPb.GetAttributeResp> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAttributeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 获取全部属性
     * </pre>
     */
    public void getAllAttribute(com.seezoon.stub.session.SessionPb.GetAllAttributeReq request,
        io.grpc.stub.StreamObserver<com.seezoon.stub.session.SessionPb.GetAllAttributeResp> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAllAttributeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 删除属性
     * </pre>
     */
    public void removeAttribute(com.seezoon.stub.session.SessionPb.RemoveAttributeReq request,
        io.grpc.stub.StreamObserver<com.seezoon.stub.session.SessionPb.RemoveAttributeResp> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRemoveAttributeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 销毁
     * </pre>
     */
    public void invalidate(com.seezoon.stub.session.SessionPb.InvalidateReq request,
        io.grpc.stub.StreamObserver<com.seezoon.stub.session.SessionPb.InvalidateResp> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getInvalidateMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service Session.
   */
  public static final class SessionBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<SessionBlockingStub> {
    private SessionBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SessionBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SessionBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 创建session
     * </pre>
     */
    public com.seezoon.stub.session.SessionPb.CreateResp create(com.seezoon.stub.session.SessionPb.CreateReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 设置属性
     * </pre>
     */
    public com.seezoon.stub.session.SessionPb.SetAttributeResp setAttribute(com.seezoon.stub.session.SessionPb.SetAttributeReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSetAttributeMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 获取属性
     * </pre>
     */
    public com.seezoon.stub.session.SessionPb.GetAttributeResp getAttribute(com.seezoon.stub.session.SessionPb.GetAttributeReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAttributeMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 获取全部属性
     * </pre>
     */
    public com.seezoon.stub.session.SessionPb.GetAllAttributeResp getAllAttribute(com.seezoon.stub.session.SessionPb.GetAllAttributeReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAllAttributeMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 删除属性
     * </pre>
     */
    public com.seezoon.stub.session.SessionPb.RemoveAttributeResp removeAttribute(com.seezoon.stub.session.SessionPb.RemoveAttributeReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRemoveAttributeMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 销毁
     * </pre>
     */
    public com.seezoon.stub.session.SessionPb.InvalidateResp invalidate(com.seezoon.stub.session.SessionPb.InvalidateReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getInvalidateMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service Session.
   */
  public static final class SessionFutureStub
      extends io.grpc.stub.AbstractFutureStub<SessionFutureStub> {
    private SessionFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SessionFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SessionFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 创建session
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.seezoon.stub.session.SessionPb.CreateResp> create(
        com.seezoon.stub.session.SessionPb.CreateReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 设置属性
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.seezoon.stub.session.SessionPb.SetAttributeResp> setAttribute(
        com.seezoon.stub.session.SessionPb.SetAttributeReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSetAttributeMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 获取属性
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.seezoon.stub.session.SessionPb.GetAttributeResp> getAttribute(
        com.seezoon.stub.session.SessionPb.GetAttributeReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAttributeMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 获取全部属性
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.seezoon.stub.session.SessionPb.GetAllAttributeResp> getAllAttribute(
        com.seezoon.stub.session.SessionPb.GetAllAttributeReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAllAttributeMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 删除属性
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.seezoon.stub.session.SessionPb.RemoveAttributeResp> removeAttribute(
        com.seezoon.stub.session.SessionPb.RemoveAttributeReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRemoveAttributeMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 销毁
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.seezoon.stub.session.SessionPb.InvalidateResp> invalidate(
        com.seezoon.stub.session.SessionPb.InvalidateReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getInvalidateMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE = 0;
  private static final int METHODID_SET_ATTRIBUTE = 1;
  private static final int METHODID_GET_ATTRIBUTE = 2;
  private static final int METHODID_GET_ALL_ATTRIBUTE = 3;
  private static final int METHODID_REMOVE_ATTRIBUTE = 4;
  private static final int METHODID_INVALIDATE = 5;

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
        case METHODID_CREATE:
          serviceImpl.create((com.seezoon.stub.session.SessionPb.CreateReq) request,
              (io.grpc.stub.StreamObserver<com.seezoon.stub.session.SessionPb.CreateResp>) responseObserver);
          break;
        case METHODID_SET_ATTRIBUTE:
          serviceImpl.setAttribute((com.seezoon.stub.session.SessionPb.SetAttributeReq) request,
              (io.grpc.stub.StreamObserver<com.seezoon.stub.session.SessionPb.SetAttributeResp>) responseObserver);
          break;
        case METHODID_GET_ATTRIBUTE:
          serviceImpl.getAttribute((com.seezoon.stub.session.SessionPb.GetAttributeReq) request,
              (io.grpc.stub.StreamObserver<com.seezoon.stub.session.SessionPb.GetAttributeResp>) responseObserver);
          break;
        case METHODID_GET_ALL_ATTRIBUTE:
          serviceImpl.getAllAttribute((com.seezoon.stub.session.SessionPb.GetAllAttributeReq) request,
              (io.grpc.stub.StreamObserver<com.seezoon.stub.session.SessionPb.GetAllAttributeResp>) responseObserver);
          break;
        case METHODID_REMOVE_ATTRIBUTE:
          serviceImpl.removeAttribute((com.seezoon.stub.session.SessionPb.RemoveAttributeReq) request,
              (io.grpc.stub.StreamObserver<com.seezoon.stub.session.SessionPb.RemoveAttributeResp>) responseObserver);
          break;
        case METHODID_INVALIDATE:
          serviceImpl.invalidate((com.seezoon.stub.session.SessionPb.InvalidateReq) request,
              (io.grpc.stub.StreamObserver<com.seezoon.stub.session.SessionPb.InvalidateResp>) responseObserver);
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
          getCreateMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.seezoon.stub.session.SessionPb.CreateReq,
              com.seezoon.stub.session.SessionPb.CreateResp>(
                service, METHODID_CREATE)))
        .addMethod(
          getSetAttributeMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.seezoon.stub.session.SessionPb.SetAttributeReq,
              com.seezoon.stub.session.SessionPb.SetAttributeResp>(
                service, METHODID_SET_ATTRIBUTE)))
        .addMethod(
          getGetAttributeMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.seezoon.stub.session.SessionPb.GetAttributeReq,
              com.seezoon.stub.session.SessionPb.GetAttributeResp>(
                service, METHODID_GET_ATTRIBUTE)))
        .addMethod(
          getGetAllAttributeMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.seezoon.stub.session.SessionPb.GetAllAttributeReq,
              com.seezoon.stub.session.SessionPb.GetAllAttributeResp>(
                service, METHODID_GET_ALL_ATTRIBUTE)))
        .addMethod(
          getRemoveAttributeMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.seezoon.stub.session.SessionPb.RemoveAttributeReq,
              com.seezoon.stub.session.SessionPb.RemoveAttributeResp>(
                service, METHODID_REMOVE_ATTRIBUTE)))
        .addMethod(
          getInvalidateMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.seezoon.stub.session.SessionPb.InvalidateReq,
              com.seezoon.stub.session.SessionPb.InvalidateResp>(
                service, METHODID_INVALIDATE)))
        .build();
  }

  private static abstract class SessionBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SessionBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.seezoon.stub.session.SessionPb.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Session");
    }
  }

  private static final class SessionFileDescriptorSupplier
      extends SessionBaseDescriptorSupplier {
    SessionFileDescriptorSupplier() {}
  }

  private static final class SessionMethodDescriptorSupplier
      extends SessionBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    SessionMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (SessionGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SessionFileDescriptorSupplier())
              .addMethod(getCreateMethod())
              .addMethod(getSetAttributeMethod())
              .addMethod(getGetAttributeMethod())
              .addMethod(getGetAllAttributeMethod())
              .addMethod(getRemoveAttributeMethod())
              .addMethod(getInvalidateMethod())
              .build();
        }
      }
    }
    return result;
  }
}
