package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

public interface SetPlayerGameTypePacket extends BedrockPacket {
    private int gamemode;


    public class SetPlayerGameTypeReader_v291 implements BedrockPacketReader<SetPlayerGameTypePacket> {
        public static final SetPlayerGameTypeReader_v291 INSTANCE = new SetPlayerGameTypeReader_v291();


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SetPlayerGameTypePacket packet) {
            VarInts.writeInt(buffer, packet.getGamemode());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SetPlayerGameTypePacket packet) {
            packet.setGamemode(VarInts.readInt(buffer));
        }
    }

}
