package chapter8.protocol.command;

import chapter8.serialize.Serializer;
import chapter8.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

public class PacketCodeC {

    private static final int MAGIC_NUMBER = 0X12345678;
    private static final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private static final Map<Byte, Serializer> serilizerMap;

    static {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);

        serilizerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serilizerMap.put(serializer.getSerializerAlogrithm(), serializer);
    }

    private Serializer getSerializer(byte serializerAlogrithm) {
        return  serilizerMap.get(serializerAlogrithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {
        return packetTypeMap.get(command);
    }

    public ByteBuf encode(Packet packet) {
        //1.创建bytebuf对象
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer();

        //2.序列化
        byte[] bytes = Serializer.DAFAULT.serialize(packet);

        //3.写数据
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DAFAULT.getSerializerAlogrithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return  byteBuf;
    }

    public Packet decode(ByteBuf byteBuf) {
        //跳过魔数
        byteBuf.skipBytes(4);

        byteBuf.skipBytes(1);

        //序列化算法
        byte serializeAlgorithm = byteBuf.readByte();

        //指令
        byte command = byteBuf.readByte();

        //数据长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.dserialize(requestType, bytes);
        }


        return null;
    }
}
