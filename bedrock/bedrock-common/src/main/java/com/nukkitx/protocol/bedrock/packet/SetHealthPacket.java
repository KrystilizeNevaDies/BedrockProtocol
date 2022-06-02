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

interface SetHealthPacket extends BedrockPacket {
    private int health;


    public class SetHealthReader_v291 implements BedrockPacketReader<SetHealthPacket> {
        public static final SetHealthReader_v291 INSTANCE = new SetHealthReader_v291();


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SetHealthPacket packet) {
            VarInts.writeInt(buffer, packet.getHealth());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SetHealthPacket packet) {
            packet.setHealth(VarInts.readInt(buffer));
        }
    }
}
