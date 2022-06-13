package com.msr.better.server;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * @author MaiShuRen
 * @site https://www.maishuren.top
 * @since 2021-04-24 15:59
 **/
@Slf4j
public class SelectorServer {
    public static void main(String[] args) {
        try (ServerSocketChannel channel = ServerSocketChannel.open()) {
            channel.bind(new InetSocketAddress(8080));
            // 1.创建Selector
            Selector selector = Selector.open();
            channel.configureBlocking(false);
            // 2.建立channel与selector的联系
            SelectionKey sscKey = channel.register(selector, SelectionKey.OP_ACCEPT);
            // 服务端的SocketChannel关注是否有连接事件
            sscKey.interestOps(SelectionKey.OP_ACCEPT);

            while (true) {
                // 3.没有事件发生，线程阻塞，有事件，线程才会恢复运行
                int count = selector.select();
                log.debug("select count: {}", count);
                // 4.获取所有事件
                Set<SelectionKey> keys = selector.selectedKeys();
                // 遍历所有事件，逐一处理
                Iterator<SelectionKey> iter = keys.iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    // 判断事件类型
                    if (key.isAcceptable()) {
                        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
                        // 取消事件
                        // key.cancel();
                        // 5.区分事件类型
                        if (key.isAcceptable()) {
                            ServerSocketChannel c = (ServerSocketChannel) key.channel();
                            // 必须处理，否则select()会一直空转
                            // 所以要么处理，要么取消，不能不处理
                            SocketChannel sc = c.accept();
                            sc.configureBlocking(false);
                            // channel关联buffer
                            SelectionKey scKey = sc.register(selector, 0, byteBuffer);
                            scKey.interestOps(SelectionKey.OP_READ);
                            log.debug("{}", sc);
                        } else if (key.isReadable()) {
                            try {
                                SocketChannel readChannel = (SocketChannel) key.channel();
                                ByteBuffer attachment = (ByteBuffer) key.attachment();
                                int read = readChannel.read(attachment);
                                // 数据传输完毕，正常断开
                                if (read == -1) {
                                    key.cancel();
                                } else {
                                    //消息处理
                                    byteBuffer.flip();
                                    log.info("客户端发来数据:{}", StandardCharsets.UTF_8.decode(byteBuffer).toString());
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                // 客户端异常，断开了。取消key，把这SocketChannel的key中Selector中删除
                                key.cancel();
                            }
                        }

                    }
                    // 处理完毕，必须将事件移除
                    iter.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void split(ByteBuffer source) {
        source.flip();
        for (int i = 0; i < source.limit(); i++) {
            // 找到一条完整消息
            if (source.get(i) == '\n') {
                int length = i + 1 - source.position();
                // 把这条完整消息存入新的 ByteBuffer
                ByteBuffer target = ByteBuffer.allocate(length);
                // 从 source 读，向 target 写
                for (int j = 0; j < length; j++) {
                    target.put(source.get());
                }
                System.out.println("position:" + target.position() + " limit:" + target.limit() + " content:" + StandardCharsets.UTF_8.decode(target));
            }
        }
        source.compact();
    }
}
