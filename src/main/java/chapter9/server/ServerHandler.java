package chapter9.server;

import chapter9.protocol.request.LoginRequestPacket;
import chapter9.protocol.Packet;
import chapter9.protocol.PacketCodeC;
import chapter9.protocol.response.LoginResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * @Auther: TK
 * @Date: 2018/12/19 16:38
 * @Description:
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        if (packet instanceof LoginRequestPacket){
            System.out.println(new Date() + "客户端开始登陆 -");
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;

            if(valid(loginRequestPacket)) {
                loginResponsePacket.setSuccess(true);
                System.out.println(new Date() + "登陆成功");
            }else{
                loginResponsePacket.setSuccess(false);
                System.out.println(new Date() + "登陆成功");
            }
        }

        //回复数据到客户端
        System.out.println(new Date() + ": 服务端写出数据");
        ByteBuf out = PacketCodeC.INSTANCE.encode(loginResponsePacket);
        ctx.channel().writeAndFlush(out);
    }

    private boolean valid(LoginRequestPacket loginRequestPacket){return true;}

}
