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

public interface ShowCreditsPacket extends BedrockPacket {
    long runtimeEntityId;
    Status status;


    public enum Status {
        START_CREDITS,
        END_CREDITS
    }

    record v291 implements ShowCreditsPacket {


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
