package chapter9.client;

import chapter9.protocol.Packet;
import chapter9.protocol.request.LoginRequestPacket;
import chapter9.protocol.PacketCodeC;
import chapter9.protocol.response.LoginResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + "客户端写出数据");

        //1.获取数据
        ByteBuf byteBuf = getByteBuf(ctx);

        //2.写数据
        ctx.channel().writeAndFlush(byteBuf);
    }
    
    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        //byte[] bytes = "你好，qwqx".getBytes(Charset.forName("utf-8"));
        System.out.println(new Date() + "客户端开始登陆");
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(0001);
        loginRequestPacket.setUsername("zhangdan");
        loginRequestPacket.setPassword("123456");
        PacketCodeC packetCodeC = new PacketCodeC();
        ByteBuf buffer = packetCodeC.encode(loginRequestPacket);
        return buffer;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(new Date() + "客户端收到数据");
        ByteBuf byteBuf = (ByteBuf) msg;
        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);
        if(packet instanceof LoginResponsePacket) {
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;
            System.out.println(new Date() + "登陆结果：" + loginResponsePacket.isSuccess());
        }
    }
}
