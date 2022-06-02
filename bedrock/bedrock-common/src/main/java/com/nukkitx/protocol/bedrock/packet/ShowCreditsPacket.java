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

interface ShowCreditsPacket extends BedrockPacket {
    private long runtimeEntityId;
    private Status status;


    public enum Status {
        START_CREDITS,
        END_CREDITS
    }

    public class ShowCreditsReader_v291 implements BedrockPacketReader<ShowCreditsPacket> {
        public static final ShowCreditsReader_v291 INSTANCE = new ShowCreditsReader_v291();


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ShowCreditsPacket packet) {
            VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
            VarInts.writeInt(buffer, packet.getStatus().ordinal());
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ShowCreditsPacket packet) {
            packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
            packet.setStatus(ShowCreditsPacket.Status.values()[VarInts.readInt(buffer)]);
        }
    }

}
