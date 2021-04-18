package com.msr.better.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author MaiShuRen
 * @site https://www.maishuren.top
 * @since 2021-04-18 16:32
 **/
public class TestGatheringWrite {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode("你好呀！");
        ByteBuffer byteBuffer1 = StandardCharsets.UTF_8.encode("hi！");
        ByteBuffer byteBuffer2 = StandardCharsets.UTF_8.encode("hello！");

        try (FileChannel channel = new RandomAccessFile("gathering.txt", "rw").getChannel()) {
            channel.write(new ByteBuffer[]{byteBuffer, byteBuffer1, byteBuffer2});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
