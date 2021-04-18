package com.msr.better.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author MaiShuRen
 * @site https://www.maishuren.top
 * @since 2021-04-18 16:26
 **/
public class TestScatteringRead {

    public static void main(String[] args) {
        try (RandomAccessFile file = new RandomAccessFile("file.txt", "rw")) {
            FileChannel channel = file.getChannel();
            ByteBuffer a = ByteBuffer.allocate(6);
            ByteBuffer b = ByteBuffer.allocate(6);
            ByteBuffer c = ByteBuffer.allocate(6);
            channel.read(new ByteBuffer[]{a, b, c});
            a.flip();
            b.flip();
            c.flip();
            System.out.println(StandardCharsets.UTF_8.decode(a).toString());
            System.out.println(StandardCharsets.UTF_8.decode(b).toString());
            System.out.println(StandardCharsets.UTF_8.decode(c).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
