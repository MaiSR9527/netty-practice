package com.msr.better.nio;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author MaiShuRen
 * @site https://www.maishuren.top
 * @since 2021-04-18 17:18
 **/
public class TestFilesAndPaths {
    public static void main(String[] args) {
        AtomicInteger dirCount = new AtomicInteger();
        AtomicInteger fileCount = new AtomicInteger();
        try {
            Files.walkFileTree(Paths.get("E:\\java\\project\\netty-practice"),new SimpleFileVisitor<Path>(){
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    System.out.println(dir.toString());
                    dirCount.incrementAndGet();
                    return super.preVisitDirectory(dir, attrs);
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    System.out.println(file.toString());
                    fileCount.incrementAndGet();
                    return super.visitFile(file, attrs);
                }
            });
            System.out.println("文件夹数："+dirCount.get());
            System.out.println("文件数："+fileCount.get());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
