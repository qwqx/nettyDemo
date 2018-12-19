package chapter6.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * @Auther: TK
 * @Date: 2018/12/19 16:38
 * @Description:
 */
public class FirstServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        System.out.println(new Date() + "服务端读到的数据 -> " + byteBuf.toString(Charset.forName("utf-8")));

        //回复数据到客户端
        System.out.println(new Date() + ": 服务端写出数据");
        ByteBuf out = getByteBuf(ctx);
        ctx.channel().writeAndFlush(out);
    }

    private  ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        byte[] bytes = "你好，欢迎关注我的公众号！".getBytes(Charset.forName("utf-8"));

        ByteBuf byteBuf = ctx.alloc().buffer();

        byteBuf.writeBytes(bytes);

        return byteBuf;
    }
}
