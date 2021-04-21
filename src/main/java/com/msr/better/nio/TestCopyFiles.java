package com.msr.better.nio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author MaiShuRen
 * @site https://www.maishuren.top
 * @since 2021-04-21 23:58
 **/
public class TestCopyFiles {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        String source = "D:\\source";
        String target = "D:\\target";

        Files.walk(Paths.get(source)).forEach(path -> {
            try {
                String targetName = path.toString().replace(source, target);
                // 是目录
                if (Files.isDirectory(path)) {
                    Files.createDirectory(Paths.get(targetName));
                }
                // 是普通文件
                else if (Files.isRegularFile(path)) {
                    Files.copy(path, Paths.get(targetName));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
