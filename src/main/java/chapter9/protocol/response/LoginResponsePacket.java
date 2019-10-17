package chapter9.protocol.response;

import chapter9.protocol.Packet;
import lombok.Data;

import static chapter9.protocol.command.Command.LOGIN_RESPONSE;

@Data
public class LoginResponsePacket extends Packet {
    private boolean success;

    private String reason;


    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
