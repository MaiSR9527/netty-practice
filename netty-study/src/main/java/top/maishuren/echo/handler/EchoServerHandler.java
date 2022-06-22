package top.maishuren.echo.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author MaiShuRen
 * @site https://www.maishuren.top
 * @since 2022-06-21 22:35
 **/
@ChannelHandler.Sharable // 表示一个ChannelHandler可以被多个Channel安全地使用
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = LoggerFactory.getLogger(EchoServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        // 日志打印消息
        logger.info("server received: {}", in.toString(CharsetUtil.UTF_8));
        // 将接收到地消息写给发送者，不成功刷出站信息
        ctx.write(in);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        // 将未决消息冲刷到远程节点，并添加一个关闭Channel的监听器（操作完成之后关闭Channel）
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 在此方法内可以捕获异常并做出处理
        logger.error(cause.getMessage(), cause);
        // 关闭channel
        ctx.close();
    }
}
