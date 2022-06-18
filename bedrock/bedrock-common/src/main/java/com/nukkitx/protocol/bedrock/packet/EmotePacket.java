package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketReader;
import com.nukkitx.protocol.bedrock.protocol.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.EmoteFlag;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.EnumSet;
import java.util.Set;

interface EmotePacket extends BedrockPacket {
    long runtimeEntityId;
    String emoteId;
    final Set<EmoteFlag> flags = EnumSet.noneOf(EmoteFlag.class);


    record v388 implements EmotePacket {


        @Override
        public void serialize(ByteBuf buffer, BedrockPacketHelper helper, EmotePacket packet) {
            VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
            helper.writeString(buffer, packet.getEmoteId());
            int flags = 0;
            for (EmoteFlag flag : packet.getFlags()) {
                flags |= 1 << flag.ordinal();
            }
            buffer.writeByte(flags);
        }

        @Override
        public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, EmotePacket packet) {
            packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
            packet.setEmoteId(helper.readString(buffer));
            int flags = buffer.readUnsignedByte();
            if ((flags & 0b1) != 0) {
                packet.getFlags().add(EmoteFlag.SERVER_SIDE);
            }
        }
    }

}
