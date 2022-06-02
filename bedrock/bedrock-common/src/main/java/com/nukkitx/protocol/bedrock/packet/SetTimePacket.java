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

interface SetTimePacket extends BedrockPacket {
    private int time;


    public class SetTimeReader_v291 implements BedrockPacketReader<SetTimePacket> {
        public static final SetTimeReader_v291 INSTANCE = new SetTimeReader_v291();


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SetTimePacket packet) {
            VarInts.writeInt(buffer, packet.getTime());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SetTimePacket packet) {
            packet.setTime(VarInts.readInt(buffer));
        }
    }

}
