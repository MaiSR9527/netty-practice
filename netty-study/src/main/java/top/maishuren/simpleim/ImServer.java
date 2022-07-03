package top.maishuren.simpleim;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author MaiShuRen
 * @site https://www.maishuren.top
 * @since 2022-07-04 00:04
 **/
public class ImServer {
    private static final Logger logger = LoggerFactory.getLogger(ImServer.class);

    public static void main(String[] args) {
        // 用于监听端口，接受连接请求的线程组
        NioEventLoopGroup leaderLoop = new NioEventLoopGroup();
        // 用于处理每个连接读写的线程组
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        // 创建服务端引导
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(leaderLoop, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioServerSocketChannel>() {
                    @Override
                    protected void initChannel(NioServerSocketChannel ch) throws Exception {
                        logger.info("Server Channel is initializing...");
                    }
                })
                .bind(8088)
                .addListener(future -> {
                    if (future.isSuccess()) {
                        logger.info("Server bind port 8088 success");
                    } else {
                        logger.error("Server bind port 8088 fail");
                    }
                });
    }
}
