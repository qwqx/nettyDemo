package chapter9.protocol.request;

import chapter9.protocol.Packet;
import lombok.Data;

import static chapter8.protocol.command.Command.LOGIN_REQUEST;

@Data
public class LoginRequestPacket extends Packet {

    private Integer userId;

    private String username;

    private String password;


    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
