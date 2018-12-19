package chapter6.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.Buffer;
import java.nio.charset.Charset;
import java.util.Date;

public class FirstClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + "客户端写出数据");

        //1.获取数据
        ByteBuf byteBuf = getByteBuf(ctx);

        //2.写数据
        ctx.channel().writeAndFlush(byteBuf);
    }
    
    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        byte[] bytes = "你好，qwqx".getBytes(Charset.forName("utf-8"));

        ByteBuf buffer = ctx.alloc().buffer();

        buffer.writeBytes(bytes);

        return buffer;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        System.out.println(new Date() + ":客户端读到数据 -> " + byteBuf.toString(Charset.forName("utf-8")));

    }
}
