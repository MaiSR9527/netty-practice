package top.maishuren.simpleim;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author MaiShuRen
 * @site https://www.maishuren.top
 * @since 2022-07-04 00:04
 **/
public class ImClient {
    private static final Logger logger = LoggerFactory.getLogger(ImClient.class);

    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                // 1.指定线程模型
                .group(workerGroup)
                // 2.指定 IO 类型为 NIO
                .channel(NioSocketChannel.class)
                // 3.IO 处理逻辑
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                    }
                });
        // 4.建立连接
        bootstrap.connect("localhost", 8088).addListener(future -> {
            if (future.isSuccess()) {
                logger.info("client connect success");
            } else {
                logger.error("client connect fail");
                // 可以进行重新连接处理
            }

        });
    }
}
