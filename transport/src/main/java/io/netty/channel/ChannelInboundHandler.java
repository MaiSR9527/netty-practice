/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.channel;

/**
 * {@link ChannelHandler} which adds callbacks for state changes. This allows the user
 * to hook in to state changes easily.
 */
public interface ChannelInboundHandler extends ChannelHandler {

    /**
     * 当 Channel 已经注册到它的 EventLoop 并且能够处理 I/O 时被调用
     * The {@link Channel} of the {@link ChannelHandlerContext} was registered with its {@link EventLoop}
     */
    void channelRegistered(ChannelHandlerContext ctx) throws Exception;

    /**
     * 当 Channel 从它的 EventLoop 注销并且无法处理任何 I/O 时被调用
     * The {@link Channel} of the {@link ChannelHandlerContext} was unregistered from its {@link EventLoop}
     */
    void channelUnregistered(ChannelHandlerContext ctx) throws Exception;

    /**
     * 当 Channel 处于活动状态时被调用； Channel 已经连接/绑定并且已经就绪
     * The {@link Channel} of the {@link ChannelHandlerContext} is now active
     */
    void channelActive(ChannelHandlerContext ctx) throws Exception;

    /**
     * 当 Channel 离开活动状态并且不再连接它的远程节点时被调用
     * The {@link Channel} of the {@link ChannelHandlerContext} was registered is now inactive and reached its
     * end of lifetime.
     */
    void channelInactive(ChannelHandlerContext ctx) throws Exception;

    /**
     * 当从 Channel 读取数据时被调用
     * Invoked when the current {@link Channel} has read a message from the peer.
     */
    void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception;

    /**
     * 当 Channel 上的一个读操作完成时被调用
     * 当所有可读的字节都已经从 Channel 中读取之后，将会调用该回调方法；所以，可能在 channelReadComplete()被调用之前看到多次调用 channelRead(...)
     * Invoked when the last message read by the current read operation has been consumed by
     * {@link #channelRead(ChannelHandlerContext, Object)}.  If {@link ChannelOption#AUTO_READ} is off, no further
     * attempt to read an inbound data from the current {@link Channel} will be made until
     * {@link ChannelHandlerContext#read()} is called.
     */
    void channelReadComplete(ChannelHandlerContext ctx) throws Exception;

    /**
     * 当 ChannelInboundHandler.fireUserEventTriggered() 方法被调用时被调用，因为一个 POJO 被传经了 ChannelPipeline
     * Gets called if an user event was triggered.
     */
    void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception;

    /**
     * 当 Channel 的可写状态发生改变时被调用。用户可以确保写操作不会完成
     * 得太快（以避免发生 OutOfMemoryError ）或者可以在 Channel 变为再
     * 次可写时恢复写入。可以通过调用 Channel 的 isWritable() 方法来检测
     * Channel 的可写性。与可写性相关的阈值可以通过 Channel.config().
     * setWriteHighWaterMark() 和 Channel.config().setWriteLowWater-
     * Mark() 方法来设置
     * Gets called once the writable state of a {@link Channel} changed. You can check the state with
     * {@link Channel#isWritable()}.
     */
    void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception;

    /**
     * Channel 发生异常时被调用
     * Gets called if a {@link Throwable} was thrown.
     */
    @Override
    @SuppressWarnings("deprecation")
    void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception;
}
