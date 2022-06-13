package com.msr.better.client;

import java.io.IOException;
import java.net.Socket;

/**
 * @author MaiShuRen
 * @site https://www.maishuren.top
 * @since 2021-04-24 16:42
 **/
public class TestClient2 {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8080)) {
            System.out.println(socket);
            socket.getOutputStream().write("world".getBytes());
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
