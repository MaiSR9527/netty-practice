package com.msr.better.nio;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @author MaiShuRen
 * @site https://www.maishuren.top
 * @since 2021-04-18 15:55
 **/
public class TestByteBufferString {
    public static void main(String[] args) {
        // 1.字符串直接转字节数组
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        String s = "buffer";
        byteBuffer.put(s.getBytes());
        System.out.println("position:" + byteBuffer.position() + " limit:" + byteBuffer.limit());

        // 2.position是0，可以直接读
        ByteBuffer byteBuffer1 = StandardCharsets.UTF_8.encode(s);
        System.out.println("position:" + byteBuffer1.position() + " limit:" + byteBuffer1.limit());

        // 3.wrap，可以直接读
        ByteBuffer byteBuffer2 = ByteBuffer.wrap(s.getBytes());
        System.out.println("position:" + byteBuffer2.position() + " limit:" + byteBuffer2.limit());

        // StandardCharsets读buffer
        String s1 = StandardCharsets.UTF_8.decode(byteBuffer1).toString();
        System.out.println("position:" + byteBuffer1.position() + " limit:" + byteBuffer1.limit());
        System.out.println(s1);

    }
}
