package com.msr.better.nio;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author MaiShuRen
 * @site https://www.maishuren.top
 * @since 2021-04-14 23:29
 **/
public class TestByteBuffer {
    public static void main(String[] args) {
        try (FileChannel channel = new FileInputStream("file.txt").getChannel()) {
            // 获取buffer
            ByteBuffer byteBuffer = ByteBuffer.allocate(10);
            while (true) {
                // 从Channel中读数据
                int len = channel.read(byteBuffer);
                if (len == -1) {
                    break;
                }
                // 反转缓冲区，切换到读
                byteBuffer.flip();
//                while (byteBuffer.hasRemaining()) {
//                    // 一次读一个字节
//                    byte b = byteBuffer.get();
//                    System.out.println((char) b);
//                }

                byte[] array = byteBuffer.array();
                System.out.println(new String(array));
                // 清空缓冲区，切换到写；
                byteBuffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
