package top.maishuren.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.maishuren.echo.handler.EchoServerHandler;

import java.net.InetSocketAddress;

/**
 * @author MaiShuRen
 * @site https://www.maishuren.top
 * @since 2022-06-21 23:21
 **/
public class EchoServer {
    private final int port;
    private static Logger logger = LoggerFactory.getLogger(EchoServer.class);

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        // 从程序启动参数获取端口号
        if (args.length != 1) {
            logger.error("Usage: {} <port> is missing", EchoServer.class.getSimpleName());
            return;
        }
        int port = Integer.parseInt(args[0]);
        EchoServer echoServer = new EchoServer(port);
        try {
            // 启动server
            echoServer.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void start() throws InterruptedException {
        // 实例化自定义的 ChannelHandler
        EchoServerHandler serverHandler = new EchoServerHandler();
        // 创建 NioEventLoopGroup
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            // 创建 ServerBootstrap
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(group)
                    // 指定所使用的NIO 传输 Channel
                    .channel(NioServerSocketChannel.class)
                    // 使用指定的端口设置套接字地址
                    .localAddress(new InetSocketAddress(port))
                    // 添加 EchoServerHandler 到子 Channel 的ChannelPipeline
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            // EchoServerHandler 使用@Shareable标注，所以可以使用同样的实例
                            ch.pipeline().addLast(serverHandler);
                        }
                    });
            // 异步绑定服务器，调用sync方法阻塞等待绑定完成
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            logger.info(this.getClass().getSimpleName()+" started listening on port: {}", channelFuture.channel().localAddress());
            // 获取Channel的 CloseFuture，并且阻塞当前线程直至完成
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        } finally {
            // 关闭NioEventLoopGroup释放资源
            group.shutdownGracefully().sync();
        }
    }
}
